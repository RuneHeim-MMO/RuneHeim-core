package org.zuma.examplePlugin.domain.party;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.party.constants.ContributionMode;
import org.zuma.examplePlugin.domain.party.constants.PartyRole;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.UUID;

public class PartyMember {
    private final UUID uuid;
    private final PartyRole role;
    private final EnumMap<SkillType, Long> xpContributed;
    private ContributionMode contributionMode;

    public PartyMember(UUID uuid, PartyRole role) {
        this.uuid = uuid;
        this.role = role;
        this.xpContributed = new EnumMap<>(SkillType.class);
        this.contributionMode = ContributionMode.PERCENT_30;

        for (SkillType skill : EnumSet.allOf(SkillType.class)) {
            this.xpContributed.put(skill, 0L);
        }
    }

    @Nullable
    public RunePlayer getRunePlayer() {
        return RuneQuestPlugin.getPlayerManager().getRunePlayer(this.uuid);
    }

    public UUID getUuid() {
        return uuid;
    }

    public PartyRole getRole() {
        return role;
    }

    public EnumMap<SkillType, Long> getXpContributed() {
        return xpContributed;
    }

    public void addXpContribution(SkillType skill, long xp) {
        this.xpContributed.put(skill, this.xpContributed.getOrDefault(skill, 0L) + xp);
    }

    public ContributionMode getContributionMode() {
        return contributionMode;
    }

    public void setContributionMode(ContributionMode contributionMode) {
        this.contributionMode = contributionMode;
    }
}
