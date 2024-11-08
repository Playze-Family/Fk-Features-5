package com.agonkolgeci.fk_features.plugin.spawners.items;

import com.agonkolgeci.fk_features.api.config.ConfigEntry;
import com.agonkolgeci.fk_features.common.PluginAddon;
import com.agonkolgeci.fk_features.common.PluginManager;
import com.agonkolgeci.fk_features.plugin.spawners.SpawnersManager;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
public class ItemsSpawnersManager extends PluginAddon<SpawnersManager> implements PluginManager {

    @NotNull private final ConfigEntry configuration;

    @NotNull private final List<ItemsSpawner> spawners;

    public ItemsSpawnersManager(@NotNull SpawnersManager module) {
        super(module);

        this.configuration = module.getConfiguration().of("items");

        this.spawners = retrieveSpawners(configuration);
    }

    @NotNull
    public List<ItemsSpawner> retrieveSpawners(@NotNull ConfigEntry configuration) {
        return configuration.keys(entry -> new ItemsSpawner(module, entry));
    }

    @Override
    public void load() {
        spawners.forEach(itemsSpawner -> itemsSpawner.enable(module));
    }

    @Override
    public void unload() {
        spawners.forEach(ItemsSpawner::disable);
    }
}
