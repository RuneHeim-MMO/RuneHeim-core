package org.zuma.examplePlugin.domain.block.impl;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
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
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.interfaces.DespawnableBlock;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.HologramSide;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.hud.LoadingBarBuilder;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.events.block.CampfireStateChanged;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CampfireBlock extends InventoryOpenerRegularBlock implements DespawnableBlock {
    private final UUID owner;
    private final String initialName, burningName;
    private final int burnSeconds;
    private final boolean soulSkin;
    @Nullable
    private final ItemRecord despawnDrop;
    private int burnLeftSeconds;
    private boolean burning;

    public CampfireBlock(UUID owner, Location location, String initialName, String burningName, boolean soulSkin, int burnSeconds, @Nullable ItemRecord despawnDrop) {
        super(new AbstractInteractableBlockMeta(-1, BlockType.InventoryOpener, BlockFace.NORTH, location, HologramSide.TOP));
        this.owner = owner;
        this.initialName = initialName;
        this.burningName = burningName;
        this.burnSeconds = burnSeconds;
        this.burnLeftSeconds = burnSeconds;
        this.burning = false;
        this.soulSkin = soulSkin;
        this.despawnDrop = despawnDrop;
        super.initializeBlock(InventoryId.FIRE.asInt(), new ArrayList<>(), "", null, soulSkin ? Material.SOUL_CAMPFIRE : Material.CAMPFIRE, null, null);
        setLitBlockState(false);
        super.setDisabled(true);
        updateHologram();
    }

    private void updateHologram() {
        Hologram hologram = getHologram();

        if (hologram != null) {
            getHologram().delete();
        }

        String title = burning ? burningName : initialName;
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

            String iconColor = soulSkin ? "§9" : "§f";

            hologramContent = List.of(
                    iconColor + Icon.FIRE.get() + " " + Color.INTERACTABLE_ENTITY.asSpigotHex() + title,
                    TextUtils.componentToColoredString(progressBar),
                    burnLeftSeconds + " s"
            );
        } else {
            hologramContent = List.of("§8" + Icon.LOG.get() + " " + Color.INTERACTABLE_ENTITY.asSpigotHex() + initialName);
        }

        setHologram(DHAPI.createHologram(UUID.randomUUID().toString(), getHologramLocation(), hologramContent));
    }

    private void startLoop() {
        new BukkitRunnable() {
            @Override
            public void run() {
                burnLeftSeconds -= 1;
                updateHologram();

                if (burnLeftSeconds <= 3) {
                    setLitBlockState(false);
                }

                if (burnLeftSeconds <= 0) {
                    extinguish();
                    this.cancel();
                }
            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0, 20L);

    }

    public void startFire() {
        CampfireStateChanged event = new CampfireStateChanged(this, true);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        this.burning = true;
        super.setDisabled(false);
        setLitBlockState(true);
        updateHologram();
        startLoop();
    }

    public void extinguish() {
        CampfireStateChanged event = new CampfireStateChanged(this, false);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        Hologram hologram = getHologram();

        if (hologram != null) {
            getHologram().delete();
        }

        forceCloseOpenedInventories();
        setLitBlockState(false);
        super.setDisabled(true);
    }

    private void setLitBlockState(boolean isLit) {
        Block block = getLocation().getBlock();

        if (block.getType() == Material.CAMPFIRE || block.getType() == Material.SOUL_CAMPFIRE) {
            BlockData data = block.getBlockData();
            if (data instanceof Campfire campfireData) {
                campfireData.setLit(isLit);
                block.setBlockData(campfireData);
            }
        }
    }


    public void onDespawn() {
        SoundType.CAMPFIRE_DESPAWN.play(this.getLocation());
    }

    public boolean shouldBeDespawned() {
        return burnLeftSeconds <= 0;
    }

    public int despawnSeconds() {
        return 0;
    }

    public ItemRecord dropOnDespawn() {
        return despawnDrop;
    }

    public UUID getOwner() {
        return owner;
    }
}
