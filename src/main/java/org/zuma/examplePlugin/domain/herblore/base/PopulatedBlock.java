package org.zuma.examplePlugin.domain.herblore.base;

import org.zuma.examplePlugin.domain.block.impl.ResourceNode;

public record PopulatedBlock(ResourceNode node, String key) { // key - x_z
}
