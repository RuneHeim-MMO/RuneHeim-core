package org.zuma.examplePlugin.domain.block.impl.trigger;

public interface Trigger {

    void startStateResetCountdown();

    String getGroupId();

    boolean isTriggered();

}
