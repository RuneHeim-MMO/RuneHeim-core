package org.zuma.examplePlugin.domain.block.impl;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.scheduler.BukkitRunnable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.interfaces.DespawnableBlock;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.HologramSide;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.hud.LoadingBarBuilder;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.constants.ItemId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CampfireBlock extends InventoryOpenerBlock implements DespawnableBlock {
    private final String initialName;
    private final int burnSeconds;
    private int burnLeftSeconds;
    private boolean burning;

    public CampfireBlock(Location location, String initialName, boolean soulSkin, int burnSeconds) {
        super(new AbstractInteractableBlockMeta(-1, BlockType.InventoryOpener, BlockFace.NORTH, location, HologramSide.TOP));
        this.initialName = initialName;
        this.burnSeconds = burnSeconds;
        this.burnLeftSeconds = burnSeconds;
        this.burning = false;
        super.initializeBlock(InventoryId.FIRE.asInt(), new ArrayList<>(), "", null, soulSkin ? Material.SOUL_CAMPFIRE : Material.CAMPFIRE, null, null);
        setIsLit(false);
    }

    private void updateHologram() {
        Hologram hologram = getHologram();

        if (hologram != null) {
            getHologram().delete();
        }

        String title = burning ? "Campfire" : initialName;
        List<String> hologramContent = List.of();

        if (burning) {
            Component progressBar = new LoadingBarBuilder()
                    .barLength(14)
                    .totalSteps(burnSeconds)
                    .currentStep(burnLeftSeconds)
                    .segment(Icon.PROGRESS_SHORT_SEGMENT.get())
                    .start(Icon.PROGRESS_SHORT_START.get())
                    .stop(Icon.PROGRESS_SHORT_END.get())
                    .emptyColor(Color.CAMPFIRE_BAR_EMPTY.asColor())
                    .fillColor(Color.CAMPFIRE_BAR_FILL.asColor())
                    .build();

            hologramContent = List.of(
                    "§f" + Icon.FIRE.get() + " " + Color.INTERACTABLE_ENTITY.asSpigotHex() + title,
                    TextUtils.componentToColoredString(progressBar),
                    burnLeftSeconds + " s"
            );
        } else {
            hologramContent = List.of("§8" + Icon.FIRE.get() + " " + Color.INTERACTABLE_ENTITY.asSpigotHex() + initialName);
        }

        setHologram(DHAPI.createHologram(UUID.randomUUID().toString(), getHologramLocation(), hologramContent));
    }

    private void startLoop() {
        new BukkitRunnable() {
            @Override
            public void run() {
                burnLeftSeconds -= 1;
                updateHologram();

                if (burnLeftSeconds <= 0) {
                    extinguish();
                    this.cancel();
                }
            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0, 20L);

    }

    public void startFire() {
        this.burning = true;
        setIsLit(true);
        updateHologram();
        startLoop();
    }

    private void extinguish() {
        Hologram hologram = getHologram();

        if (hologram != null) {
            getHologram().delete();
        }

        setIsLit(true);
    }

    private void setIsLit(boolean isLit) {
        Block block = getLocation().getBlock();
        if (block.getType() == Material.CAMPFIRE || block.getType() == Material.SOUL_CAMPFIRE) {
            BlockData data = block.getBlockData();
            if (data instanceof Campfire campfireData) {
                campfireData.setLit(isLit);
                block.setBlockData(campfireData);
            }
        }
    }

    @Override
    public boolean shouldBeDespawned() {
        return burnLeftSeconds <= 0;
    }

    @Override
    public int despawnSeconds() {
        return 0;
    }


    @Override
    public ItemRecord dropOnDespawn() {
        return new ItemRecord(ItemId.BURNT_FOOD, 1);
    }
}
