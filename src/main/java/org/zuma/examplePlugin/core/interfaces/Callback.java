package org.zuma.examplePlugin.core.interfaces;

@FunctionalInterface
public interface Callback<T> {
    static <T> Callback<T> noop() {
        return result -> {
        };
    }

    void onComplete(T result);

}
