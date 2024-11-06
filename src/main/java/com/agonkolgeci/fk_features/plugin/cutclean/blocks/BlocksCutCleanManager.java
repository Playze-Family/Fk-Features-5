package com.agonkolgeci.fk_features.plugin.cutclean.blocks;

import com.agonkolgeci.fk_features.api.events.ListenerAdapter;
import com.agonkolgeci.fk_features.common.PluginAddon;
import com.agonkolgeci.fk_features.common.PluginManager;
import com.agonkolgeci.fk_features.plugin.cutclean.CutCleanManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class BlocksCutCleanManager extends PluginAddon<CutCleanManager> implements PluginManager, ListenerAdapter {

    @Getter @Setter private boolean active;

    public BlocksCutCleanManager(@NotNull CutCleanManager module) {
        super(module);

        this.active = module.getConfiguration().require("blocks");
    }

    @Override
    public void load() throws Exception {
        module.getPlugin().getEventsController().registerEventAdapter(this);
    }

    @Override
    public void unload() throws Exception {
        module.getPlugin().getEventsController().unregisterEventAdapter(this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemSpawn(@Nonnull ItemSpawnEvent event) {
        if(!active) return;

        @NotNull final ItemStack itemStack = event.getEntity().getItemStack();

        @Nullable final Material transformedMaterial = transformMaterial(itemStack.getType());
        if(transformedMaterial != null) {
            itemStack.setType(transformedMaterial);
        }
    }

    @Nullable
    public static Material transformMaterial(@NotNull Material material) {
        switch (material) {
            case GRAVEL: return Material.FLINT;
        }

        return null;
    }

}
