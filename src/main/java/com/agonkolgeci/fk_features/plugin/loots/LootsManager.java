package com.agonkolgeci.fk_features.plugin.loots;

import com.agonkolgeci.fk_features.FkPlugin;
import com.agonkolgeci.fk_features.api.config.ConfigEntry;
import com.agonkolgeci.fk_features.api.events.ListenerAdapter;
import com.agonkolgeci.fk_features.api.messages.MessagesAdapter;
import com.agonkolgeci.fk_features.common.PluginManager;
import com.agonkolgeci.fk_features.common.PluginModule;
import com.agonkolgeci.fk_features.plugin.players.FkPlayer;
import com.agonkolgeci.fk_features.utils.minecraft.inventory.InventoryUtils;
import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEventSource;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class LootsManager extends PluginModule implements PluginManager, ListenerAdapter, MessagesAdapter {

    @Getter @NotNull private final ConfigEntry configuration;

    @NotNull private final URL wiki;
    @NotNull private final List<LootableBlock> blocks;

    public LootsManager(@NotNull FkPlugin plugin) throws MalformedURLException {
        super(plugin);

        this.configuration = plugin.getConfigManager().of("loots");

        this.wiki = new URL(configuration.require("wiki"));
        this.blocks = retrieveBlocks(configuration);
    }

    @NotNull
    public static List<LootableBlock> retrieveBlocks(@NotNull ConfigEntry configuration) {
        return configuration.of("blocks").keys(LootableBlock::new);
    }

    @Override
    public void load() {
        plugin.getEventsController().registerEventAdapter(this);
    }

    @Override
    public void unload() {
        plugin.getEventsController().unregisterEventAdapter(this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(@NotNull BlockBreakEvent event) {
        @NotNull final FkPlayer fkPlayer = plugin.getPlayersManager().getFkPlayer(event.getPlayer());
        if(fkPlayer.getEntity().getGameMode() != GameMode.SURVIVAL) return;

        @NotNull final Block block = event.getBlock();
        @NotNull final Material material = block.getType();

        @NotNull final List<LootableBlock> lootableBlocks = blocks.stream().filter(lb -> lb.getType() == material).collect(Collectors.toList());
        for(@NotNull LootableBlock lootableBlock : lootableBlocks) {
            final double randomValue1 = Math.random();

            if(randomValue1 < lootableBlock.getProbability()) {
                final double totalProbability = lootableBlock.getItems().stream().mapToDouble(LootableItem::getProbability).sum();
                final double randomValue2 = Math.random() * totalProbability;
                double cumulativeProbability = 0;

                for(@NotNull LootableItem lootableItem : lootableBlock.getItems()) {
                    cumulativeProbability += lootableItem.getProbability();

                    if(randomValue2 < cumulativeProbability) {
                        @NotNull final ItemStack lootableItemStack = lootableItem.getItemStack();

                        block.getWorld().dropItemNaturally(block.getLocation().clone().add(.5, .5, .5), lootableItemStack.clone());

                        sendInfo(fkPlayer.audience(), Component
                                .text("Vous avez trouvé un nouveau")
                                .appendSpace()
                                .append(Component.text("trésor", NamedTextColor.YELLOW))
                                .appendSpace()
                                .append(Component.text("!"))
                        );

                        break;
                    }
                }
            }
        }
    }

    @Override
    public @NotNull Component getLabel() {
        return Component.text("Loots", NamedTextColor.GREEN).hoverEvent(HOVER_EVENT_ENCOURAGE_CLICK).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, wiki.toString()));
    }
}
