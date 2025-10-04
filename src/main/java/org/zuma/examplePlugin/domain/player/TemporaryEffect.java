package org.zuma.examplePlugin.domain.player;

import org.zuma.examplePlugin.domain.item.StatsEffect;

import javax.annotation.Nullable;

public class TemporaryEffect {
    private final StatsEffect effect;
    private final String expirationMessage;

    @Nullable
    private final String grantedBy; // Key of the entity/item that granted the effect, can be null
    private Integer expiresInSeconds;

    public TemporaryEffect(StatsEffect effect, Integer expiresInSeconds, @Nullable String expirationMessage, @Nullable String grantedBy) {
        this.effect = effect;
        this.expiresInSeconds = expiresInSeconds;
        this.expirationMessage = expirationMessage;
        this.grantedBy = grantedBy;
    }

    public StatsEffect getEffect() {
        return effect;
    }

    public Integer getExpiresInSeconds() {
        return expiresInSeconds;
    }

    public void setExpiresInSeconds(Integer expiresInSeconds) {
        this.expiresInSeconds = expiresInSeconds;
    }

    public boolean isExpired(){
        return this.expiresInSeconds <= 0;
    }

    @Nullable
    public String getExpirationMessage() {
        return expirationMessage;
    }

    @Nullable
    public String getGrantedBy() {
        return grantedBy;
    }
}
