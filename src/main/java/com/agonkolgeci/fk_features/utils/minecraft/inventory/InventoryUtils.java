package com.agonkolgeci.fk_features.utils.minecraft.inventory;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InventoryUtils {

    @NotNull
    public static Component getDisplayName(@NotNull ItemStack itemStack, @NotNull TextColor color) {
        return Component.text(String.format("%,d", itemStack.getAmount()), color).appendSpace().append(Component.text(itemStack.getType().toString()).color(color));
    }

}
