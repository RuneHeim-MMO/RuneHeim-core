package org.zuma.examplePlugin.core.analytics.impl;

import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.analytics.AnalyticsType;
import org.zuma.examplePlugin.core.analytics.base.BaseContainer;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;

public class PlayerAnalytics extends BaseContainer {
    public PlayerAnalytics(UUID ownerUUID, @Nullable Map<AnalyticsKey, Double> initialValues) {
        super(ownerUUID.toString(), initialValues);
    }

    @Override
    public AnalyticsType type() {
        return AnalyticsType.PLAYER;
    }
}
