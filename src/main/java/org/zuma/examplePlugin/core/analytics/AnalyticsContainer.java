package org.zuma.examplePlugin.core.analytics;

import java.util.Map;

public interface AnalyticsContainer {
    void increment(AnalyticsKey key, double value);

    void decrement(AnalyticsKey key, double value);

    double getValue(AnalyticsKey key);

    Map<AnalyticsKey, Double> getAllValues();

    AnalyticsType type();

    String key();
}
