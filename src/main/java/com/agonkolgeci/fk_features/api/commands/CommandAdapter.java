package com.agonkolgeci.fk_features.api.commands;

import com.agonkolgeci.fk_features.FkBootstrap;
import com.agonkolgeci.fk_features.utils.objects.ObjectUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public interface CommandAdapter extends CommandExecutor, TabCompleter {

    default boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try {
            return onCommandComplete(sender, command, label, args);
        } catch (IllegalStateException | IllegalArgumentException exception) {
            FkBootstrap.getPlugin().sendError(FkBootstrap.getPlugin().getAdventureManager().sender(sender), exception);

            return true;
        } catch (Exception exception) {
            exception.printStackTrace();

            return false;
        }
    }

    @Nullable
    default List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try {
            return ObjectUtils.requireNonNullElse(onCommandTabComplete(sender, command, label, args), Collections.emptyList());
        } catch (IllegalStateException | IllegalArgumentException exception) {
            FkBootstrap.getPlugin().sendError(FkBootstrap.getPlugin().getAdventureManager().sender(sender), exception);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        
        return Collections.emptyList();
    }

    boolean onCommandComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args);
    @Nullable List<String> onCommandTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args);

}
