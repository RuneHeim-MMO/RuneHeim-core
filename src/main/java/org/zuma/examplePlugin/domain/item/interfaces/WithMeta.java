package org.zuma.examplePlugin.domain.item.interfaces;

public interface WithMeta {
    public void deserializeAndApplyMeta();

    public String getJsonMeta();
}
