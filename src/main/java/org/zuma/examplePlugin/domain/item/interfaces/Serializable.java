package org.zuma.examplePlugin.domain.item.interfaces;

public interface Serializable {
    public void deserializeAndApplyMeta();

    public String getJsonMeta();
}
