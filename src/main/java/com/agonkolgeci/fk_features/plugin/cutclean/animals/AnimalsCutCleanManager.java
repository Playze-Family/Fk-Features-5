package com.agonkolgeci.fk_features.plugin.cutclean.animals;

import com.agonkolgeci.fk_features.api.events.ListenerAdapter;
import com.agonkolgeci.fk_features.common.PluginAddon;
import com.agonkolgeci.fk_features.common.PluginManager;
import com.agonkolgeci.fk_features.plugin.cutclean.CutCleanManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class AnimalsCutCleanManager extends PluginAddon<CutCleanManager> implements PluginManager, ListenerAdapter {

    @Getter @Setter private boolean active;

    public AnimalsCutCleanManager(@NotNull CutCleanManager module) {
        super(module);

        this.active = module.getConfiguration().require("animals");
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
    public void onEntityDeath(@Nonnull EntityDeathEvent event) {
        if(!active) return;

        for(@NotNull final ItemStack dropItemStack : event.getDrops()) {
            @Nullable final Material burnedMaterial = burnMaterial(dropItemStack.getType());

            if(burnedMaterial != null) {
                dropItemStack.setType(burnedMaterial);
            }
        }
    }

    @Nullable
    public static Material burnMaterial(@NotNull Material material) {
        switch (material) {
            case PORK: return Material.GRILLED_PORK;
            case RAW_FISH: return Material.RAW_FISH;
            case RAW_BEEF: return Material.COOKED_BEEF;
            case RAW_CHICKEN: return Material.COOKED_CHICKEN;
            case RABBIT: return Material.COOKED_RABBIT;
            case MUTTON: return Material.COOKED_MUTTON;
        }

        return null;
    }

}
