package org.zuma.examplePlugin.core.config;

public enum EnvironmentType {
    PRODUCTION("production"),
    DEVELOPMENT("development"),
    STAGING("staging");

    private final String key;

    EnvironmentType(String key) {
        this.key = key;
    }

    public static EnvironmentType getByKey(String key) {
        for (EnvironmentType type : values()) {
            if (type.getKey().equalsIgnoreCase(key)) {
                return type;
            }
        }

        return PRODUCTION;
    }

    public String getKey() {
        return key;
    }
}
