package com.agonkolgeci.fk_features.common;

import com.agonkolgeci.fk_features.FkPlugin;
import lombok.Getter;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

@Getter
public abstract class PluginModule {

    @NotNull protected final FkPlugin plugin;
    @NotNull protected final JavaPlugin instance;

    @NotNull protected final Logger logger;

    @NotNull protected final Server server;
    @NotNull protected final PluginManager pluginManager;
    @NotNull protected final BukkitScheduler bukkitScheduler;

    public PluginModule(@NotNull FkPlugin plugin) {
        this.plugin = plugin;
        this.instance = plugin.getInstance();

        this.logger = instance.getLogger();

        this.server = instance.getServer();
        this.pluginManager = server.getPluginManager();
        this.bukkitScheduler = server.getScheduler();
    }
}
