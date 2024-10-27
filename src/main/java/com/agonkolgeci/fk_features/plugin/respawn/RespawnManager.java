package com.agonkolgeci.fk_features.plugin.respawn;

import com.agonkolgeci.fk_features.FkPlugin;
import com.agonkolgeci.fk_features.api.config.ConfigEntry;
import com.agonkolgeci.fk_features.api.events.ListenerAdapter;
import com.agonkolgeci.fk_features.common.PluginManager;
import com.agonkolgeci.fk_features.common.PluginModule;
import com.agonkolgeci.fk_features.plugin.players.FkPlayer;
import com.agonkolgeci.fk_features.utils.objects.ObjectUtils;
import lombok.Getter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.jetbrains.annotations.NotNull;

public class RespawnManager extends PluginModule implements PluginManager, ListenerAdapter {

    @Getter private final boolean autoRespawn;
    @Getter private final int protectionTime;

    public RespawnManager(@NotNull FkPlugin plugin) {
        super(plugin);

        @NotNull ConfigEntry configuration = plugin.getConfigManager().of("respawn");

        this.autoRespawn = configuration.get("auto_respawn", false);
        this.protectionTime = configuration.get("protection_time", 0);
    }

    @Override
    public void load() throws Exception {
        plugin.getEventsController().registerEventAdapter(this);
    }

    @Override
    public void unload() throws Exception {
        plugin.getEventsController().unregisterEventAdapter(this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerRespawn(@NotNull PlayerRespawnEvent event) {
        @NotNull final FkPlayer fkPlayer = plugin.getPlayersManager().getFkPlayer(event.getPlayer());

        if(autoRespawn) {
            server.getScheduler().runTaskLater(instance, () -> fkPlayer.getEntity().spigot().respawn(), 1);
        }

        final int protectionTicks = ((int) ObjectUtils.retrieveTicks(protectionTime)) + fkPlayer.getEntity().getMaximumNoDamageTicks();
        event.getPlayer().setNoDamageTicks(protectionTicks);
    }

}
