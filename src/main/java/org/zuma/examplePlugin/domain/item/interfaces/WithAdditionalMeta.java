package org.zuma.examplePlugin.domain.item.interfaces;

public interface WithAdditionalMeta {
    public String serializeMeta();

    public void deserializeAndApplyMeta(String meta);

}
