package com.agonkolgeci.fk_features.plugin.protections;

import com.agonkolgeci.fk_features.FkPlugin;
import com.agonkolgeci.fk_features.api.config.ConfigEntry;
import com.agonkolgeci.fk_features.api.messages.MessagesAdapter;
import com.agonkolgeci.fk_features.common.PluginManager;
import com.agonkolgeci.fk_features.common.PluginModule;
import com.agonkolgeci.fk_features.plugin.protections.blocks.BlocksProtectionsManager;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;

public class ProtectionsManager extends PluginModule implements PluginManager, MessagesAdapter {

    @Getter @NotNull private final ConfigEntry configuration;

    @Getter @NotNull private final URL wiki;
    @Getter @NotNull private final BlocksProtectionsManager blocksProtectionsManager;

    public ProtectionsManager(@NotNull FkPlugin plugin) throws MalformedURLException {
        super(plugin);

        this.configuration = plugin.getConfigManager().of("protections");

        this.wiki = new URL(configuration.require("wiki"));
        this.blocksProtectionsManager = new BlocksProtectionsManager(this);
    }

    @Override
    public void load() throws Exception {
        blocksProtectionsManager.load();
    }

    @Override
    public void unload() throws Exception {
        blocksProtectionsManager.unload();
    }

    @Override
    public @NotNull Component getLabel() {
        return Component.text("Protections", NamedTextColor.GOLD).hoverEvent(HOVER_EVENT_ENCOURAGE_CLICK).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, wiki.toString()));
    }
}
