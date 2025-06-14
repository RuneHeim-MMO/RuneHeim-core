package org.zuma.examplePlugin.domain.mob;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.constants.MobDataKey;
import org.zuma.examplePlugin.domain.mob.constants.MobId;
import org.zuma.examplePlugin.domain.mob.impl.GenericShopkeeper;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;

public class MobRegistry {
    private final HashMap<Integer, AbstractMob> mobs = new HashMap<>();


    public MobRegistry() {
        this.registerMob(new GenericShopkeeper(1, "Chestplate Shop 0", EquipmentSet.SHOPKEEPER_1, MobBehaviors.VILLAGER_MAN_1.getBehavior(), "shopkeeper", 0, false));
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
