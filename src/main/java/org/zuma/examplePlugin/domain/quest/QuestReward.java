package org.zuma.examplePlugin.domain.quest;

import org.zuma.examplePlugin.core.constants.XpSource;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestReward {
    @Nullable
    private final List<ItemRecord> itemRewards;

    @Nullable
    private final Map<SkillType, Double> xpRewards;

    @Nullable
    private final Integer coins;

    public QuestReward(@Nullable List<ItemRecord> itemRewards, @Nullable Map<SkillType, Double> xpRewards, @Nullable Integer coins) {
        this.itemRewards = itemRewards;
        this.xpRewards = xpRewards;
        this.coins = coins;
    }


    @Nullable
    public Map<SkillType, Double> getXpRewards() {
        return xpRewards;
    }

    @Nullable
    public List<ItemRecord> getItemRewards() {
        return itemRewards;
    }

    @Nullable
    public Integer getCoins() {
        return coins;
    }


    public void applyRewards(RunePlayer player) {
        if (itemRewards != null) {
            itemRewards.forEach(player::giveItemOrDrop);
        }

        if (xpRewards != null) {
            xpRewards.forEach((type, xp) -> player.addXp(type, xp, XpSource.QUEST));
        }

        if (coins != null) {
            player.setPouchCoins(player.getPouchCoins() + coins, false);
        }
    }
}
