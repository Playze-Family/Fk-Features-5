package com.agonkolgeci.fk_features;

import com.agonkolgeci.fk_features.api.commands.CommandsManager;
import com.agonkolgeci.fk_features.api.config.ConfigManager;
import com.agonkolgeci.fk_features.api.events.EventsManager;
import com.agonkolgeci.fk_features.api.messages.MessagesAdapter;
import com.agonkolgeci.fk_features.common.PluginManager;
import com.agonkolgeci.fk_features.plugin.cutclean.CutCleanManager;
import com.agonkolgeci.fk_features.plugin.loots.LootsManager;
import com.agonkolgeci.fk_features.plugin.mobs.MobsManager;
import com.agonkolgeci.fk_features.plugin.players.PlayersManager;
import com.agonkolgeci.fk_features.plugin.protections.ProtectionsManager;
import com.agonkolgeci.fk_features.plugin.respawn.RespawnManager;
import com.agonkolgeci.fk_features.plugin.restrictions.RestrictionsManager;
import com.agonkolgeci.fk_features.plugin.spawners.SpawnersManager;
import com.agonkolgeci.fk_features.plugin.tab.TabManager;
import com.agonkolgeci.fk_features.plugin.world.WorldManager;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

public class FkPlugin implements PluginManager, MessagesAdapter {

    @Getter @NotNull private final FkBootstrap instance;

    @Getter @NotNull private final BukkitAudiences adventureManager;

    @Getter @NotNull private final CommandsManager commandsManager;
    @Getter @NotNull private final EventsManager eventsController;
    @Getter @NotNull private final ConfigManager configManager;

    @Getter @NotNull private final PlayersManager playersManager;
    @Getter @NotNull private final WorldManager worldManager;

    @Getter @NotNull private final TabManager tabManager;
    @Getter @NotNull private final RespawnManager respawnManager;
    @Getter @NotNull private final CutCleanManager cutCleanManager;
    @Getter @NotNull private final LootsManager lootsManager;
    @Getter @NotNull private final ProtectionsManager protectionsManager;
    @Getter @NotNull private final RestrictionsManager restrictionsManager;
    @Getter @NotNull private final SpawnersManager spawnersManager;
    @Getter @NotNull private final MobsManager mobsManager;

    public FkPlugin(@NotNull FkBootstrap instance) throws Exception {
        this.instance = instance;

        this.adventureManager = BukkitAudiences.create(instance);

        this.commandsManager = new CommandsManager(this);
        this.eventsController = new EventsManager(this);
        this.configManager = new ConfigManager(this);

        // Necessary managers
        this.playersManager = new PlayersManager(this);
        this.worldManager = new WorldManager(this);

        this.tabManager = new TabManager(this);
        this.respawnManager = new RespawnManager(this);
        this.cutCleanManager = new CutCleanManager(this);
        this.lootsManager = new LootsManager(this);
        this.protectionsManager = new ProtectionsManager(this);
        this.restrictionsManager = new RestrictionsManager(this);
        this.spawnersManager = new SpawnersManager(this);
        this.mobsManager = new MobsManager(this);
    }

    @Override
    public void load() throws Exception {
        commandsManager.load();
        eventsController.load();
        configManager.load();

        playersManager.load();
        worldManager.load();

        tabManager.load();
        respawnManager.load();
        cutCleanManager.load();
        lootsManager.load();
        protectionsManager.load();
        restrictionsManager.load();
        spawnersManager.load();
        mobsManager.load();
    }

    @Override
    public void unload() throws Exception {
        commandsManager.unload();
        eventsController.unload();
        configManager.unload();

        playersManager.unload();
        worldManager.unload();

        tabManager.unload();
        respawnManager.unload();
        cutCleanManager.unload();
        lootsManager.unload();
        protectionsManager.unload();
        restrictionsManager.unload();
        spawnersManager.unload();
        mobsManager.unload();
    }

    @Override
    public @NotNull Component getLabel() {
        return Component.text("Fallen Kingdoms 5", NamedTextColor.RED);
    }
}