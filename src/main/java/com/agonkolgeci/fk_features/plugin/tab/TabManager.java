package com.agonkolgeci.fk_features.plugin.tab;

import com.agonkolgeci.fk_features.FkPlugin;
import com.agonkolgeci.fk_features.api.config.ConfigEntry;
import com.agonkolgeci.fk_features.api.events.ListenerAdapter;
import com.agonkolgeci.fk_features.common.PluginManager;
import com.agonkolgeci.fk_features.common.PluginModule;
import com.agonkolgeci.fk_features.plugin.players.FkPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class TabManager extends PluginModule implements PluginManager, ListenerAdapter {

    @NotNull private final Component header;
    @NotNull private final Component footer;

    public TabManager(@NotNull FkPlugin plugin) {
        super(plugin);

        @NotNull ConfigEntry configuration = plugin.getConfigManager().of("tab");

        this.header = LegacyComponentSerializer.legacyAmpersand().deserialize(configuration.get("header", ""));
        this.footer = LegacyComponentSerializer.legacyAmpersand().deserialize(configuration.get("footer", ""));
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
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        @NotNull final FkPlayer fkPlayer = plugin.getPlayersManager().getFkPlayer(event.getPlayer());

        fkPlayer.audience().sendPlayerListHeaderAndFooter(header, footer);
    }
}
