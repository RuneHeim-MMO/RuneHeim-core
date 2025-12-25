package org.zuma.examplePlugin.domain.player;

import org.zuma.examplePlugin.domain.modifier.scopes.server.ServerModifierType;

import java.util.EnumMap;

public class PlayerBombsContainer {
    private int doubleCombatXPBombs, doubleSkillXPBombs, increasedMobDropsBombs, increasedResourceDropsBombs, increasedPerfectStrikeBombs, megaphoneUses;

    public PlayerBombsContainer(int doubleCombatXPBombs, int doubleSkillXPBombs, int increasedMobDropsBombs, int increasedResourceDropsBombs, int increasedPerfectStrikeBombs, int megaphoneUses) {
        this.doubleCombatXPBombs = doubleCombatXPBombs;
        this.doubleSkillXPBombs = doubleSkillXPBombs;
        this.increasedMobDropsBombs = increasedMobDropsBombs;
        this.increasedResourceDropsBombs = increasedResourceDropsBombs;
        this.increasedPerfectStrikeBombs = increasedPerfectStrikeBombs;
        this.megaphoneUses = megaphoneUses;
    }

    public int getMegaphoneUses() {
        return megaphoneUses;
    }

    public void setMegaphoneUses(int megaphoneUses) {
        this.megaphoneUses = megaphoneUses;
    }

    public int getDoubleCombatXPBombs() {
        return doubleCombatXPBombs;
    }

    public void setDoubleCombatXPBombs(int doubleCombatXPBombs) {
        this.doubleCombatXPBombs = doubleCombatXPBombs;
    }

    public int getDoubleSkillXPBombs() {
        return doubleSkillXPBombs;
    }

    public void setDoubleSkillXPBombs(int doubleSkillXPBombs) {
        this.doubleSkillXPBombs = doubleSkillXPBombs;
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

        amounts.put(ServerModifierType.DOUBLE_COMBAT_XP, doubleCombatXPBombs);
        amounts.put(ServerModifierType.DOUBLE_SKILL_XP, doubleSkillXPBombs);
        amounts.put(ServerModifierType.INCREASED_MOB_DROPS, increasedMobDropsBombs);
        amounts.put(ServerModifierType.INCREASED_RESOURCE_DROPS, increasedResourceDropsBombs);
        amounts.put(ServerModifierType.INCREASED_PERFECT_STRIKE, increasedPerfectStrikeBombs);

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
            case DOUBLE_COMBAT_XP -> {
                container.setDoubleCombatXPBombs(container.getDoubleCombatXPBombs() - 1);
                return true;
            }
            case DOUBLE_SKILL_XP -> {
                container.setDoubleSkillXPBombs(container.getDoubleSkillXPBombs() - 1);
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
            case DOUBLE_COMBAT_XP -> this.doubleCombatXPBombs += amount;
            case DOUBLE_SKILL_XP -> this.doubleSkillXPBombs += amount;
            case INCREASED_MOB_DROPS -> this.increasedMobDropsBombs += amount;
            case INCREASED_RESOURCE_DROPS -> this.increasedResourceDropsBombs += amount;
            case INCREASED_PERFECT_STRIKE -> this.increasedPerfectStrikeBombs += amount;
        }
    }
}
