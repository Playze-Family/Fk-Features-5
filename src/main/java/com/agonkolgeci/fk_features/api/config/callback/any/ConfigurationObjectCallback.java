package com.agonkolgeci.fk_features.api.config.callback.any;

import com.agonkolgeci.fk_features.api.config.ConfigEntry;
import org.jetbrains.annotations.NotNull;

public interface ConfigurationObjectCallback<O> {


    @NotNull O retrieveObject(@NotNull ConfigEntry configuration);

}