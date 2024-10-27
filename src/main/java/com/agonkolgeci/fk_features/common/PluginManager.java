package com.agonkolgeci.fk_features.common;

public interface PluginManager {

    void load() throws Exception;
    void unload() throws Exception;

}