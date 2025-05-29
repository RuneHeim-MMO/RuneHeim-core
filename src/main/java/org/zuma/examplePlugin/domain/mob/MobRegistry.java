package org.zuma.examplePlugin.domain.mob;

import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.constants.MobDataKey;
import org.zuma.examplePlugin.domain.mob.constants.MobId;
import org.zuma.examplePlugin.domain.mob.impl.GenericShopkeeper;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MobRegistry {
    private final HashMap<Integer, AbstractMob> mobs = new HashMap<>();

    private final MobBehavior testBehavior = new MobBehavior(
            // Idle sounds - subtle, ambient, or vocal-ish
            List.of(
                    Sound.ENTITY_VILLAGER_AMBIENT,
                    Sound.ENTITY_VILLAGER_TRADE,
                    Sound.ENTITY_PARROT_AMBIENT,
                    Sound.BLOCK_NOTE_BLOCK_BELL,
                    Sound.BLOCK_NOTE_BLOCK_FLUTE
            ),

            // Interaction success - positive UI or bell-like sounds
            List.of(
                    Sound.UI_TOAST_CHALLENGE_COMPLETE,
                    Sound.UI_BUTTON_CLICK,
                    Sound.BLOCK_ANVIL_USE,
                    Sound.BLOCK_BELL_RESONATE
            ),

            // Interaction fail - negative or subtle rejection sounds
            List.of(
                    Sound.BLOCK_CHEST_LOCKED,
                    Sound.BLOCK_WOODEN_TRAPDOOR_CLOSE,
                    Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO
            ),

            // Damage sounds - small "ouch" or clink-like sounds
            List.of(
                    Sound.ENTITY_VILLAGER_HURT,
                    Sound.BLOCK_ANVIL_LAND,
                    Sound.BLOCK_LANTERN_BREAK,
                    Sound.ENTITY_ITEM_PICKUP
            )
    );

    public MobRegistry() {
        this.registerMob(new GenericShopkeeper(MobId.CHESTPLATE_SHOP.asInt(), "Chestplate Shop 0", EquipmentSet.SHOPKEEPER_1, testBehavior, 0));
        this.registerMob(new GenericShopkeeper(MobId.CHESTPLATE_SHOP_DUPE.asInt(), "Chestplate Shop 1", EquipmentSet.SHOPKEEPER_1, testBehavior, 1));
    }

    @Nullable
    public AbstractMob getItem(MobId id) {
        return getById(id.asInt());
    }


    @Nullable
    public AbstractMob getById(int id) {
        return mobs.get(id);
    }

    public Collection<AbstractMob> getAll() {
        return this.mobs.values();
    }

    @Nullable
    public AbstractMob getMob(Entity entity) {
        NamespacedKey key = new NamespacedKey(RuneQuestPlugin.getInstance(), MobDataKey.MOB_ID.asString());
        Integer value = entity.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);

        return value != null ? getById(value) : null;
    }

    public void registerMob(AbstractMob mob) {
        AbstractMob existing = this.mobs.get(mob.getId());

        if (existing != null) {
            throw new Error("[DUPLICATE] Mob with id " + mob.getId() + " already exists.");
        }

        this.mobs.put(mob.getId(), mob);
    }
}
