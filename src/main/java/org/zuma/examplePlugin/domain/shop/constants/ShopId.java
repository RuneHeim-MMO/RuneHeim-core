package org.zuma.examplePlugin.domain.shop.constants;

public enum ShopId {
    GENERAL_STORE(1),
    DRAKKAR_BLACKSMITH(2);

    private final int id;

    ShopId(int id) {
        this.id = id;
    }

    public int asInt() {
        return this.id;
    }


}
