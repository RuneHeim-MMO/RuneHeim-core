package org.zuma.examplePlugin.domain.mob.impl;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.SheepWatcher;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.SkillLevelRecord;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class ShearableSheep extends GenericCombatant {
    private static final List<Integer> DROP_TABLES_WHEN_HAS_WOOL = List.of(DropTableId.BONES.asInt(), DropTableId.WOOL.asInt());
    private static final List<Integer> DROP_TABLES_WITHOUT_WOOL = List.of(DropTableId.BONES.asInt());

    private static final int WOOL_RESPAWN_DELAY_SECONDS = 60;
    private boolean hasWool = true;

    public ShearableSheep(String displayName, List<SkillLevelRecord> skills, List<Modifier> modifiers,
                          @Nullable MobBehavior behavior, double walkSpeed, int health) {
        super(displayName, null, skills, modifiers, null, walkSpeed, false, false, health, null, null,
                DisguiseType.SHEEP, DROP_TABLES_WHEN_HAS_WOOL);

        HashMap<Attribute, Double> attributes = new HashMap<>();
        attributes.put(Attribute.GENERIC_MOVEMENT_SPEED, walkSpeed);

        super.setAttributes(attributes);
        super.setBehavior(behavior);

    }

    private void setWoolState(Entity entity, boolean hasWool) {
        if (!DisguiseAPI.isDisguised(entity)) {
            return;
        }

        MobDisguise disguise = (MobDisguise) DisguiseAPI.getDisguise(entity);

        if (disguise.getType() != DisguiseType.SHEEP) {
            return;
        }

        SheepWatcher watcher = (SheepWatcher) disguise.getWatcher();
        watcher.setSheared(!hasWool);
        disguise.setWatcher(watcher);

        if (!hasWool) {
            setDropTableIds(DROP_TABLES_WITHOUT_WOOL);
        } else {
            setDropTableIds(DROP_TABLES_WHEN_HAS_WOOL);
        }

    }

    public void shearDisguisedSheep(Entity entity, RunePlayer player) {
        if (!hasWool) {
            return;
        }

        if (super.getBehavior() != null) {
            super.getBehavior().playSound(entity, MobBehavior.MobSoundType.POSITIVE); // shear sound
        }

        setWoolState(entity, false);
        player.giveItemOrDrop(new ItemRecord(ItemId.WOOL, 1));
        player.asBukkitPlayer().swingMainHand();
        hasWool = false;

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            setWoolState(entity, true);
            hasWool = true;
        }, WOOL_RESPAWN_DELAY_SECONDS * 20);
    }

    @Override
    public void reapplyMetaToEntity(@Nullable ChunkLoadEvent event) {
        if (this.getEntity() instanceof LivingEntity livingEntity) {
            this.setLivingEntityMeta(livingEntity);

            if (this.getDisguise() != null) {
                DisguiseAPI.disguiseToAll(this.getEntity(), this.getDisguise().clone());
                setWoolState(this.getEntity(), hasWool);
            }
        }
    }

    @Override
    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, RunePlayer player) {
        ItemStack inHand = player.asBukkitPlayer().getInventory().getItemInMainHand();

        if (inHand.getType() == Material.AIR) {
            return;
        }

        AbstractItemType item = RuneQuestPlugin.getItemRegistry().getType(inHand);

        if (item == null) {
            return;
        }

        if (item.id != ItemId.SHEARS.asInt()) {
            return;
        }


        shearDisguisedSheep(event.getRightClicked(), player);
    }

}
