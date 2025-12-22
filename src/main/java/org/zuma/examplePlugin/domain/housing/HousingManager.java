package org.zuma.examplePlugin.domain.housing;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.config.EnvironmentType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.housing.instance.HouseInstance;
import org.zuma.examplePlugin.domain.housing.instance.HouseInstanceCreator;
import org.zuma.examplePlugin.domain.housing.persistence.HousingRepository;
import org.zuma.examplePlugin.domain.housing.records.HouseEntry;
import org.zuma.examplePlugin.domain.housing.template.HouseTemplate;
import org.zuma.examplePlugin.domain.housing.template.HousingMetaLoader;
import org.zuma.examplePlugin.domain.housing.template.TemplateMeta;
import org.zuma.examplePlugin.domain.housing.world.WorldManager;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.schematic.SchematicSpawner;
import org.zuma.examplePlugin.events.player.RunePlayerLoadedEvent;

import java.sql.SQLException;
import java.util.*;

public class HousingManager implements Listener {
    public final static int GRID_SIZE = 128;
    private final static int AUTOSAVE_INTERVAL_MINUTES = 2;

    private final Map<Integer, Map<Integer, HouseInstance>> gridMap; // gridX : (gridZ : house instance)
    private final Map<UUID, HashMap<HousingRegion, HouseInstance>> playerHouseMap; // player uuid : (region : house instance)
    private final Map<UUID, HouseInstance> uuidHouseInstanceMap; // instance uuid : house instance
    private final Map<HouseTemplate, HouseInstance> templatePreviewMap;

    private final HouseInstanceCreator instanceCreator;
    private final HousingMetaLoader houseMetaHandler;
    private final HousingRepository repository;

    private final HashMap<UUID, HashMap<HousingRegion, HouseEntry>> playerHouses; // player uuid : saved house entry

    public HousingManager(WorldManager worldManager, HousingRepository repository) {
        this.gridMap = new HashMap<>();
        this.playerHouseMap = new HashMap<>();
        this.uuidHouseInstanceMap = new HashMap<>();
        this.templatePreviewMap = new HashMap<>();
        this.instanceCreator = new HouseInstanceCreator(this, worldManager, new SchematicSpawner());
        this.houseMetaHandler = new HousingMetaLoader(this);
        this.playerHouses = new HashMap<>();
        this.repository = repository;
    }

