package org.zuma.examplePlugin.core.interfaces;

@FunctionalInterface
public interface Callback<T> {
    void onComplete(T result);

    static <T> Callback<T> noop() {
        return result -> {
        };
    }

}
