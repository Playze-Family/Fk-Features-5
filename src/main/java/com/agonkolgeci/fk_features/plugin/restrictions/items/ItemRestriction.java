package com.agonkolgeci.fk_features.plugin.restrictions.items;

import com.agonkolgeci.fk_features.api.config.ConfigEntry;
import com.agonkolgeci.fk_features.plugin.restrictions.enchants.EnchantRestriction;
import com.agonkolgeci.fk_features.plugin.restrictions.enchants.EnchantsRestrictionsManager;
import lombok.Getter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Getter
public class ItemRestriction {

    @Getter @NotNull private final ConfigEntry configuration;

    @Getter @NotNull private final ItemStack restrictedItemStack;
    @Getter @NotNull private final Map<Enchantment, EnchantRestriction> enchantmentsRestriction;

    public ItemRestriction(@NotNull ConfigEntry configuration) {
        this.configuration = configuration;

        this.restrictedItemStack = configuration.require("item");

        this.enchantmentsRestriction = EnchantsRestrictionsManager.retrieveRestrictions(configuration.of("enchantments"));
    }

    public boolean isRestricted(@NotNull ItemStack itemStack) {
        if(restrictedItemStack.getType() != itemStack.getType()) return false;

        if(!enchantmentsRestriction.isEmpty()) {
            return enchantmentsRestriction.values().stream().anyMatch(restriction -> restriction.isRestricted(itemStack));
        }

        return restrictedItemStack.isSimilar(itemStack);
    }

}
