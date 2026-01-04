package org.zuma.examplePlugin.domain.map;

public enum MarkerType {
    TREE("tree-markers", "Trees", "./assets/markers/tree.webp", false),
    BIRCH("tree-birch-markers", "Birch trees", "./assets/markers/birch.webp", true),
    MAGIC("tree-magic-markers", "Magic trees", "./assets/markers/magic_tree.webp", true),
    MAHOGANY("tree-mahogany-markers", "Mahogany trees", "./assets/markers/mahogany.webp", true),
    MAPLE("tree-maple-markers", "Maple trees", "./assets/markers/maple.webp", true),
    YEW("tree-yew-markers", "Yew trees", "./assets/markers/yew.webp", true),

    CRAWLER("fish-crawler-markers", "Crawler fishing spots", "./assets/markers/crawler.png", true),
    EEL("fish-eel-markers", "Eel fishing spots", "./assets/markers/eel.png", true),
    HERRING("fish-herring-markers", "Herring fishing spots", "./assets/markers/herring.png", true),
    MINNOW("fish-minnow-markers", "Minnow fishing spots", "./assets/markers/minnow.png", true),
    PIKE("fish-pike-markers", "Pike fishing spots", "./assets/markers/pike.png", true),
    RAY("fish-ray-markers", "Ray fishing spots", "./assets/markers/ray.png", true),
    SALMON("fish-salmon-markers", "Salmon fishing spots", "./assets/markers/salmon.png", true),
    SHARK("fish-shark-markers", "Shark fishing spots", "./assets/markers/shark.png", true),
    TROUT("fish-trout-markers", "Trout fishing spots", "./assets/markers/trout.png", true),
    TUNA("fish-tuna-markers", "Tuna fishing spots", "./assets/markers/tuna.png", true),
    TURTLE("fish-turtle-markers", "Turtle fishing spots", "./assets/markers/turtle.png", true),

    COPPER("ore-copper-markers", "Copper ore veins", "./assets/markers/copper_ore.png", true),
    TIN("ore-tin-markers", "Tin ore veins", "./assets/markers/tin_ore.png", true),
    COAL("ore-coal-markers", "Coal ore veins", "./assets/markers/coal_ore.png", true),
    IRON("ore-iron-markers", "Iron ore veins", "./assets/markers/iron_ore.png", true),
    MITHRIL("ore-mithril-markers", "Mithril ore veins", "./assets/markers/mithril_ore.png", true),
    ADAMANT("ore-adamantite-markers", "Adamantite ore veins", "./assets/markers/adamantite_ore.png", true),
    RUNE("ore-runite-markers", "Runite ore veins", "./assets/markers/runite_ore.png", true),
    GOLD("ore-gold-markers", "Gold ore veins", "./assets/markers/gold_ore.png", true),

    FIRE_SOURCE("fire-source-markers", "Fire sources", "./assets/markers/fire_source.png", true),
    SMELTING_FURNACE("smelting-furnace-markers", "Smelting furnaces", "./assets/markers/smelting_furnace.png", true),
    ANVIL("anvil-markers", "Anvils", "./assets/markers/anvil.png", true),
    CRAFTING_TABLE("crafting-table-markers", "Crafting tables", "./assets/markers/crafting_table.png", true),
    SPINNING_WHEEL("spinning-wheel-markers", "Spinning wheels", "./assets/markers/spinning_wheel.png", true),
    FLAX("flax-markers", "Flax", "./assets/markers/flax.png", true),

    BANK_TELLER("bank-tellers", "Bank Tellers", "./assets/markers/bank.png", true),
    GENERAL_STORE("general-stores", "General Stores", "./assets/markers/general_store.png", true),
    BLACKSMITH("blacksmiths", "Blacksmiths", "./assets/markers/blacksmith.png", true),
    JEWELER("jewelers", "Jewelers", "./assets/markers/jeweler.png", true),
    AUCTION("auctioneers", "Auctions", "./assets/markers/auction.png", true),
    MOUNT_SHOP("mount-merchants", "Mount Shops", "./assets/markers/mount_shop.png", true),
    ARMOR_SHOP("armor-merchants", "Armor Shops", "./assets/markers/armor_shop.png", true),
    FOOD_SHOP("food-merchants", "Food Shops", "./assets/markers/food_shop.png", true),
    ARCHERY_SHOP("archery-shops", "Archery Shops", "./assets/markers/archery_shop.png", true),
    FISHING_SHOP("fishing-shops", "Fishing Shops", "./assets/markers/fishing_shop.png", true),
    MINING_SHOP("mining-shops", "Mining Shops", "./assets/markers/mining_shop.png", true),
    AXE_SHOP("axe-shops", "Axe Shops", "./assets/markers/axe_shop.png", true),
    WEAPON_SHOP("weapon-shops", "Weapon Shops", "./assets/markers/weapon_shop.png", true),

    ALTAR("altars", "Altars", "./assets/markers/altar.png", true),
    FUSION_MASTER("fusion-masters", "Fusion Masters", "./assets/markers/fusion_master.png", true),
    CONTRACT_BOARD("contract-boards", "Contract Boards", "./assets/markers/contract_board.png", true),
    QUICK_TRAVEL("quick-travels", "Quick Travel", "./assets/markers/quick_travel.png", true),
    TANNER("tanners", "Tanner", "./assets/markers/tanner.png", true),
    FLETCHING_TABLE("fletching-tables", "Fletching Table", "./assets/markers/fletching_table.png", true),
    HERBLORE_SHOP("herblore-shops", "Herblore Shop", "./assets/markers/herblore_shop.png", true),
    TERRITORY("territories", "Territories", "", true),
    DAILY_REWARD("daily_reward", "Collector of Days", "./assets/markers/daily_rewards.png", true),
    DUNGEON("dungeon", "Dungeons", "./assets/markers/dungeon.png", true),
    PLAYER_HOUSING("concierge", "Player Housing", "./assets/markers/concierge.png", true),

    POTATO("potato-markers", "Potato", "./assets/markers/potato.png", true),
    CARROT("carrot-markers", "Carrot", "./assets/markers/carrot.png", true),


    // Dev
    BIOME_SPAWN_ZONE("biome_spawn_zone", "Biome Spawn Zones", "", true),
    MOB_SPAWN_ZONE("mob_spawn_zone", "Mob Spawn Zones", "", true);


    private final String id, label, iconURL;
    private final boolean defaultVisible;

    MarkerType(String id, String label, String iconURL, boolean defaultVisible) {
        this.id = id;
        this.label = label;
        this.iconURL = iconURL;
        this.defaultVisible = defaultVisible;
    }

    public boolean isDefaultVisible() {
        return defaultVisible;
    }

    public String getLabel() {
        return label;
    }

    public String getId() {
        return id;
    }

    public String getIconURL() {
        return iconURL;
    }
}
