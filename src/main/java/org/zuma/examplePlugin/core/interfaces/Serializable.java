package org.zuma.examplePlugin.core.interfaces;

public interface Serializable {
    public String getJsonMeta();

    void deserializeAndApplyMeta();
}
