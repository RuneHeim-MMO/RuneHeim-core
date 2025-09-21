package org.zuma.examplePlugin.domain.shop.constants;

public enum ShopId {
    GENERAL_STORE(1),
    DRAKKAR_BLACKSMITH(2),
    INVESTMENT_BANKER(3),
    JEWLER(4),
    DRAKKAR_ARMOR_MERCHANT(5),
    BASIC_FOOD_STALL(6),
    DRAKKAR_GUARD_ARCHER_MERCHANT(7);

    private final int id;

    ShopId(int id) {
        this.id = id;
    }

    public int asInt() {
        return this.id;
    }


}
