package com.agonkolgeci.fk_features.plugin.restrictions.enchants;

import com.agonkolgeci.fk_features.api.config.ConfigEntry;
import com.agonkolgeci.fk_features.api.events.ListenerAdapter;
import com.agonkolgeci.fk_features.api.events.custom.inventory.AnvilCombineItemsEvent;
import com.agonkolgeci.fk_features.common.PluginAddon;
import com.agonkolgeci.fk_features.common.PluginManager;
import com.agonkolgeci.fk_features.plugin.players.FkPlayer;
import com.agonkolgeci.fk_features.plugin.restrictions.RestrictionsManager;
import com.agonkolgeci.fk_features.utils.minecraft.inventory.ItemBuilder;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

@Getter
public class EnchantsRestrictionsManager extends PluginAddon<RestrictionsManager> implements PluginManager, ListenerAdapter {

    @NotNull private final ConfigEntry configuration;

    @NotNull private final Map<Enchantment, EnchantRestriction> restrictions;

    public EnchantsRestrictionsManager(@NotNull RestrictionsManager module) {
        super(module);

        this.configuration = module.getConfiguration().of("enchantments");

        this.restrictions = retrieveRestrictions(configuration);
    }

    @Override
    public void load() throws Exception {
        module.getPlugin().getEventsController().registerEventAdapter(this);
    }

    @Override
    public void unload() throws Exception {
        module.getPlugin().getEventsController().unregisterEventAdapter(this);
    }

    @NotNull
    public static Map<Enchantment, EnchantRestriction> retrieveRestrictions(@NotNull ConfigEntry configuration) {
        return configuration.keys(c -> Objects.requireNonNull(Enchantment.getByName(c.getName().toUpperCase()), String.format("Unknown Enchantment '%s'", c)), EnchantRestriction::new);
    }

    public boolean isRestricted(@NotNull ItemStack itemStack) {
        if(module.getItemsRestrictionsManager().isRestricted(itemStack)) {
            return true;
        }

        return restrictions.values().stream().anyMatch(restriction -> restriction.isRestricted(itemStack));
    }

    @NotNull private static final Component FORBIDDEN_ENCHANTMENTS_CREATIVE_WARN = Component.text("Attention, les enchantements que vous essayez d'appliquer sur votre objet ne sont pas autorisés en survie.", NamedTextColor.RED);

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerEnchantItem(@NotNull EnchantItemEvent event) {
        @NotNull final FkPlayer fkPlayer = module.getPlugin().getPlayersManager().getFkPlayer(event.getEnchanter());
        @NotNull final ItemStack itemStack = event.getItem();
        if(itemStack == null) return;

        try {
            @NotNull final ItemStack resultItem = new ItemBuilder<>(itemStack.clone()).addEnchantments(event.getEnchantsToAdd(), true).toItemStack();

            if(isRestricted(resultItem)) {
                if(fkPlayer.getEntity().getGameMode() == GameMode.CREATIVE) {
                    module.sendInfo(fkPlayer.audience(), FORBIDDEN_ENCHANTMENTS_CREATIVE_WARN);

                    return;
                }

                throw new ForbiddenEnchantmentsException();
            }
        } catch (IllegalStateException exception) {
            event.setCancelled(true);

            module.sendError(fkPlayer.audience(), exception);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerAnvilCombineItem(@NotNull AnvilCombineItemsEvent event) {
        if(!(event.getWhoClicked() instanceof Player)) return;

        @NotNull final FkPlayer fkPlayer = module.getPlugin().getPlayersManager().getFkPlayer((Player) event.getWhoClicked());

        try {
            @NotNull final ItemStack resultItem = event.getResultItem();

            if(isRestricted(resultItem)) {
                if(fkPlayer.getEntity().getGameMode() == GameMode.CREATIVE) {
                    module.sendInfo(fkPlayer.audience(), FORBIDDEN_ENCHANTMENTS_CREATIVE_WARN);

                    return;
                }

                throw new ForbiddenEnchantmentsException();
            }
        } catch (IllegalStateException exception) {
            event.setCancelled(true);

            fkPlayer.getEntity().closeInventory();
            module.sendError(fkPlayer.audience(), exception);
        }
    }

}
