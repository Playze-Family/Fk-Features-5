package com.agonkolgeci.fk_features.api.config;

import com.agonkolgeci.fk_features.FkPlugin;
import com.agonkolgeci.fk_features.common.PluginManager;
import com.agonkolgeci.fk_features.common.PluginModule;
import com.agonkolgeci.fk_features.utils.objects.ObjectUtils;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConfigManager extends PluginModule implements PluginManager {

    @Getter @NotNull private final FileConfiguration configuration;

    public ConfigManager(@NotNull FkPlugin plugin) {
        super(plugin);

        this.instance.saveDefaultConfig();
        this.configuration = instance.getConfig();
    }

    @Override
    public void load() throws Exception {
    }

    @Override
    public void unload() throws Exception {
    }

    public void saveChanges() {
        instance.saveConfig();
    }

    public ConfigEntry of() {
        return new ConfigEntry(this, configuration);
    }

    @NotNull
    public ConfigEntry of(@NotNull String... path) {
        return new ConfigEntry(this, requireConfigurationSection(configuration, path));
    }

    @NotNull
    protected String formatPath(@NotNull String ... path) {
        return String.join(".", path);
    }

    @Nullable
    public ConfigurationSection retrieveConfigurationSection(@NotNull ConfigurationSection parent, @NotNull String... path) {
        return parent.getConfigurationSection(formatPath(path));
    }

    @NotNull
    public ConfigurationSection requireConfigurationSection(@NotNull ConfigurationSection parent, @NotNull String... path) {
        return ObjectUtils.requireNonNullElseGet(retrieveConfigurationSection(parent, formatPath(path)), () -> parent.createSection(formatPath(path)));
    }

}
