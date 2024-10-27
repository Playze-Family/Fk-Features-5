package com.agonkolgeci.fk_features.api.events.custom.inventory;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class AnvilCombineItemsEvent extends InventoryClickEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Getter @Setter private boolean isCancelled;

    @Getter @NotNull private final ItemStack firstItem;
    @Getter @NotNull private final ItemStack secondItem;
    @Getter @NotNull private final ItemStack resultItem;
    
    public AnvilCombineItemsEvent(@NotNull InventoryClickEvent event, @NotNull ItemStack firstItem, @NotNull ItemStack secondItem, @NotNull ItemStack resultItem) {
        super(event.getView(), event.getSlotType(), event.getRawSlot(), event.getClick(), event.getAction());

        this.firstItem = firstItem;
        this.secondItem = secondItem;
        this.resultItem = resultItem;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

}
