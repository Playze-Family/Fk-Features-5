package com.agonkolgeci.fk_features.plugin.restrictions.enchants;

public class ForbiddenEnchantmentsException extends IllegalStateException {

    public ForbiddenEnchantmentsException() {
        super("Les enchantements que vous essayez d'appliquer sur votre objet ne sont pas autorisés.");
    }
}
