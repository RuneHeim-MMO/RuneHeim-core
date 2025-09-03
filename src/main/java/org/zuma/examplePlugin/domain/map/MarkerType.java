package org.zuma.examplePlugin.domain.map;

public enum MarkerType {
    BANKS("bank-markers", "Banks", "./assets/markers/bank.png"),
    SHOPS("shop-markers", "Shops", "./assets/markers/shop.png"),

    TREE("tree-markers", "Trees", "./assets/markers/tree.png"),
    BIRCH("tree-birch-markers", "Birch trees", "./assets/markers/birch.png"),
    MAGIC("tree-magic-markers", "Magic trees", "./assets/markers/magic_tree.png"),
    MAHOGANY("tree-mahogany-markers", "Mahogany trees", "./assets/markers/mahogany.png"),
    MAPLE("tree-maple-markers", "Maple trees", "./assets/markers/maple.png"),
    YEW("tree-yew-markers", "Yew trees", "./assets/markers/yew.png"),

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
    ADAMANTITE("ore-adamantite-markers", "Adamantite ore veins", "./assets/markers/adamantite_ore.png"),
    RUNITE("ore-runite-markers", "Runite ore veins", "./assets/markers/runite_ore.png"),

    FIRE_SOURCE("fire-source-markers", "Fire sources", "./assets/markers/fire_source.png"),
    SMELTING_FURNACE("smelting-furnace-markers", "Smelting furnaces", "./assets/markers/smelting_furnace.png"),
    ANVIL("anvil-markers", "Anvils", "./assets/markers/anvil.png"),
    CRAFTING_TABLE("crafting-table-markers", "Crafting tables", "./assets/markers/crafting_table.png"),
    SPINNING_WHEEL("spinning-wheel-markers", "Spinning wheels", "./assets/markers/spinning_wheel.png"),
    FLAX("flax-markers", "Flax", "./assets/markers/flax.png"),

    // TODO
    ALTAR("altars", "Altars", ""),
    FUSION_MASTER("fusion-masters", "Fusion Masters", ""),
    CONTRACT_BOARD("contract-boards", "Contract Boards", ""),
    AREAS("areas", "Areas", ""),
    SPAWN_ZONE("spawn_zone", "Mob Spawn Zones", "");


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
