package org.zuma.examplePlugin.domain.quest;

import org.zuma.examplePlugin.core.constants.XpSource;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class QuestReward {
    @Nullable
    private final List<ItemRecord> itemRewards;

    @Nullable
    private final HashMap<SkillType, Double> xpRewards;

    private final Integer coins;

    public QuestReward(@Nullable List<ItemRecord> itemRewards, @Nullable HashMap<SkillType, Double> xpRewards, @Nullable Integer coins) {
        this.itemRewards = itemRewards;
        this.xpRewards = xpRewards;
        this.coins = coins;
    }


    @Nullable
    public HashMap<SkillType, Double> getXpRewards() {
        return xpRewards;
    }

    @Nullable
    public List<ItemRecord> getItemRewards() {
        return itemRewards;
    }

    public void applyRewards(RunePlayer player) {
        if (itemRewards != null) {
            itemRewards.forEach(player::giveItemOrDrop);
        }

        if (xpRewards != null) {
            xpRewards.forEach((type, xp) -> player.addXp(type, xp, XpSource.PERSONAL));
        }

        if (coins != null) {
            player.setPouchCoins(player.getPouchCoins() + coins, false);
        }
    }
}
