package org.zuma.examplePlugin.core.exception;

import org.zuma.examplePlugin.domain.item.constants.ItemType;

public class UnsupportedJsonVersionException extends RuntimeException {
    public UnsupportedJsonVersionException(ItemType type, int version) {
        super("Unsupported JSON meta version: " + version + " for item type: " + type);
    }
}