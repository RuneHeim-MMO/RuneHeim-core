package org.zuma.examplePlugin.domain.droptable;

public class DropTableEntry {
    public final int id, min, max, oneInNChance;
    public final Type type;

    public enum Type {
        ITEM, NESTED_DROP_TABLE
    }

    public DropTableEntry(int id, int min, int max, int oneInNChance, Type type) {
        this.id = id;
        this.min = min;
        this.max = max;
        this.oneInNChance = oneInNChance;
        this.type = type;
    }
}
