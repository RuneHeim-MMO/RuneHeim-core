package org.zuma.examplePlugin.domain.item.state.meta.v1;

import java.util.List;

public record FairyStateMeta(List<Integer> upgradeIds, double stamina, double experience, boolean canConsumeFruit, int generation) {
}
