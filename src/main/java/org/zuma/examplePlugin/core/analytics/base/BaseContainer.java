package org.zuma.examplePlugin.core.analytics.base;

import org.zuma.examplePlugin.core.analytics.AnalyticsContainer;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseContainer implements AnalyticsContainer {
    private final Map<AnalyticsKey, Double> values;
    private final String key;

    public BaseContainer(String key, @Nullable Map<AnalyticsKey, Double> initialValues) {
        this.key = key;
        this.values = initialValues == null ? new HashMap<>() : initialValues;
    }

    @Override
    public void increment(AnalyticsKey key, double value) {
        synchronized (values) {
            if (values.containsKey(key)) {
                values.put(key, values.get(key) + value);
            } else {
                values.put(key, value);
            }
        }
    }

    @Override
    public void decrement(AnalyticsKey key, double value) {
        synchronized (values) {
            if (values.containsKey(key)) {
                values.put(key, values.get(key) - value);
            } else {
                values.put(key, -value);
            }
        }
    }

    @Override
    public double getValue(AnalyticsKey key) {
        synchronized (values) {
            return values.getOrDefault(key, 0.0);
        }
    }

    @Override
    public Map<AnalyticsKey, Double> getAllValues() {
        synchronized (values) {
            return values;
        }
    }

    public String key() {
        return key;
    }
}
