package com.agonkolgeci.fk_features.plugin.spawners.items;

import com.agonkolgeci.fk_features.api.config.ConfigEntry;
import com.agonkolgeci.fk_features.plugin.spawners.SpawnersManager;
import com.agonkolgeci.fk_features.utils.minecraft.entities.EntityUtils;
import com.agonkolgeci.fk_features.utils.objects.ObjectUtils;
import com.agonkolgeci.fk_features.utils.objects.structure.CircularQueue;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ItemsSpawner {

    @NotNull private final List<Location> locations;
    @NotNull private final CircularQueue<ItemStack> items;

    @NotNull private final Type type;

    private final int radius;
    private final int interval;

    @Nullable private BukkitTask spawnerTask;

    public ItemsSpawner(@NotNull ConfigEntry configuration) {
        this.locations = configuration.of("locations").keys(c -> c.require("location", "Incorrect Location"));
        this.items = new CircularQueue<>(configuration.of("items").keys(c -> c.require("item", "Incorrect Item")));

        this.type = ObjectUtils.fetchObject(Type.class, configuration.get("type"), "Unknown Items Spawner Type: '%s'");

        this.radius = configuration.require("radius");
        this.interval = configuration.require("interval");
    }

    public void enable(@NotNull SpawnersManager spawnersManager) {
        if(spawnerTask == null) {
            spawnerTask = spawnersManager.getServer().getScheduler().runTaskTimer(spawnersManager.getInstance(), this::spawnItems, 0, ObjectUtils.retrieveTicks(interval));
        }
    }

    public void disable() {
        if(spawnerTask != null) {
            spawnerTask.cancel();
        }
    }

    public void spawnItems() {
        for(@NotNull Location location : locations) {
            @NotNull final List<Player> players = EntityUtils.getNearByEntities(location, radius).stream().filter(entity -> entity instanceof Player).map(entity -> (Player) entity).collect(Collectors.toList());
            if(players.isEmpty()) {
                continue;
            }

            @NotNull final World world = location.getWorld();
            switch (type) {
                case ALL: {
                    for(@NotNull ItemStack itemStack : items) {
                        world.dropItem(location, itemStack);
                    }

                    break;
                }

                case CIRCULAR: {
                    world.dropItem(location, items.next());

                    break;
                }

                case RANDOM: {
                    world.dropItem(location, ObjectUtils.retrieveRandomObject(items));

                    break;
                }
            }

            this.spawnParticle(location);
        }
    }

    protected void spawnParticle(@NotNull Location location) {
        location.getWorld().playEffect(location, Effect.MOBSPAWNER_FLAMES, 0, radius *2);
    }

    public enum Type {

        ALL,
        CIRCULAR,
        RANDOM

    }

}
