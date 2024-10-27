package com.agonkolgeci.fk_features.plugin.spawners;

import com.agonkolgeci.fk_features.FkPlugin;
import com.agonkolgeci.fk_features.api.config.ConfigEntry;
import com.agonkolgeci.fk_features.common.PluginManager;
import com.agonkolgeci.fk_features.common.PluginModule;
import com.agonkolgeci.fk_features.plugin.spawners.items.ItemsSpawnersManager;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public class SpawnersManager extends PluginModule implements PluginManager {

    @Getter @NotNull private final ConfigEntry configuration;

    @Getter @NotNull private final ItemsSpawnersManager itemsSpawnersManager;

    public SpawnersManager(@NotNull FkPlugin plugin) {
        super(plugin);

        this.configuration = plugin.getConfigManager().of("spawners");

        this.itemsSpawnersManager = new ItemsSpawnersManager(this);
    }

    @Override
    public void load() throws Exception {
        itemsSpawnersManager.load();
    }

    @Override
    public void unload() throws Exception {
        itemsSpawnersManager.unload();
    }
}
