package com.agonkolgeci.fk_features.plugin.mobs;

import com.agonkolgeci.fk_features.FkPlugin;
import com.agonkolgeci.fk_features.api.config.ConfigEntry;
import com.agonkolgeci.fk_features.api.events.ListenerAdapter;
import com.agonkolgeci.fk_features.common.PluginManager;
import com.agonkolgeci.fk_features.common.PluginModule;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MobsManager extends PluginModule implements PluginManager, ListenerAdapter {

    public MobsManager(@NotNull FkPlugin plugin) {
        super(plugin);
    }

    @Override
    public void load() throws Exception {
        plugin.getEventsController().registerEventAdapter(this);
    }

    @Override
    public void unload() throws Exception {
        plugin.getEventsController().unregisterEventAdapter(this);
    }


    @EventHandler
    public void onEntityDeath(@NotNull EntityDeathEvent event) {
        @NotNull final Entity entity = event.getEntity();

        @NotNull List<ItemStack> drops = retrieveMissingDrops(entity);
        if(drops.isEmpty()) return;

        for (@NotNull ItemStack drop : drops) {
            entity.getWorld().dropItemNaturally(entity.getLocation(), drop);
        }
    }

    @NotNull
    public List<ItemStack> retrieveMissingDrops(@NotNull Entity entity) {
        switch (entity.getType()) {
            case MAGMA_CUBE: {
                return Arrays.asList(new ItemStack(Material.MAGMA_CREAM, 1));
            }
        }

        return Collections.emptyList();
    }

}
