package com.agonkolgeci.fk_features.plugin.world;

import com.agonkolgeci.fk_features.FkPlugin;
import com.agonkolgeci.fk_features.api.config.ConfigEntry;
import com.agonkolgeci.fk_features.api.events.ListenerAdapter;
import com.agonkolgeci.fk_features.common.PluginManager;
import com.agonkolgeci.fk_features.common.PluginModule;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.jetbrains.annotations.NotNull;

public class WorldManager extends PluginModule implements PluginManager, ListenerAdapter {

    @Getter @NotNull private final ConfigEntry configuration;

    @Getter @Setter private boolean weather;

    public WorldManager(@NotNull FkPlugin plugin) {
        super(plugin);

        this.configuration = plugin.getConfigManager().of("world");

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
