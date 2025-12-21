package org.zuma.examplePlugin.domain.territory;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.AnalyticsUtils;
import org.zuma.examplePlugin.core.utils.MathUtils;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.ambience.sound.AmbientMusicHandler;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.territory.TerritoryEnterEvent;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.*;

public class TerritoryHandler {
    private static final long AMBIENT_SOUND_COOLDOWN_SEC = 30;

    private final TerritoryRepository repository;
    private final List<Territory> territoryList;
    private final HashMap<UUID, Territory> lastPlayerTerritory;
    private final HashMap<UUID, Location> lastPlayerLocation;

    private final AmbientMusicHandler ambientMusicHandler;

    public TerritoryHandler(TerritoryRepository repository) {
        this.repository = repository;
        this.territoryList = new ArrayList<>();
        this.lastPlayerTerritory = new HashMap<>();
        this.lastPlayerLocation = new HashMap<>();
        this.ambientMusicHandler = new AmbientMusicHandler();
    }

    public void initialize() {
        Plugin plugin = RuneQuestPlugin.getInstance();

        loadTerritories();
        startLoop();
        plugin.getServer().getPluginManager().registerEvents(this.ambientMusicHandler, plugin);
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
        Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), this::ambientSoundLoopTick, 0L, 20 * AMBIENT_SOUND_COOLDOWN_SEC);
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


            Territory insideOf = null;
            int lastInsideOfDistance = -1;
            int playerY = player.getLocation().getBlockY();

            Territory lastTerritory = lastPlayerTerritory.get(uuid);

            for (Territory territory : territoryList) {
                double distance = MathUtils.getLocationsDistanceXZ(player.getLocation(), territory.getCenterPoint());

                if (distance >= territory.getRadius()) {
                    continue;  // skip territories where player is outside radius
                }

                if (territory.getMaxHeight() < playerY) {
                    continue; // skip territories where player is above max height
                }

                if (lastInsideOfDistance != -1 && lastInsideOfDistance <= distance) {
                    continue;  // skip if we already have a closer territory
                }

                insideOf = territory;
                lastInsideOfDistance = (int) distance;
            }

            if (insideOf != null && !insideOf.isIgnoreCutscene() && runePlayer.isInCutscene()) {
                continue; // skip handling if player is in cutscene
            }

            if (lastTerritory != null && !lastTerritory.equals(insideOf) && lastTerritory.getAmbientMusic() != null) {
                if (insideOf == null) {
                    // If player went to the wilderness, keep playing, but stop the loop;
                    this.ambientMusicHandler.removePlayerFromLoop(runePlayer);
                } else {
                    if (!(insideOf.getAmbientMusic() != null && lastTerritory.getAmbientMusic().name().equals(insideOf.getAmbientMusic().name()))) {
                        // Different music, stop previous one
                        this.ambientMusicHandler.stopMusic(runePlayer);
                    }

                }
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

    private void ambientSoundLoopTick() {
        this.lastPlayerTerritory.forEach((uuid, territory) -> {
            if (territory != null) {
                RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(uuid);
                if (runePlayer != null && !runePlayer.isDead()) {
                    playTerritoryAmbientSound(runePlayer, territory);
                }
            }
        });
    }


    private void handlePlayerEnterTerritory(Player player, Territory territory, @Nullable Territory lastTerritory) {
        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);

        if (runePlayer == null || runePlayer.isDead() || !runePlayer.meetsRequirements(territory)) {
            return;
        }

        if (territory.getAmbientMusic() != null) {
            this.ambientMusicHandler.startPlayerMusicLoop(territory.getAmbientMusic(), runePlayer);
        }

        boolean isFirstDiscovery = territory.isTemporary() ? !runePlayer.getDiscoveredTerritoryIds().contains(territory.getId()) : false;
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

        if (territory.isSilent()) return; // If territory is silent, do not send any messages

        if (!isFirstDiscovery) {
            SoundType.TERRITORY_ENTER.play(player, false);
            AnalyticsUtils.incrementServerAndPlayer(runePlayer, AnalyticsKey.TERRITORIES_ENTERED, 1);
        } else {
            if (!territory.isTemporary()) {
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

                AnalyticsUtils.incrementServerAndPlayer(runePlayer, AnalyticsKey.TERRITORIES_DISCOVERED, 1);
            }
            AnalyticsUtils.incrementServerAndPlayer(runePlayer, AnalyticsKey.TERRITORIES_ENTERED, 1);
        }

        String titleIcon = territory.isPvpZone() ? territory.isDropItemsOnDeath() ? "§c" + Icon.SKULL_CLOSED_MOUTH.get() : " §f" + Icon.SKULL_CLOSED_MOUTH.get() : "";

        player.showTitle(Title.title(
                Component.text((!titleIcon.isEmpty() ? titleIcon + " " : "") + territory.getName() + (!titleIcon.isEmpty() ? " " + titleIcon : "")),
                Component.text(territory.getDescription(), Color.ITEM_LORE_DESCRIPTION.asColor()),
                Title.Times.times(
                        Duration.ofSeconds(1),
                        Duration.ofSeconds(2, 5),
                        Duration.ofSeconds(3)
                )
        ));

    }

    private void playTerritoryAmbientSound(RunePlayer runePlayer, Territory territory) {
        SoundType sound = territory.getRandomAmbientSound();

        if (sound == null) return;

        sound.play(runePlayer.asBukkitPlayer(), false);
    }
}
