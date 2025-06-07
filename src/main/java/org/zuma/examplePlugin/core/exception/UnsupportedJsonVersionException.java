package org.zuma.examplePlugin.core.exception;

public class UnsupportedJsonVersionException extends RuntimeException {
    public UnsupportedJsonVersionException(String type, int version) {
        super("Unsupported JSON meta version: " + version + " for item type: " + type);
    }
}