    public void init() {
        this.houseMetaHandler.loadTemplatesMeta();
        Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), this::autoSaveAllHouses, AUTOSAVE_INTERVAL_MINUTES * 60 * 20L, AUTOSAVE_INTERVAL_MINUTES * 60 * 20L);
    }

    public HouseInstanceCreator getInstanceCreator() {
        return this.instanceCreator;
    }

    public TemplateMeta getTemplateMeta(HouseTemplate template) {
        return this.houseMetaHandler.getTemplateMetaMap().get(template);
    }

    private void autoSaveAllHouses() {
        List<HouseEntry> housesToSave = new ArrayList<>();

        this.playerHouses.forEach((playerUUID, housesPerRegion) -> {
            housesPerRegion.forEach((region, savedEntry) -> {
                housesToSave.add(savedEntry);
            });
        });

        if (housesToSave.isEmpty()) {
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            try {
                this.repository.savePlayerHouses(housesToSave);
                if (ConfigManager.environment.equals(EnvironmentType.DEVELOPMENT)) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "[PlayerHousing] Auto-saved " + housesToSave.size() + " player houses successfully.");
                }
            } catch (SQLException e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "[PlayerHousing] Failed to auto-save player houses!", e);
            }
        });
    }


    public void teleportToOwnHouse(RunePlayer player, HousingRegion region) {
        HashMap<HousingRegion, HouseInstance> regionMap = playerHouseMap.get(player.getUUID());

        if (regionMap != null) {
            HouseInstance instance = regionMap.get(region);

            if (instance != null) {
                instance.teleportToHouse(player);
                return;
            }
        }

        // Check for match from saved houses
        HashMap<HousingRegion, HouseEntry> housesPerRegion = playerHouses.get(player.getUUID());

        if (housesPerRegion == null) {
            HudService.sendFailMessage(player, Component.text("You do not own a house in this region."));
            return;
        }

        HouseEntry savedEntry = housesPerRegion.get(region);

        if (savedEntry == null) {
            HudService.sendFailMessage(player, Component.text("You do not own a house in this region."));
            return;
        }

        HouseInstance instance = instanceCreator.createInstance(player.getUUID(), savedEntry.uuid(), player.asBukkitPlayer().getName(), savedEntry.template(), false, savedEntry.furniture());
        instance.teleportToHouse(player);
    }

    public boolean doesPlayerOwnHouseInRegion(UUID ownerUUID, HousingRegion region) {
        // Check for match from saved houses
        HashMap<HousingRegion, HouseEntry> housesPerRegion = playerHouses.get(ownerUUID);

        if (housesPerRegion == null) {
            return false;
        }

        HouseEntry savedEntry = housesPerRegion.get(region);

        if (savedEntry == null) {
            return false;
        }

        return true;
    }

    public void removeHouseReference(UUID ownerUUID, HousingRegion region) {
        HashMap<HousingRegion, HouseInstance> regionMap = playerHouseMap.get(ownerUUID);

        if (regionMap == null) {
            return;
        }

        HouseInstance instance = regionMap.get(region);

        if (instance == null) {
            return;
        }

        uuidHouseInstanceMap.remove(instance.getInstanceUUID());
        regionMap.remove(region);
        instance.cleanup();

        int gridX = instance.getGridX();
        int gridZ = instance.getGridZ();

        Map<Integer, HouseInstance> row = gridMap.get(gridX);

        if (row != null) {
            row.remove(gridZ);
        }

        if (regionMap.isEmpty()) {
            playerHouseMap.remove(ownerUUID);
        }
    }

    @Nullable
    public HouseInstance getHousingAt(int x, int z) {
        int gridX = Math.floorDiv(x, GRID_SIZE);
        int gridZ = Math.floorDiv(z, GRID_SIZE);

        Map<Integer, HouseInstance> row = gridMap.get(gridX);

        if (row != null) {
            return row.get(gridZ);
        }

        return null;
    }

    @Nullable
    public HouseInstance getVisitingHouse(RunePlayer visitor) {
        for (Map<HousingRegion, HouseInstance> regionMap : playerHouseMap.values()) {
            for (HouseInstance instance : regionMap.values()) {
                if (instance.getCurrentVisitors().stream().anyMatch(uuid -> visitor.getUUID().equals(uuid))) {
                    return instance;
                }
            }
        }

        return null;
    }

    public List<HouseInstance> getAllRegionHouses(HousingRegion region) {
        List<HouseInstance> houses = new ArrayList<>();

        this.playerHouseMap.values().forEach(regionMap -> {
            HouseInstance instance = regionMap.get(region);
            if (instance != null) {
                houses.add(instance);
            }
        });

        return houses;
    }

    @Nullable
    public HouseInstance getHouseInstanceByOwner(UUID ownerUUID, HousingRegion region) {
        HashMap<HousingRegion, HouseInstance> regionMap = playerHouseMap.get(ownerUUID);

        if (regionMap == null) {
            return null;
        }

        return regionMap.get(region);
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        HashMap<HousingRegion, HouseEntry> existing = this.playerHouses.get(event.getPlayer().getUniqueId());

        if (existing != null) {
            Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
                if (existing.isEmpty()) {
                    return;
                }

                try {
                    this.repository.savePlayerHouses(new ArrayList<>(existing.values()));
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "[PlayerHousing] Saved " + existing.size() + " player houses for player " + event.getPlayer().getName());

                    Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                        this.playerHouses.remove(event.getPlayer().getUniqueId());

                        Arrays.stream(HousingRegion.values()).forEach(region -> {
                            removeHouseReference(event.getPlayer().getUniqueId(), region);
                        });
                    });

                } catch (SQLException e) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "[PlayerHousing] Failed to save player houses for player " + event.getPlayer().getName(), e);
                }
            });
        }

        Arrays.stream(HousingRegion.values()).forEach(region -> {
            this.playerHouseMap.values().stream().map(a -> a.get(region)).filter(Objects::nonNull).forEach(i -> {
                i.getCurrentVisitors().remove(event.getPlayer().getUniqueId());
            });
        });
    }

    @EventHandler
    public void onPlayerConnect(RunePlayerLoadedEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            try {
                List<HouseEntry> houses = this.repository.getPlayerHouses(event.getPlayer().getUUID());
                HashMap<HousingRegion, HouseEntry> housesPerRegion = new HashMap<>();

                for (HouseEntry house : houses) {
                    housesPerRegion.put(house.region(), house);
                }

                if (housesPerRegion.isEmpty()) {
                    return;
                }

                this.playerHouses.put(event.getPlayer().getUUID(), housesPerRegion);

                RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "[PlayerHousing] Loaded " + houses.size() + " player houses for player " + event.getPlayer().asBukkitPlayer().getName());
            } catch (SQLException e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "[PlayerHousing] Failed to load player houses for player " + event.getPlayer().asBukkitPlayer().getName(), e);
            }
        });
    }

    public void buyPlayerHouse(RunePlayer buyer, HousingRegion region, HouseTemplate template) {
        HashMap<HousingRegion, HouseEntry> existing = this.playerHouses.get(buyer.getUUID());

        if (existing != null && existing.containsKey(region)) {
            throw new IllegalStateException("Player already owns a house in this region.");
        }

        HouseEntry newEntry = new HouseEntry(
                UUID.randomUUID(),
                buyer.getUUID(),
                region,
                template,
                new HashMap<>()
        );

        if (existing == null) {
            existing = new HashMap<>();
            this.playerHouses.put(buyer.getUUID(), existing);
        }

        existing.put(region, newEntry);

        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            try {
                this.repository.savePlayerHouses(List.of(newEntry));
                RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "[PlayerHousing] Saved new player house (" + template.getTemplateName() + ") for player " + buyer.asBukkitPlayer().getName());
            } catch (SQLException e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "[PlayerHousing] Failed to save new player house for player " + buyer.asBukkitPlayer().getName(), e);
            }
        });
    }

    @Nullable
    public HouseInstance getInstanceByUUID(UUID uuid) {
        return this.uuidHouseInstanceMap.get(uuid);
    }

    public Map<Integer, Map<Integer, HouseInstance>> getGridMap() {
        return this.gridMap;
    }

    public Map<UUID, HashMap<HousingRegion, HouseInstance>> getPlayerHouseMap() {
        return this.playerHouseMap;
    }

    public Map<UUID, HouseInstance> getUuidHouseInstanceMap() {
        return this.uuidHouseInstanceMap;
    }

    public Map<HouseTemplate, HouseInstance> getTemplatePreviewMap() {
        return templatePreviewMap;
    }
}
