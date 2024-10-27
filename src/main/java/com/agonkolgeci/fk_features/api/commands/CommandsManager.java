package com.agonkolgeci.fk_features.api.commands;

import com.agonkolgeci.fk_features.FkPlugin;
import com.agonkolgeci.fk_features.common.PluginManager;
import com.agonkolgeci.fk_features.common.PluginModule;
import org.bukkit.command.PluginCommand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unchecked")
public class CommandsManager extends PluginModule implements PluginManager {

    public CommandsManager(@NotNull FkPlugin plugin) {
        super(plugin);
    }

    @Override
    public void load() throws Exception {
    }

    @Override
    public void unload() throws Exception {
    }

    @NotNull
    public <C extends CommandAdapter> C registerCommandAdapter(@NotNull String commandName, @NotNull CommandAdapter commandAdapter) {
        @Nullable final PluginCommand pluginCommand = instance.getCommand(commandName);
        if(pluginCommand == null) throw new IllegalStateException(String.format("Cannot retrieve command: %s", commandName));

        pluginCommand.setExecutor(commandAdapter);
        pluginCommand.setTabCompleter(commandAdapter);

        return (C) commandAdapter;
    }
}
