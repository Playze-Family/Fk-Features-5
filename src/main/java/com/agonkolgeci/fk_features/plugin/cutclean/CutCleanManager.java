package com.agonkolgeci.fk_features.plugin.cutclean;

import com.agonkolgeci.fk_features.FkPlugin;
import com.agonkolgeci.fk_features.api.config.ConfigEntry;
import com.agonkolgeci.fk_features.api.events.ListenerAdapter;
import com.agonkolgeci.fk_features.common.PluginManager;
import com.agonkolgeci.fk_features.common.PluginModule;
import com.agonkolgeci.fk_features.plugin.cutclean.animals.AnimalsCutCleanManager;
import com.agonkolgeci.fk_features.plugin.cutclean.ores.OresCutCleanManager;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public class CutCleanManager extends PluginModule implements PluginManager, ListenerAdapter {

    @Getter @NotNull private final ConfigEntry configuration;

    @Getter @NotNull private final AnimalsCutCleanManager animalsCutCleanManager;
    @Getter @NotNull private final OresCutCleanManager oresCutCleanManager;

    public CutCleanManager(@NotNull FkPlugin plugin) {
        super(plugin);

        this.configuration = plugin.getConfigManager().of("cutclean");

        this.animalsCutCleanManager = new AnimalsCutCleanManager(this);
        this.oresCutCleanManager = new OresCutCleanManager(this);
    }

    @Override
    public void load() throws Exception {
        animalsCutCleanManager.load();
        oresCutCleanManager.load();
    }

    @Override
    public void unload() throws Exception {
        animalsCutCleanManager.unload();
        oresCutCleanManager.unload();
    }

}
