package org.zuma.examplePlugin.domain.block.impl.furniture;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Lightable;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.List;
import java.util.UUID;

public class LightableBlock extends AbstractInteractableBlock {
    private boolean isLit;

    public LightableBlock(AbstractInteractableBlockMeta meta) {
        super(meta, null, null);
        this.isLit = true;
    }

    @Override
    public void spawn(Material material) {
        super.spawn(material);
        setLitBlockState(isLit);
    }


    private void setLitBlockState(boolean isLit) {
        Block block = getLocation().getBlock();
        BlockData data = block.getBlockData();

        if (data instanceof Lightable lightableData) {
            lightableData.setLit(isLit);
            block.setBlockData(lightableData);

            this.isLit = isLit;

            if (isLit) {
                Hologram hologram = getHologram();

                if (hologram != null) {
                    hologram.delete();
                    setHologram(null);
                }

                SoundType.PLAYER_LIGHT_LIGHTABLE.play(this.getLocation());
            } else {
                setHologram(DHAPI.createHologram("lightable_block_" + UUID.randomUUID(), this.getLocation().clone().add(0.5, 0.5, 0.5), List.of(Icon.FIRE.get() + " ")));
            }
        }
    }

    @Override
    public void onPlayerInteractEvent(RunePlayer player, Action action, PlayerInteractEvent event, AbstractItemType itemInHand) {
        event.setCancelled(true);
        setLitBlockState(!isLit);
    }

    public Location getLocation() {
        return super.getLocation();
    }
}
