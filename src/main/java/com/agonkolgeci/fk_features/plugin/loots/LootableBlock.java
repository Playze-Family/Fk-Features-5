package com.agonkolgeci.fk_features.plugin.loots;

import com.agonkolgeci.fk_features.api.config.ConfigEntry;
import com.agonkolgeci.fk_features.utils.objects.ObjectUtils;
import lombok.Getter;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
public class LootableBlock {

    @NotNull private final Material type;
    private final double probability;

    @NotNull private final List<LootableItem> items;

    public LootableBlock(@NotNull ConfigEntry configuration) {
        this.type = ObjectUtils.fetchObject(Material.class, configuration.require("type"), "Unknown Material '%s'");
        this.probability = configuration.require("probability");

        this.items = retrieveItems(configuration.of("items"));
    }

    @NotNull
    public static List<LootableItem> retrieveItems(@NotNull ConfigEntry configuration) {
        return configuration.keys(LootableItem::new);
    }

}
