package org.zuma.examplePlugin.domain.map;

public enum MarkerType {
    BANKS("bank-markers", "Banks", "https://i.imgur.com/4vif6Tm.png"),
    SHOPS("shop-markers", "Shops", "https://i.imgur.com/D8qq3aO.png"),

    TREE("tree-markers", "Trees", "https://i.imgur.com/y7oRDtE.png"),
    BIRCH("tree-birch-markers", "Birch trees", "https://i.imgur.com/NqA7YIZ.png"),
    MAGIC("tree-magic-markers", "Magic trees", "https://i.imgur.com/4xXRk2l.png"),
    MAHOGANY("tree-mahogany-markers", "Mahogany trees", "https://i.imgur.com/rtpqnVN.png"),
    MAPLE("tree-maple-markers", "Maple trees", "https://i.imgur.com/pFnrqlj.png"),
    YEW("tree-yew-markers", "Yew trees", "https://i.imgur.com/QA7RbKG.png"),

    CRAWLER("fish-crawler-markers", "Crawler fishing spots", "https://i.imgur.com/dkc0hSm.png"),
    EEL("fish-eel-markers", "Eel fishing spots", "https://i.imgur.com/IYVfzC4.png"),
    HERRING("fish-herring-markers", "Herring fishing spots", "https://i.imgur.com/PGTw8MR.png"),
    MINNOW("fish-minnow-markers", "Minnow fishing spots", "https://i.imgur.com/9eE5pfs.png"),
    PIKE("fish-pike-markers", "Pike fishing spots", "https://i.imgur.com/1GDIcYM.png"),
    RAY("fish-ray-markers", "Ray fishing spots", "https://i.imgur.com/1j1QJOp.png"),
    SALMON("fish-salmon-markers", "Salmon fishing spots", "https://i.imgur.com/zOlueVU.png"),
    SHARK("fish-shark-markers", "Shark fishing spots", "https://i.imgur.com/JuajCSD.png"),
    TROUT("fish-trout-markers", "Trout fishing spots", "https://i.imgur.com/c4eZJKc.png"),
    TUNA("fish-tuna-markers", "Tuna fishing spots", "https://i.imgur.com/SgOFKtS.png"),
    TURTLE("fish-turtle-markers", "Turtle fishing spots", "https://i.imgur.com/tazDWqL.png"),

    COPPER("ore-copper-markers", "Copper ore veins", "https://i.imgur.com/2Yk6Ker.png"),
    TIN("ore-tin-markers", "Tin ore veins", "https://i.imgur.com/mxTL6yK.png"),
    COAL("ore-coal-markers", "Coal ore veins", "https://i.imgur.com/fq9fCz9.png"),
    IRON("ore-iron-markers", "Iron ore veins", "https://i.imgur.com/THemPsw.png"),
    MITHRIL("ore-mithril-markers", "Mithril ore veins", "https://i.imgur.com/ctUyoqV.png"),
    ADAMANTITE("ore-adamantite-markers", "Adamantite ore veins", "https://i.imgur.com/XyrJr1T.png"),
    RUNITE("ore-runite-markers", "Runite ore veins", "https://i.imgur.com/7hiGFYY.png"),

    // TODO
    FIRE_SOURCE("fire-source-markers", "Fire sources", ""),
    SMELTING_FURNACE("smelting-furnace-markers", "Smelting furnaces", ""),
    ANVIL("anvil-markers", "Anvils", ""),
    CRAFTING_TABLE("crafting-table-markers", "Crafting tables", ""),
    SPINNING_WHEEL("spinning-wheel-markers", "Spinning wheels", "");


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
