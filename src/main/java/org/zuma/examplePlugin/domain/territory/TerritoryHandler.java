package org.zuma.examplePlugin.domain.territory;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.MathUtils;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.territory.TerritoryEnterEvent;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.*;

public class TerritoryHandler {
    private final TerritoryRepository repository;
    private final List<Territory> territoryList;
    private final HashMap<UUID, Territory> lastPlayerTerritory;
    private final HashMap<UUID, Location> lastPlayerLocation;

    public TerritoryHandler(TerritoryRepository repository) {
        this.repository = repository;
        this.territoryList = new ArrayList<>();
        this.lastPlayerTerritory = new HashMap<>();
        this.lastPlayerLocation = new HashMap<>();
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
            RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(uuid);

            if (player == null || !player.isOnline() || runePlayer == null) {
                lastPlayerTerritory.remove(uuid);
                lastPlayerLocation.remove(uuid);
                continue;
            }

            if (runePlayer.isInCutscene()) {
                continue; // skip handling if player is in cutscene
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

            if (insideOf == null) {
                lastPlayerTerritory.put(player.getUniqueId(), null);
                lastPlayerLocation.put(player.getUniqueId(), player.getLocation());

                if (lastTerritory != null && lastTerritory.isPvpZone()) {
                    SoundType.PVP_TERRITORY_LEAVE.play(player, false);
                }
                continue;
            }

            // Check if player meets requirements to be in the territory
            if (!runePlayer.meetsRequirements(insideOf)) {
                Location lastLocation = lastPlayerLocation.get(player.getUniqueId());

                if (lastLocation == null) {
                    if (lastTerritory == null || lastTerritory.getSpawnPoint() == null) {
                        // No last location, no last territory or last territory has no spawn point, teleport to drakkar spawn
                        player.teleport(new Location(Bukkit.getWorld("world"), 192, 73, -71));
                    } else {
                        // Teleport to last territory spawn point
                        player.teleport(lastTerritory.getSpawnPoint());
                    }
                } else {
                    Location safeLocation = lastLocation.clone();
                    safeLocation.setYaw(player.getYaw());
                    safeLocation.setPitch(player.getPitch());

                    player.teleport(safeLocation);
                }

                if (insideOf.getEntryDenyMessage() != null) {
                    HudService.sendFailMessage(runePlayer, Component.text(insideOf.getEntryDenyMessage()));
                }

                continue; // Skip further processing for this player
            }

            if ((lastTerritory == null || !insideOf.getId().equals(lastTerritory.getId()))) {
                handlePlayerEnterTerritory(player, insideOf, lastTerritory);
            }

            lastPlayerTerritory.put(player.getUniqueId(), insideOf);
            lastPlayerLocation.put(player.getUniqueId(), player.getLocation());

        }

    }

    private void handlePlayerEnterTerritory(Player player, Territory territory, @Nullable Territory lastTerritory) {
        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);

        if (runePlayer == null || runePlayer.isDead() || !runePlayer.meetsRequirements(territory)) {
            return;
        }

        boolean isFirstDiscovery = !runePlayer.getDiscoveredTerritoryIds().contains(territory.getId());
        TerritoryEnterEvent event = new TerritoryEnterEvent(runePlayer, territory, isFirstDiscovery);
        Bukkit.getServer().getPluginManager().callEvent(event);

        // If new territory is PVP zone
        if (territory.isPvpZone()) {
            SoundType.PVP_TERRITORY_ENTER.play(player, false);
        }

        // If last territory was PVP zone and new one is not, play leave sound
        if (lastTerritory != null && lastTerritory.isPvpZone() && !territory.isPvpZone()) {
            SoundType.PVP_TERRITORY_LEAVE.play(player, false);
        }

        if (!isFirstDiscovery) {
            SoundType.TERRITORY_ENTER.play(player, false);
        } else {
            runePlayer.getDiscoveredTerritoryIds().add(territory.getId());
            SoundType.TERRITORY_DISCOVERY.play(player, false);

            Component hoverText = TextUtils.getChatTerritoryHoverComponent(runePlayer, territory);
            player.sendMessage(Icon.INFO.asComponent().append(Component.text("New location discovered: ", Color.INFO.asColor())
                    .append(Icon.MAP.asComponent().append(Component.text(territory.getName() + "!", NamedTextColor.AQUA)).hoverEvent(HoverEvent.showText(hoverText)))));

            if (territory.getFirstTimeDiscoveryLore() != null) {
                Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                    if (!player.isOnline()) return;

                    player.showTitle(Title.title(
                            Component.empty(),
                            Component.text(territory.getFirstTimeDiscoveryLore(), NamedTextColor.GRAY),
                            Title.Times.times(
                                    Duration.ofSeconds(1),
                                    Duration.ofSeconds(3, 5),
                                    Duration.ofSeconds(3)
                            )
                    ));

                    SoundType.TERRITORY_ENTER.play(player, false);
                }, 120);

            }
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
