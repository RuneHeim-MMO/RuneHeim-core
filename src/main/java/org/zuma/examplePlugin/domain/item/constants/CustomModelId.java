package org.zuma.examplePlugin.domain.item.constants;

public enum CustomModelId {
    RED_POTION(100),
    BLUE_POTION(101),
    PURPLE_POTION(102),
    GREEN_POTION(103),
    CYAN_POTION(104),
    YELLOW_POTION(105);


    private final int id;

    CustomModelId(int id) {
        this.id = id;
    }

    public int asInt() {
        return this.id;
    }


}
