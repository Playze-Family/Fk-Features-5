package com.agonkolgeci.fk_features.plugin.loots;

import com.agonkolgeci.fk_features.api.config.ConfigEntry;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Getter
public class LootableItem {

    private final double probability;
    @NotNull private final ItemStack itemStack;

    public LootableItem(@NotNull ConfigEntry configuration) {
        this.probability = configuration.require("probability");
        this.itemStack = configuration.require("item");
    }


}
