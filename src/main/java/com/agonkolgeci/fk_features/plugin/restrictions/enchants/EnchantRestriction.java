package com.agonkolgeci.fk_features.plugin.restrictions.enchants;

import com.agonkolgeci.fk_features.api.config.ConfigEntry;
import lombok.Getter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Getter
public class EnchantRestriction {

    @Getter @NotNull private final Enchantment enchantment;
    @Getter @NotNull private final ConfigEntry configuration;

    private final int levelMax;

    public EnchantRestriction(@NotNull Enchantment enchantment, @NotNull ConfigEntry configuration) {
        this.enchantment = enchantment;
        this.configuration = configuration;

        this.levelMax = configuration.get("level_max", Integer.MAX_VALUE);
    }

    public boolean isRestricted(@NotNull ItemStack itemStack) {
        return itemStack.getEnchantments().getOrDefault(enchantment, 0) > levelMax;
    }

}
