package com.agonkolgeci.fk_features.plugin.protections.blocks;

import com.agonkolgeci.fk_features.api.config.ConfigEntry;
import com.agonkolgeci.fk_features.api.events.ListenerAdapter;
import com.agonkolgeci.fk_features.common.PluginAddon;
import com.agonkolgeci.fk_features.common.PluginManager;
import com.agonkolgeci.fk_features.plugin.players.FkPlayer;
import com.agonkolgeci.fk_features.plugin.protections.ProtectionsManager;
import com.agonkolgeci.fk_features.utils.objects.ObjectUtils;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BlocksProtectionsManager extends PluginAddon<ProtectionsManager> implements PluginManager, ListenerAdapter {

    @NotNull private final ConfigEntry configuration;

    @NotNull private final List<Material> unbreakable;
    @NotNull private final List<Material> indestructible;

    public BlocksProtectionsManager(@NotNull ProtectionsManager module) {
        super(module);

        this.configuration = module.getConfiguration().of("blocks");

        this.unbreakable = retrieveMaterials(configuration, "unbreakable");
        this.indestructible = retrieveMaterials(configuration, "indestructible");
    }

    @NotNull private static final Component UNBREAKABLE_BLOCK_CREATIVE_WARN = Component.text("Attention, ce bloc n'est pas cassable en survie.", NamedTextColor.RED);

    @Override
    public void load() throws Exception {
        module.getPlugin().getEventsController().registerEventAdapter(this);
    }

    @Override
    public void unload() throws Exception {
        module.getPlugin().getEventsController().unregisterEventAdapter(this);
    }

    @NotNull
    public List<Material> retrieveMaterials(@NotNull ConfigEntry configuration, @NotNull String key) {
        return configuration.get(key, new ArrayList<>()).stream().map(m -> ObjectUtils.fetchObject(Material.class, (String) m, "Unknown Material: '%s'")).collect(Collectors.toList());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(@Nonnull BlockBreakEvent event) {
        @NotNull final FkPlayer fkPlayer = module.getPlugin().getPlayersManager().getFkPlayer(event.getPlayer());
        @NotNull final Block block = event.getBlock();

        try {
            if(isUnbreakableBlock(block)) {
                if(fkPlayer.getEntity().getGameMode() == GameMode.CREATIVE) {
                    module.sendInfo(fkPlayer.audience(), UNBREAKABLE_BLOCK_CREATIVE_WARN);

                    return;
                }

                throw new UnbreakableBlockException();
            }
        } catch (IllegalStateException exception) {
            event.setCancelled(true);

            module.sendError(fkPlayer.audience(), exception);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityExplode(@Nonnull EntityExplodeEvent event) {
        event.blockList().removeIf(this::isIndestructibleBlock);
    }

    public boolean isUnbreakableBlock(@NotNull Material material) {
        return unbreakable.contains(material);
    }

    public boolean isUnbreakableBlock(@NotNull Block block) {
        return isUnbreakableBlock(block.getType());
    }

    public boolean isIndestructibleBlock(@NotNull Material material) {
        return indestructible.contains(material);
    }

    public boolean isIndestructibleBlock(@NotNull Block block) {
        return isIndestructibleBlock(block.getType());
    }


}
