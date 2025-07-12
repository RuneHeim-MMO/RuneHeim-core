package org.zuma.examplePlugin.domain.mob.impl;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.SheepWatcher;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.mob.base.CombatantMob;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.meta.CombatantMobMeta;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;
import org.zuma.examplePlugin.domain.skill.SkillLevelRecord;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class ShearableSheep extends GenericCombatant {
    private static final int WOOL_RESPAWN_DELAY_SECONDS = 60;
    private boolean hasWool = true;

    public ShearableSheep(String displayName, List<SkillLevelRecord> skills, List<Modifier> modifiers,
                          @Nullable MobBehavior behavior, double walkSpeed, boolean attacksOnSight, boolean attackWithGroup, int health) {
        super(displayName, null, skills, modifiers, null, walkSpeed, false, false, health, DisguiseType.SHEEP);

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
        hasWool = false;

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            setWoolState(entity, true);
            hasWool = true;
        }, WOOL_RESPAWN_DELAY_SECONDS * 20);
    }

    @Override
    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, RunePlayer player) {
        ItemStack inHand = player.asBukkitPlayer().getInventory().getItemInMainHand();

        if (inHand.getType() == Material.AIR) {
            return;
        }

        AbstractItemType item = RuneQuestPlugin.getItemRegistry().getItem(inHand);

        if (item == null) {
            return;
        }

        if (item.id != ItemId.SHEARS.asInt()) {
            return;
        }


        shearDisguisedSheep(event.getRightClicked(), player);
    }

}
