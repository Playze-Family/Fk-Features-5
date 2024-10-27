package com.agonkolgeci.fk_features.plugin.restrictions.items;

import com.agonkolgeci.fk_features.api.config.ConfigEntry;
import com.agonkolgeci.fk_features.api.events.ListenerAdapter;
import com.agonkolgeci.fk_features.common.PluginAddon;
import com.agonkolgeci.fk_features.common.PluginManager;
import com.agonkolgeci.fk_features.plugin.players.FkPlayer;
import com.agonkolgeci.fk_features.plugin.restrictions.RestrictionsManager;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemsRestrictionsManager extends PluginAddon<RestrictionsManager> implements PluginManager, ListenerAdapter {

    @Getter @NotNull private final ConfigEntry configuration;

    @Getter @NotNull private final List<ItemRestriction> restrictions;

    public ItemsRestrictionsManager(@NotNull RestrictionsManager module) {
        super(module);

        this.configuration = module.getConfiguration().of("items");

        this.restrictions = retrieveRestrictions(configuration);
    }

    @NotNull private static final Component FORBIDDEN_ITEM_CREATIVE_WARN = Component.text("Attention, cet objet n'est pas autorisé en survie.", NamedTextColor.RED);

    @Override
    public void load() throws Exception {
        module.getPlugin().getEventsController().registerEventAdapter(this);
    }

    @Override
    public void unload() throws Exception {
        module.getPlugin().getEventsController().unregisterEventAdapter(this);
    }

    @NotNull
    public static List<ItemRestriction> retrieveRestrictions(@NotNull ConfigEntry configuration) {
        return configuration.keys(ItemRestriction::new);
    }

    public boolean isRestricted(@NotNull ItemStack itemStack) {
        return restrictions.stream().anyMatch(restriction -> restriction.isRestricted(itemStack));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCraftItem(@NotNull CraftItemEvent event) {
        if(!(event.getWhoClicked() instanceof Player)) return;

        @NotNull final FkPlayer fkPlayer = module.getPlugin().getPlayersManager().getFkPlayer((Player) event.getWhoClicked());

        try {
            @NotNull final ItemStack itemStack = event.getRecipe().getResult();

            if(isRestricted(itemStack)) {
                if(fkPlayer.getEntity().getGameMode() == GameMode.CREATIVE) {
                    module.sendInfo(fkPlayer.audience(), FORBIDDEN_ITEM_CREATIVE_WARN);

                    return;
                }

                throw new ForbiddenItemException();
            }
        } catch (IllegalStateException exception) {
            event.setCancelled(true);

            fkPlayer.getEntity().closeInventory();
            module.sendError(fkPlayer.audience(), exception);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(@NotNull PlayerInteractEvent event) {
        @NotNull final FkPlayer fkPlayer = module.getPlugin().getPlayersManager().getFkPlayer(event.getPlayer());
        @NotNull final ItemStack itemStack = event.getItem();
        if(itemStack == null) return;

        try {
            if(isRestricted(itemStack)) {
                if(fkPlayer.getEntity().getGameMode() == GameMode.CREATIVE) {
                    module.sendInfo(fkPlayer.audience(), FORBIDDEN_ITEM_CREATIVE_WARN);

                    return;
                }

                throw new ForbiddenItemException();
            }
        } catch (IllegalStateException exception) {
            event.setCancelled(true);

            fkPlayer.getEntity().getInventory().remove(itemStack);
            module.sendError(fkPlayer.audience(), exception);
        }
    }

}
