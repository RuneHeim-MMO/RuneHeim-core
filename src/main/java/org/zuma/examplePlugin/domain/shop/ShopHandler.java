package org.zuma.examplePlugin.domain.shop;

import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.shop.persistence.ShopRepository;

import java.util.Collection;

public class ShopHandler {
    private final ShopRegistry registry;
    private final ShopFactory factory;
    private final ShopRepository repository;

    public ShopHandler(ShopRegistry registry, ShopRepository repository) {
        this.registry = registry;
        this.repository = repository;
        factory = new ShopFactory();

        startStockReplenishmentLoop();
    }

    public void initializeShops() {
        ItemPriceCalculator.buildCache();
        repository.getAll().forEach(config -> {
            Shop newShop = factory.createShop(config);
            registry.registerShop(newShop.getId(), newShop);
        });
    }

    public void startStockReplenishmentLoop() {
        Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), () -> {
            Collection<Shop> shops = registry.getAll().values();
            long currentTimeMillis = System.currentTimeMillis();

            shops.forEach(shop -> {
                Collection<ShopItem> itemRecords = shop.getStock().values();

                itemRecords.forEach(record -> {
                    boolean toBeReplenished = false;

                    if (record.getStockReplenishmentSeconds() != null) {
                        toBeReplenished = record.getLastReplenishedMillis() == null ||
                                (currentTimeMillis - record.getLastReplenishedMillis()) > (record.getStockReplenishmentSeconds() * 1000);

                    }

                    if (!toBeReplenished) {
                        return;
                    }

                    int currentStock = record.getCurrentStock();
                    int defaultStock = record.getDefaultStock();


                    if (currentStock < defaultStock) {
                        record.setCurrentStock(currentStock + 1);  // replenish stock
                        record.setLastReplenishedMillis(currentTimeMillis);
                        shop.notifyStockSubscribers();
                    } else if (currentStock > defaultStock) {
                        record.setCurrentStock(currentStock - 1);  // reduce excess stock
                        record.setLastReplenishedMillis(currentTimeMillis);
                        shop.notifyStockSubscribers();
                    }


                });
            });


        }, 0, 60L);
    }

}
