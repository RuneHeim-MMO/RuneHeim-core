package org.zuma.examplePlugin.interfaces;

public interface WithAdditionalMeta {
    public String serializeMeta();

    public void deserializeAndApplyMeta(String meta);

}
