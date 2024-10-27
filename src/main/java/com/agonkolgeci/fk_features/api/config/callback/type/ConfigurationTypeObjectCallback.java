package com.agonkolgeci.fk_features.api.config.callback.type;

import com.agonkolgeci.fk_features.api.config.ConfigEntry;
import org.jetbrains.annotations.NotNull;

public interface ConfigurationTypeObjectCallback<T, O> {


    @NotNull O retrieveObject(@NotNull T type, @NotNull ConfigEntry configuration);

}