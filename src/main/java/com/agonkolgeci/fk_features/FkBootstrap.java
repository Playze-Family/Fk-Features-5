package com.agonkolgeci.fk_features;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class FkBootstrap extends JavaPlugin {

    @Getter private static FkBootstrap instance;
    @Getter private static FkPlugin plugin;

    @Override
    public void onEnable() {
        try {
            instance = this;

            plugin = new FkPlugin(this);
            plugin.load();
        } catch (Exception exception) {
            this.shutdown(exception);
        }
    }

    @Override
    public void onDisable() {
        try {
            plugin.unload();
        } catch (Exception exception) {
            this.shutdown(exception);
        }
    }

    public void shutdown(@NotNull Exception exception) {
        getLogger().log(Level.SEVERE, String.format("An critical error occurred with plugin %s, you can find the complete stack trace below.", getDescription().getName()), exception);
        getPluginLoader().disablePlugin(this);
    }

}
