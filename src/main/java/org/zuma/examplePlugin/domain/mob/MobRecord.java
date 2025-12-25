package org.zuma.examplePlugin.domain.mob;

import org.zuma.examplePlugin.domain.mob.constants.MobType;

public class MobRecord {
    private final MobType type;
    private final int amount;

    public MobRecord(MobType type, int amount) {
        this(type.toString(), amount);
    }

    public MobRecord(String type, int amount) {
        if (amount <= 0) {
            throw new Error("Mob record (mob " + type + ") cannot have negative or 0 amount!");
        }
        this.type = MobType.valueOf(type);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public MobType getType() {
        return type;
    }
}
