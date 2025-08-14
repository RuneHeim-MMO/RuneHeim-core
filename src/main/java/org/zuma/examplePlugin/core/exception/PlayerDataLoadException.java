package org.zuma.examplePlugin.core.exception;


public class PlayerDataLoadException extends Exception {
    private final boolean critical;

    public PlayerDataLoadException(String msg, boolean critical) {
        super(msg);
        this.critical = critical;
    }

    public boolean isCritical() {
        return critical;
    }
}