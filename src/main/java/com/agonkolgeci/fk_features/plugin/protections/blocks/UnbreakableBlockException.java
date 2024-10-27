package com.agonkolgeci.fk_features.plugin.protections.blocks;

public class UnbreakableBlockException extends IllegalStateException {

    public UnbreakableBlockException() {
        super("Ce bloc n'est pas cassable.");
    }
}
