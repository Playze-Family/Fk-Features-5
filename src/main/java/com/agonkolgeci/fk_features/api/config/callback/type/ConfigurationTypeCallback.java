package com.agonkolgeci.fk_features.api.config.callback.type;

import com.agonkolgeci.fk_features.api.config.ConfigEntry;
import org.jetbrains.annotations.NotNull;

public interface ConfigurationTypeCallback<K> {

    @NotNull K retrieveType(@NotNull ConfigEntry configuration);

}