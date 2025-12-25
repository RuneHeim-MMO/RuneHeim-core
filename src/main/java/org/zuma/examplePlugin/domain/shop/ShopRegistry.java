package org.zuma.examplePlugin.domain.shop;

import javax.annotation.Nullable;
import java.util.HashMap;

public class ShopRegistry {
    private final HashMap<Integer, Shop> shops = new HashMap<>();

    public ShopRegistry() {
    }

    public void registerShop(int id, Shop shop) {
        shops.put(id, shop);
    }

    @Nullable
    public Shop getShop(int id) {
        return shops.get(id);
    }

    public HashMap<Integer, Shop> getAll() {
        return this.shops;
    }

}
