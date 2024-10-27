package com.agonkolgeci.fk_features.api.events.trigger.inventory;

import com.agonkolgeci.fk_features.api.events.EventsManager;
import com.agonkolgeci.fk_features.api.events.ListenerAdapter;
import com.agonkolgeci.fk_features.api.events.custom.inventory.AnvilCombineItemsEvent;
import com.agonkolgeci.fk_features.common.PluginAddon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TriggerAnvilCombineItem extends PluginAddon<EventsManager> implements ListenerAdapter {

    public TriggerAnvilCombineItem(@NotNull EventsManager module) {
        super(module);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(@NotNull InventoryClickEvent event) {
        @NotNull final Inventory inventory = event.getInventory();

        if(!(inventory instanceof AnvilInventory)) return;
        if(event.getRawSlot() != 2) return;

        @Nullable final ItemStack firstItem = inventory.getItem(0);
        @Nullable final ItemStack secondItem = inventory.getItem(1);
        @Nullable final ItemStack resultItem = inventory.getItem(2);
        if(firstItem == null || secondItem == null || resultItem == null) return;

        @NotNull final AnvilCombineItemsEvent anvilCombineItemsEvent = new AnvilCombineItemsEvent(event, firstItem, secondItem, resultItem);

        module.callEvent(anvilCombineItemsEvent);

        if(anvilCombineItemsEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

}
