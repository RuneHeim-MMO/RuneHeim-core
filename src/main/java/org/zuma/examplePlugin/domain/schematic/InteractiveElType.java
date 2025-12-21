package org.zuma.examplePlugin.domain.schematic;

public enum InteractiveElType {
    ACTION, // For nodes, block that triggers an action

    // Housing
    SPAWN, // Player spawn point inside the house
    EXIT, // Block that will be replaced with a door
    SLOT, // Storage slot for interactive housing furniture
    DECORATION_SLOT // Decoration slot for non interactive housing furniture
}
