package org.zuma.examplePlugin.domain.mount;

import org.zuma.examplePlugin.domain.item.state.states.MountState;

public record ActiveMountData(MountState state, int level) {
}
