package com.agonkolgeci.fk_features.plugin.restrictions;

import com.agonkolgeci.fk_features.FkPlugin;
import com.agonkolgeci.fk_features.api.config.ConfigEntry;
import com.agonkolgeci.fk_features.api.messages.MessagesAdapter;
import com.agonkolgeci.fk_features.common.PluginManager;
import com.agonkolgeci.fk_features.common.PluginModule;
import com.agonkolgeci.fk_features.plugin.restrictions.enchants.EnchantsRestrictionsManager;
import com.agonkolgeci.fk_features.plugin.restrictions.items.ItemsRestrictionsManager;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

public class RestrictionsManager extends PluginModule implements PluginManager, MessagesAdapter {

    @Getter @NotNull private final ConfigEntry configuration;

    @Getter @NotNull private final EnchantsRestrictionsManager enchantsRestrictionsManager;
    @Getter @NotNull private final ItemsRestrictionsManager itemsRestrictionsManager;

    public RestrictionsManager(@NotNull FkPlugin plugin) {
        super(plugin);

        this.configuration = plugin.getConfigManager().of("restrictions");

        this.enchantsRestrictionsManager = new EnchantsRestrictionsManager(this);
        this.itemsRestrictionsManager = new ItemsRestrictionsManager(this);
    }

    @Override
    public void load() throws Exception {
        enchantsRestrictionsManager.load();
        itemsRestrictionsManager.load();
    }

    @Override
    public void unload() throws Exception {
        enchantsRestrictionsManager.unload();
        itemsRestrictionsManager.unload();
    }

    @Override
    public @NotNull Component getLabel() {
        return Component.text("Restrictions", NamedTextColor.RED);
    }
}
