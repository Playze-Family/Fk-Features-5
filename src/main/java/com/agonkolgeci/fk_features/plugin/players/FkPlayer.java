package com.agonkolgeci.fk_features.plugin.players;

import com.agonkolgeci.fk_features.FkPlugin;
import com.agonkolgeci.fk_features.common.PluginModule;
import lombok.Getter;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FkPlayer extends PluginModule {

    @Getter @NotNull private final Player entity;

    @NotNull private final Audience audience;

    public FkPlayer(@NotNull FkPlugin plugin, @NotNull Player entity) {
        super(plugin);

        this.entity = entity;

        this.audience = plugin.getAdventureManager().player(entity);
    }

    @NotNull
    public Audience audience() {
        return audience;
    }

}
