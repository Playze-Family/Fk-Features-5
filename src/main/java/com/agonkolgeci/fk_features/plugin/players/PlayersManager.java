package com.agonkolgeci.fk_features.plugin.players;

import com.agonkolgeci.fk_features.FkPlugin;
import com.agonkolgeci.fk_features.api.events.ListenerAdapter;
import com.agonkolgeci.fk_features.common.PluginManager;
import com.agonkolgeci.fk_features.common.PluginModule;
import com.agonkolgeci.fk_features.utils.objects.ObjectUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class PlayersManager extends PluginModule implements PluginManager, ListenerAdapter {

    @NotNull private final Map<Player, FkPlayer> players;

    public PlayersManager(@NotNull FkPlugin plugin) {
        super(plugin);

        this.players = new HashMap<>();
    }

    @Override
    public void load() throws Exception {
    }

    @Override
    public void unload() throws Exception {
    }

    public FkPlayer getFkPlayer(@NotNull Player player) {
        return ObjectUtils.retrieveObjectOrElseGet(players, player, () -> new FkPlayer(plugin, player));
    }

}
