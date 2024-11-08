package com.agonkolgeci.fk_features.plugin.world;

import com.agonkolgeci.fk_features.FkPlugin;
import com.agonkolgeci.fk_features.api.config.ConfigEntry;
import com.agonkolgeci.fk_features.api.events.ListenerAdapter;
import com.agonkolgeci.fk_features.common.PluginManager;
import com.agonkolgeci.fk_features.common.PluginModule;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WorldManager extends PluginModule implements PluginManager, ListenerAdapter {

    @Getter @NotNull private final ConfigEntry configuration;

    @Getter @NotNull private final World world;
    @Getter @Nullable private final World nether;
    @Getter @Nullable private final World end;

    @Getter @Setter private boolean weather;

    public WorldManager(@NotNull FkPlugin plugin) {
        super(plugin);

        this.configuration = plugin.getConfigManager().of("world");

        this.world = server.getWorlds().stream().findFirst().orElseThrow(() -> new IllegalStateException("Cannot find the primary World !"));
        this.nether = server.getWorlds().size() > 1 ? server.getWorlds().get(1) : null;
        this.end = server.getWorlds().size() > 2 ? server.getWorlds().get(2) : null;

        this.weather = configuration.require("weather");
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
    public void onWeatherChange(@NotNull WeatherChangeEvent event) {
        if(!weather) {
            event.setCancelled(true);
        }
    }

}
