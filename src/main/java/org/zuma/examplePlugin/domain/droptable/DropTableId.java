package org.zuma.examplePlugin.domain.droptable;

public enum DropTableId {
    BONES(0),
    COINS_SMALL(1),
    WOOL(2),
    CHICKEN_MEAT(3),
    FEATHERS(4);

    private final int id;

    DropTableId(int id) {
        this.id = id;
    }

    public int asInt() {
        return this.id;
    }
}
