package org.zuma.examplePlugin.core.config;

public enum WhitelistMode {
    OP_ONLY("OP_ONLY"), // Allows only ops
    STAGING("STAGING"), // Allows only players with permission "runeheim.whitelist.staging"
    LOCKDOWN("LOCKDOWN"), // Only players with permission "runeheim.whitelist.lockdown"
    NONE("NONE"); // No whitelist

    private final String key;

    WhitelistMode(String key) {
        this.key = key;
    }

    public static WhitelistMode getByKey(String key) {
        for (WhitelistMode type : values()) {
            if (type.getKey().equalsIgnoreCase(key)) {
                return type;
            }
        }

        return NONE;
    }

    public String getKey() {
        return key;
    }
}
