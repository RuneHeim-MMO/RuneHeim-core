package org.zuma.examplePlugin.core.analytics.impl;

import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.analytics.AnalyticsType;
import org.zuma.examplePlugin.core.analytics.base.BaseContainer;

import javax.annotation.Nullable;
import java.util.Map;

public class ServerAnalytics extends BaseContainer {
    public ServerAnalytics(@Nullable Map<AnalyticsKey, Double> initialValues) {
        super(String.valueOf(ConfigManager.getServerId()), initialValues);
    }

    @Override
    public AnalyticsType type() {
        return AnalyticsType.SERVER;
    }
}
