package org.zuma.examplePlugin.domain.item.state.meta;

import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.item.state.StateType;

public record AbstractStateMeta(StateType type, VersionedJson json) {

}
