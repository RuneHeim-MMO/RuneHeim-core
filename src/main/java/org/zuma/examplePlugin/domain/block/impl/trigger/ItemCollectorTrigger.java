package org.zuma.examplePlugin.domain.block.impl.trigger;

import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.*;

public class ItemCollectorTrigger extends AbstractInteractableBlock {
    private final String groupId, triggeredEntityId;
    private final List<ItemRecord> requiredItems;
    private final HashMap<Integer, Integer> collectedItems = new HashMap<>();
    private final int triggeredDurationSeconds;
    private final Location particleLoc;
    private boolean isTriggered;

    public ItemCollectorTrigger(AbstractInteractableBlockMeta meta, String groupId, String triggeredEntityId, int triggeredDurationSeconds, List<ItemRecord> requiredItems) {
        super(meta, null, null);
        this.groupId = groupId;
        this.triggeredEntityId = triggeredEntityId;
        this.requiredItems = requiredItems;
        this.triggeredDurationSeconds = triggeredDurationSeconds;
        this.isTriggered = false;
        this.particleLoc = this.getLocation().clone();
        particleLoc.add(0.5, 1.0, 0.5);
    }

    public List<ItemRecord> getRequiredItems() {
        return requiredItems;
    }

    public String getGroupId() {
        return groupId;
    }

    public boolean isTriggered() {
        return isTriggered;
    }

    @Override
    public void onPlayerInteractEvent(RunePlayer player, Action action, PlayerInteractEvent event, AbstractItemType itemInHand) {
        event.setCancelled(true);

        if (isTriggered) {
            SoundType.ITEM_COLLECTOR_REJECT.play(getLocation());
            return;
        }

        ItemStack inHand = event.getItem();
        if (inHand == null) {
            SoundType.ITEM_COLLECTOR_REJECT.play(getLocation());
            return;
        }

        AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(inHand);
        if (type == null) {
            SoundType.ITEM_COLLECTOR_REJECT.play(getLocation());
            return;
        }

        int itemId = type.id;

        // ✅ Precompute and reuse results
        Optional<ItemRecord> requiredOpt = requiredItems.stream()
                .filter(record -> record.getItemId() == itemId)
                .findFirst();

        if (requiredOpt.isEmpty()) {
            SoundType.ITEM_COLLECTOR_REJECT.play(getLocation());
            return;
        }

        ItemRecord requiredRecord = requiredOpt.get();
        int alreadyCollected = collectedItems.getOrDefault(itemId, 0);
        int stillNeeded = requiredRecord.getAmount() - alreadyCollected;

        // ✅ Nothing needed? Early return
        if (stillNeeded <= 0) {
            SoundType.ITEM_COLLECTOR_REJECT.play(getLocation());
            return;
        }

        // ✅ Use min() once, store in variable for consistency
        int toCollect = Math.min(inHand.getAmount(), stillNeeded);

        // ✅ Perform item removal and update state
        boolean removeSuccess = PlayerUtils.removeItems(player, List.of(new ItemRecord(itemId, toCollect)));

        if (!removeSuccess) {
            SoundType.ITEM_COLLECTOR_REJECT.play(getLocation());
            return;
        }

        collectedItems.put(itemId, alreadyCollected + toCollect);

        SoundType.ITEM_COLLECTOR_ACCEPT.play(getLocation());
        particleLoc.getWorld().spawnParticle(Particle.CRIMSON_SPORE, particleLoc, 3, 0.3, 0.3, 0.3, 0.01);
        updateHologram();

        // ✅ Check completion only once, with allMatch short-circuiting
        boolean complete = requiredItems.stream()
                .allMatch(record -> collectedItems.getOrDefault(record.getItemId(), 0) >= record.getAmount());

        if (complete) {
            SoundType.ITEM_COLLECTOR_FULL.play(getLocation());
            particleLoc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, particleLoc, 3, 0.3, 0.3, 0.3, 0.01);

            this.isTriggered = true;
            RuneQuestPlugin.getBlockManager().getTriggeredBlocksHandler().onTriggerTriggered(groupId, triggeredEntityId);
        }
    }

    public void startStateResetCountdown() {
        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            particleLoc.getWorld().spawnParticle(Particle.SMOKE, particleLoc, 3, 0.3, 0.3, 0.3, 0.01);

            this.isTriggered = false;
            collectedItems.clear();
            SoundType.ITEM_COLLECTOR_RESET.play(getLocation());
            RuneQuestPlugin.getBlockManager().getTriggeredBlocksHandler().onTriggerTriggered(groupId, triggeredEntityId);
            updateHologram();
        }, triggeredDurationSeconds * 20L);

        long period = 5L + (long) (Math.random() * 5L);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (collectedItems.isEmpty()) {
                    this.cancel();
                }

                SoundType.ITEM_COLLECTOR_TICKING.play(getLocation(), (float) (Math.random() * 0.5));
            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 20L, period);
    }

    private void initializeHologram() {
        List<String> hologramContent = new ArrayList<>(List.of("Required Items"));

        this.requiredItems.forEach(req -> {
            hologramContent.add("");
        });

        setHologram(DHAPI.createHologram(UUID.randomUUID().toString(), getHologramLocation(), hologramContent));
        updateHologram();

        if (this.requiredItems.size() == 1) {
            AbstractItemType itemType = RuneQuestPlugin.getItemRegistry().getType(this.requiredItems.getFirst().getItemId());
            if (itemType == null) return;
            ItemStack displayItemStack = itemType.createItemStackOfType();
            DHAPI.addHologramLine(this.getHologram(), displayItemStack);
        }
    }

    private void updateHologram() {
        if (getHologram() != null) {
            int requirementsLength = requiredItems.size();

            for (int i = 0; i < requirementsLength; i++) {
                ItemRecord req = requiredItems.get(i);
                AbstractItemType itemType = RuneQuestPlugin.getItemRegistry().getType(req.getItemId());
                if (itemType == null) continue;

                boolean isCollected = collectedItems.getOrDefault(req.getItemId(), 0) >= req.getAmount();
                String lineContent = (isCollected ? Color.SUCCESS : Color.ERROR).asSpigotHex() +
                        itemType.displayName + (isCollected ? Color.SUCCESS : Color.ERROR_LIGHT).asSpigotHex() + " " + collectedItems.getOrDefault(req.getItemId(), 0) + "/" + req.getAmount();

                getHologram().getPage(0).setLine(i + 1, lineContent);
            }
        }

    }

    @Override
    public void spawn(Material material) {
        this.setFace(BlockFace.UP);
        super.spawn(Material.DROPPER);
    }


    public void initializeBlock() {
        initializeHologram();
        this.spawn(Material.DROPPER);
    }
}
