package org.zuma.examplePlugin.domain.map;

public enum MarkerType {
    TREE("tree-markers", "Trees", "./assets/markers/tree.webp"),
    BIRCH("tree-birch-markers", "Birch trees", "./assets/markers/birch.webp"),
    MAGIC("tree-magic-markers", "Magic trees", "./assets/markers/magic_tree.webp"),
    MAHOGANY("tree-mahogany-markers", "Mahogany trees", "./assets/markers/mahogany.webp"),
    MAPLE("tree-maple-markers", "Maple trees", "./assets/markers/maple.webp"),
    YEW("tree-yew-markers", "Yew trees", "./assets/markers/yew.webp"),

    CRAWLER("fish-crawler-markers", "Crawler fishing spots", "./assets/markers/crawler.png"),
    EEL("fish-eel-markers", "Eel fishing spots", "./assets/markers/eel.png"),
    HERRING("fish-herring-markers", "Herring fishing spots", "./assets/markers/herring.png"),
    MINNOW("fish-minnow-markers", "Minnow fishing spots", "./assets/markers/minnow.png"),
    PIKE("fish-pike-markers", "Pike fishing spots", "./assets/markers/pike.png"),
    RAY("fish-ray-markers", "Ray fishing spots", "./assets/markers/ray.png"),
    SALMON("fish-salmon-markers", "Salmon fishing spots", "./assets/markers/salmon.png"),
    SHARK("fish-shark-markers", "Shark fishing spots", "./assets/markers/shark.png"),
    TROUT("fish-trout-markers", "Trout fishing spots", "./assets/markers/trout.png"),
    TUNA("fish-tuna-markers", "Tuna fishing spots", "./assets/markers/tuna.png"),
    TURTLE("fish-turtle-markers", "Turtle fishing spots", "./assets/markers/turtle.png"),

    COPPER("ore-copper-markers", "Copper ore veins", "./assets/markers/copper_ore.png"),
    TIN("ore-tin-markers", "Tin ore veins", "./assets/markers/tin_ore.png"),
    COAL("ore-coal-markers", "Coal ore veins", "./assets/markers/coal_ore.png"),
    IRON("ore-iron-markers", "Iron ore veins", "./assets/markers/iron_ore.png"),
    MITHRIL("ore-mithril-markers", "Mithril ore veins", "./assets/markers/mithril_ore.png"),
    ADAMANT("ore-adamantite-markers", "Adamantite ore veins", "./assets/markers/adamantite_ore.png"),
    RUNE("ore-runite-markers", "Runite ore veins", "./assets/markers/runite_ore.png"),

    FIRE_SOURCE("fire-source-markers", "Fire sources", "./assets/markers/fire_source.png"),
    SMELTING_FURNACE("smelting-furnace-markers", "Smelting furnaces", "./assets/markers/smelting_furnace.png"),
    ANVIL("anvil-markers", "Anvils", "./assets/markers/anvil.png"),
    CRAFTING_TABLE("crafting-table-markers", "Crafting tables", "./assets/markers/crafting_table.png"),
    SPINNING_WHEEL("spinning-wheel-markers", "Spinning wheels", "./assets/markers/spinning_wheel.png"),
    FLAX("flax-markers", "Flax", "./assets/markers/flax.png"),

    BANK_TELLER("bank-tellers", "Bank Tellers", "./assets/markers/bank.png"),
    GENERAL_STORE("general-stores", "General Stores", "./assets/markers/general_store.png"),
    BLACKSMITH("blacksmiths", "Blacksmiths", "./assets/markers/blacksmith.png"),
    JEWELER("jewelers", "Jewelers", "./assets/markers/jeweler.png"),
    AUCTION("auctioneers", "Auctions", "./assets/markers/auction.png"),
    MOUNT_SHOP("mount-merchants", "Mount Shops", "./assets/markers/mount_shop.png"),
    ARMOR_SHOP("armor-merchants", "Armor Shops", "./assets/markers/armor_shop.png"),
    FOOD_SHOP("food-merchants", "Food Shops", "./assets/markers/food_shop.png"),
    ARCHERY_SHOP("archery-shops", "Archery Shops", "./assets/markers/archery_shop.png"),
    FISHING_SHOP("fishing-shops", "Fishing Shops", "./assets/markers/fishing_shop.png"),
    MINING_SHOP("mining-shops", "Mining Shops", "./assets/markers/mining_shop.png"),
    AXE_SHOP("axe-shops", "Axe Shops", "./assets/markers/axe_shop.png"),
    WEAPON_SHOP("weapon-shops", "Weapon Shops", "./assets/markers/weapon_shop.png"),

    ALTAR("altars", "Altars", "./assets/markers/altar.png"),
    FUSION_MASTER("fusion-masters", "Fusion Masters", "./assets/markers/fusion_master.png"),
    CONTRACT_BOARD("contract-boards", "Contract Boards", "./assets/markers/contract_board.png"),
    QUICK_TRAVEL("quick-travels", "Quick Travel", "./assets/markers/quick_travel.png"),
    TANNER("tanners", "Tanner", "./assets/markers/tanner.png"),
    FLETCHING_TABLE("fletching-tables", "Fletching Table", "./assets/markers/fletching_table.png"),
    HERBLORE_SHOP("herblore-shops", "Herblore Shop", "./assets/markers/herblore_shop.png"),
    TERRITORY("territories", "Territories", ""),

    // Dev
    BIOME_SPAWN_ZONE("biome_spawn_zone", "Biome Spawn Zones", ""),
    MOB_SPAWN_ZONE("mob_spawn_zone", "Mob Spawn Zones", "");


    private final String id, label, iconURL;

    MarkerType(String id, String label, String iconURL) {
        this.id = id;
        this.label = label;
        this.iconURL = iconURL;
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
