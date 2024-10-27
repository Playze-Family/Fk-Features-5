package com.agonkolgeci.fk_features.utils.minecraft.inventory;

import com.agonkolgeci.fk_features.utils.objects.ObjectUtils;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class ItemBuilder<B extends ItemBuilder<B>> {

    @NotNull private final ItemStack itemStack;

    public ItemBuilder(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder(@NotNull Material material) {
        this(new ItemStack(material));
    }

    @NotNull
    public ItemStack toItemStack() {
        return itemStack;
    }

    @NotNull
    public B amount(int amount) {
        itemStack.setAmount(amount);
        return (B) this;
    }

    @NotNull
    public B type(@NotNull Material material) {
        itemStack.setType(material);
        return (B) this;
    }

    @NotNull
    public B itemMeta(@NotNull ItemMeta itemMeta) {
        itemStack.setItemMeta(itemMeta);
        return (B) this;
    }

    @Nullable
    public String displayName() {
        @Nullable final ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) return null;

        return itemMeta.getDisplayName();
    }

    @NotNull
    public B displayName(@NotNull String displayName) {
        @Nullable final ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) return (B) this;

        itemMeta.setDisplayName(displayName);

        return this.itemMeta(itemMeta);
    }

    @Nullable
    public List<String> lore() {
        @Nullable final ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) return null;

        return itemMeta.getLore();
    }

    @NotNull
    public B lore(@NotNull List<String> lines) {
        @Nullable final ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) return (B) this;

        itemMeta.setLore(lines);

        return this.itemMeta(itemMeta);
    }

    @NotNull
    public B lore(@NotNull String... lines) {
        return lore(Arrays.asList(lines));
    }

    @NotNull
    public B addLore(@NotNull List<String> lines) {
        @Nullable final ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) return (B) this;

        @Nullable final List<String> newLore = ObjectUtils.requireNonNullElse(lore(), new ArrayList<>());

        newLore.addAll(lines);

        return this.lore(newLore);
    }

    @NotNull
    public B addLore(@NotNull String... lines) {
        return addLore(Arrays.asList(lines));
    }

    @NotNull
    public B addEmptyLore() {
        return addLore("");
    }

    @NotNull
    public B addEnchantments(@NotNull Map<Enchantment, Integer> enchantmentLevelsMap, boolean ignoreLevelRestriction) {
        @Nullable final ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) return (B) this;

        enchantmentLevelsMap.forEach(((enchantment, level) -> itemMeta.addEnchant(enchantment, level, ignoreLevelRestriction)));

        return this.itemMeta(itemMeta);
    }

    @NotNull
    public B addEnchantment(@NotNull Enchantment enchantment, final int level) {
        itemStack.addEnchantment(enchantment, level);

        return (B) this;
    }

    @NotNull
    public B addUnsafeEnchantment(@NotNull Enchantment enchantment, final int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);

        return (B) this;
    }

    @NotNull
    public B removeEnchantment(@NotNull Enchantment enchantment) {
        @Nullable final ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) return (B) this;

        itemMeta.removeEnchant(enchantment);

        return this.itemMeta(itemMeta);
    }

    @NotNull
    public B addItemFlags(@NotNull ItemFlag... itemFlags) {
        @Nullable final ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) return (B) this;

        itemMeta.addItemFlags(itemFlags);

        return this.itemMeta(itemMeta);
    }

    @NotNull
    public B addItemFlags(@NotNull List<ItemFlag> itemFlags) {
        return addItemFlags(itemFlags.toArray(new ItemFlag[0]));
    }

    @NotNull
    public B removeItemFlags(@NotNull ItemFlag... itemFlags) {
        @Nullable final ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) return (B) this;

        itemMeta.removeItemFlags(itemFlags);

        return this.itemMeta(itemMeta);
    }

    @NotNull
    public B removeItemFlags(@NotNull List<ItemFlag> itemFlags) {
        return removeItemFlags(itemFlags.toArray(new ItemFlag[0]));
    }

    @NotNull
    public B hideAttributes() {
        return addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    }

    @NotNull
    public B unbreakable(final boolean state) {
        @Nullable final ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) return (B) this;

        itemMeta.spigot().setUnbreakable(state);

        return this.itemMeta(itemMeta);
    }

    @NotNull
    public B glowing(boolean glowing) {
        @NotNull final Enchantment enchantment = Enchantment.ARROW_INFINITE;

        if(glowing) {
            this.addUnsafeEnchantment(enchantment, 1);
            this.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            this.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
            this.removeEnchantment(enchantment);
        }

        return (B) this;
    }

    @NotNull
    public B skullOwner(@NotNull String owner) {
        @Nullable final ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) return (B) this;

        if(!(itemMeta instanceof SkullMeta)) return (B) this;
        @NotNull SkullMeta skullMeta = (SkullMeta) itemMeta;

        skullMeta.setOwner(owner);

        return this.itemMeta(skullMeta);
    }

    @NotNull
    public B addPotionCustomEffect(@NotNull PotionEffect effect) {
        @Nullable final ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) return (B) this;
        if(!(itemMeta instanceof PotionMeta)) return (B) this;
        @NotNull PotionMeta potionMeta = (PotionMeta) itemMeta;

        potionMeta.setMainEffect(effect.getType());
        potionMeta.addCustomEffect(effect, true);

        return this.itemMeta(potionMeta);
    }

    @NotNull
    public B addFireworkEffect(@NotNull FireworkEffect... fireworkEffect) {
        @Nullable final ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) return (B) this;
        if(!(itemMeta instanceof FireworkMeta)) return (B) this;
        @NotNull FireworkMeta fireworkMeta = (FireworkMeta) itemMeta;

        fireworkMeta.addEffects(fireworkEffect);

        return this.itemMeta(fireworkMeta);
    }

}