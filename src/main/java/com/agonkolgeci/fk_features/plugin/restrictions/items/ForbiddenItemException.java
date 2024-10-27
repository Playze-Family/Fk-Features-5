package com.agonkolgeci.fk_features.plugin.restrictions.items;

public class ForbiddenItemException extends IllegalStateException {

    public ForbiddenItemException() {
        super("Cet objet n'est pas autorisé.");
    }
}
