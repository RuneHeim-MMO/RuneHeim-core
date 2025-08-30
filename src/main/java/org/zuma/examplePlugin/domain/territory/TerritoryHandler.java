package org.zuma.examplePlugin.domain.territory;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.MathUtils;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.territory.TerritoryEnterEvent;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.*;

public class TerritoryHandler {
    private final TerritoryRepository repository;
    private final List<Territory> territoryList;
    private final HashMap<UUID, Territory> lastPlayerTerritory;

    public TerritoryHandler(TerritoryRepository repository) {
        this.repository = repository;
        this.territoryList = new ArrayList<>();
        this.lastPlayerTerritory = new HashMap<>();
    }

    public void initialize() {
        loadTerritories();
        startLoop();
    }

    @Nullable
    public Territory getPlayersTerritory(RunePlayer player) {
        return lastPlayerTerritory.get(player.getUUID());
    }

    public List<Territory> getTerritoryList() {
        return this.territoryList;
    }

    private void loadTerritories() {
        territoryList.addAll(repository.getAll());
    }

    private void startLoop() {
        Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), this::loopTick, 0L, 10L);
    }

    private void loopTick() {
        Set<UUID> keys = new HashSet<>(lastPlayerTerritory.keySet());

        Bukkit.getOnlinePlayers().forEach(player -> lastPlayerTerritory.putIfAbsent(player.getUniqueId(), null));

        for (UUID uuid : keys) {
            Player player = Bukkit.getPlayer(uuid);

            if (player == null || !player.isOnline()) {
                lastPlayerTerritory.remove(uuid);
                continue;
            }

            Territory insideOf = null;
            int lastInsideOfDistance = -1;

            Territory lastTerritory = lastPlayerTerritory.get(uuid);

            for (Territory territory : territoryList) {
                double distance = MathUtils.getLocationsDistanceXZ(player.getLocation(), territory.getCenterPoint());

                if (distance >= territory.getRadius()) {
                    continue;  // skip territories where player is outside radius
                }

                if (lastInsideOfDistance != -1 && lastInsideOfDistance <= distance) {
                    continue;  // skip if we already have a closer territory
                }

                insideOf = territory;
                lastInsideOfDistance = (int) distance;
            }

            lastPlayerTerritory.put(player.getUniqueId(), insideOf);

            if (insideOf != null && (lastTerritory == null || !insideOf.getId().equals(lastTerritory.getId()))) {
                handlePlayerEnterTerritory(player, insideOf);
            }


        }

    }

    private void handlePlayerEnterTerritory(Player player, Territory territory) {
        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);

        if (runePlayer == null) {
            return;
        }

        boolean isFirstDiscovery = !runePlayer.getDiscoveredTerritoryIds().contains(territory.getId());
        TerritoryEnterEvent event = new TerritoryEnterEvent(runePlayer, territory, isFirstDiscovery);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (!isFirstDiscovery) {
            SoundType.TERRITORY_ENTER.play(player, false);
        } else {
            runePlayer.getDiscoveredTerritoryIds().add(territory.getId());
            SoundType.TERRITORY_DISCOVERY.play(player, false);

            Component hoverText = TextUtils.getChatTerritoryHoverComponent(runePlayer, territory);
            player.sendMessage(Icon.INFO.asComponent().append(Component.text("New location discovered: ", Color.INFO.asColor())
                    .append(Icon.MAP.asComponent().append(Component.text(territory.getName() + "!", NamedTextColor.AQUA)).hoverEvent(HoverEvent.showText(hoverText)))));
        }

        player.showTitle(Title.title(
                Component.text(territory.getName()),
                Component.text(territory.getDescription(), Color.ITEM_LORE_DESCRIPTION.asColor()),
                Title.Times.times(
                        Duration.ofSeconds(1),
                        Duration.ofSeconds(2, 5),
                        Duration.ofSeconds(3)
                )
        ));
    }
}
