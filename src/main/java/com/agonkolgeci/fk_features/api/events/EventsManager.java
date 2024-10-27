package com.agonkolgeci.fk_features.api.events;

import com.agonkolgeci.fk_features.FkPlugin;
import com.agonkolgeci.fk_features.api.events.trigger.inventory.TriggerAnvilCombineItem;
import com.agonkolgeci.fk_features.common.PluginManager;
import com.agonkolgeci.fk_features.common.PluginModule;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class EventsManager extends PluginModule implements PluginManager {

    @Getter @NotNull private final List<ListenerAdapter> registeredListeners;

    @NotNull private final TriggerAnvilCombineItem triggerAnvilCombineItem;

    public EventsManager(@NotNull FkPlugin plugin) {
        super(plugin);

        this.registeredListeners = new ArrayList<>();

        this.triggerAnvilCombineItem = new TriggerAnvilCombineItem(this);
    }

    @Override
    public void load() throws Exception {
        this.registerEventAdapter(triggerAnvilCombineItem);
    }

    @Override
    public void unload() throws Exception {
        registeredListeners.forEach(this::unregisterEventAdapter);
    }

    @NotNull
    public <L extends Listener> L registerEventAdapter(@NotNull ListenerAdapter listener) {
        pluginManager.registerEvents(listener, instance);
        registeredListeners.add(listener);

        return (L) listener;
    }

    public void unregisterEventAdapter(@NotNull ListenerAdapter listener) {
        HandlerList.unregisterAll(listener);
    }

    @NotNull
    public <E extends Event> E callEvent(@NotNull E event) {
        pluginManager.callEvent(event);
        
        return event;
    }

}
