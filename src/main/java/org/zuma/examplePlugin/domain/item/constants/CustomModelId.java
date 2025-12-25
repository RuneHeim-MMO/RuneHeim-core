package org.zuma.examplePlugin.domain.item.constants;

public enum CustomModelId {
    RED_POTION_SMALL(100),
    BLUE_POTION_SMALL(101),
    PURPLE_POTION_SMALL(102),
    GREEN_POTION_SMALL(103),
    CYAN_POTION_SMALL(104),
    YELLOW_POTION_SMALL(105),
    RED_POTION_MEDIUM(106),
    BLUE_POTION_MEDIUM(107),
    PURPLE_POTION_MEDIUM(108),
    GREEN_POTION_MEDIUM(109),
    CYAN_POTION_MEDIUM(110),
    YELLOW_POTION_MEDIUM(111);


    private final int id;

    CustomModelId(int id) {
        this.id = id;
    }

    public int asInt() {
        return this.id;
    }


}
