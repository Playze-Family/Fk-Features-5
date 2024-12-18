package com.agonkolgeci.fk_features.plugin.cutclean.ores;

import com.agonkolgeci.fk_features.api.events.ListenerAdapter;
import com.agonkolgeci.fk_features.common.PluginAddon;
import com.agonkolgeci.fk_features.common.PluginManager;
import com.agonkolgeci.fk_features.plugin.cutclean.CutCleanManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class OresCutCleanManager extends PluginAddon<CutCleanManager> implements PluginManager, ListenerAdapter {

    @Getter @Setter private boolean active;

    public OresCutCleanManager(@NotNull CutCleanManager module) {
        super(module);

        this.active = module.getConfiguration().require("ores");
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
    public void onBlockBreak(@Nonnull BlockBreakEvent event) {
        if(!active) return;

        @NotNull final Block block = event.getBlock();
        @Nullable final Material blastedMaterial = blastMaterial(block.getType());

        if(blastedMaterial != null) {
            block.getWorld().dropItem(block.getLocation().clone().add(.5, .5, .5), new ItemStack(blastedMaterial, 1));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemSpawn(@Nonnull ItemSpawnEvent event) {
        if(!active) return;

        @NotNull final ItemStack itemStack = event.getEntity().getItemStack();
        @Nullable final Material blastedMaterial = blastMaterial(itemStack.getType());

        if(blastedMaterial != null) {
            itemStack.setType(blastedMaterial);
        }
    }

    @Nullable
    public static Material blastMaterial(@NotNull Material material) {
        switch (material) {
            case COAL_ORE: return Material.COAL;
            case IRON_ORE: return Material.IRON_INGOT;
            case GOLD_ORE: return Material.GOLD_INGOT;
            case REDSTONE_ORE: return Material.REDSTONE;
            case EMERALD_ORE: return Material.EMERALD;
            case DIAMOND_ORE: return Material.DIAMOND;
        }

        return null;
    }
}
