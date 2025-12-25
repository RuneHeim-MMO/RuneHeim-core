package org.zuma.examplePlugin.domain.block.meta.v1;

import java.util.List;

public record CommandBlockMeta(List<String> hologramContent, List<String> commands) {
}
