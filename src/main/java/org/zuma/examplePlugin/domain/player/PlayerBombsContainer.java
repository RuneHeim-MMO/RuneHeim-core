package org.zuma.examplePlugin.domain.player;

import org.zuma.examplePlugin.domain.modifier.scopes.server.ServerModifierType;

import java.util.EnumMap;

public class PlayerBombsContainer {
    private int doubleXpBombs, increasedMobDropsBombs, increasedResourceDropsBombs, increasedPerfectStrikeBombs;

    public PlayerBombsContainer(int doubleXpBombs, int increasedMobDropsBombs, int increasedResourceDropsBombs, int increasedPerfectStrikeBombs) {
        this.doubleXpBombs = doubleXpBombs;
        this.increasedMobDropsBombs = increasedMobDropsBombs;
        this.increasedResourceDropsBombs = increasedResourceDropsBombs;
        this.increasedPerfectStrikeBombs = increasedPerfectStrikeBombs;
    }

    public int getDoubleXpBombs() {
        return doubleXpBombs;
    }

    public void setDoubleXpBombs(int doubleXpBombs) {
        this.doubleXpBombs = doubleXpBombs;
    }

    public int getIncreasedMobDropsBombs() {
        return increasedMobDropsBombs;
    }

    public void setIncreasedMobDropsBombs(int increasedMobDropsBombs) {
        this.increasedMobDropsBombs = increasedMobDropsBombs;
    }

    public int getIncreasedResourceDropsBombs() {
        return increasedResourceDropsBombs;
    }

    public void setIncreasedResourceDropsBombs(int increasedResourceDropsBombs) {
        this.increasedResourceDropsBombs = increasedResourceDropsBombs;
    }

    public int getIncreasedPerfectStrikeBombs() {
        return increasedPerfectStrikeBombs;
    }

    public void setIncreasedPerfectStrikeBombs(int increasedPerfectStrikeBombs) {
        this.increasedPerfectStrikeBombs = increasedPerfectStrikeBombs;
    }

    public EnumMap<ServerModifierType, Integer> getBombsAmounts() {
        EnumMap<ServerModifierType, Integer> amounts = new EnumMap<>(ServerModifierType.class);

        amounts.put(ServerModifierType.DOUBLE_XP, this.getDoubleXpBombs());
        amounts.put(ServerModifierType.INCREASED_MOB_DROPS, this.getIncreasedMobDropsBombs());
        amounts.put(ServerModifierType.INCREASED_RESOURCE_DROPS, this.getIncreasedResourceDropsBombs());
        amounts.put(ServerModifierType.INCREASED_PERFECT_STRIKE, this.getIncreasedPerfectStrikeBombs());

        return amounts;
    }

    public boolean attemptDeductPlayerBomb(ServerModifierType type) {
        EnumMap<ServerModifierType, Integer> amounts = getBombsAmounts();
        PlayerBombsContainer container = this;

        int currentAmount = amounts.getOrDefault(type, 0);

        if (currentAmount <= 0) {
            return false;
        }

        switch (type) {
            case DOUBLE_XP -> {
                container.setDoubleXpBombs(container.getDoubleXpBombs() - 1);
                return true;
            }
            case INCREASED_MOB_DROPS -> {
                container.setIncreasedMobDropsBombs(container.getIncreasedMobDropsBombs() - 1);
                return true;
            }
            case INCREASED_RESOURCE_DROPS -> {
                container.setIncreasedResourceDropsBombs(container.getIncreasedResourceDropsBombs() - 1);
                return true;
            }
            case INCREASED_PERFECT_STRIKE -> {
                container.setIncreasedPerfectStrikeBombs(container.getIncreasedPerfectStrikeBombs() - 1);
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    public void addBomb(ServerModifierType type, int amount) {
        switch (type) {
            case DOUBLE_XP -> this.doubleXpBombs += amount;
            case INCREASED_MOB_DROPS -> this.increasedMobDropsBombs += amount;
            case INCREASED_RESOURCE_DROPS -> this.increasedResourceDropsBombs += amount;
            case INCREASED_PERFECT_STRIKE -> this.increasedPerfectStrikeBombs += amount;
        }
    }
}
