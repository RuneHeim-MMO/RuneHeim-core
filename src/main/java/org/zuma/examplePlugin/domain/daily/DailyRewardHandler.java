package org.zuma.examplePlugin.domain.daily;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.daily.persistence.DailyRewardRepository;
import org.zuma.examplePlugin.domain.droptable.DropTable;
import org.zuma.examplePlugin.domain.droptable.DropTableEntry;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DailyRewardHandler {
    private final static long NOTIFY_PERIOD_MS = 1000L * 60 * 10; // 10 minutes;
    private final DailyRewardRepository repository;
    private final Map<Integer, DropTable> rewardPool;

    public DailyRewardHandler(DailyRewardRepository repository, List<DropTable> dailyRewards) {
        this.repository = repository;
        this.rewardPool = IntStream.range(1, dailyRewards.size()).boxed().collect(Collectors.toMap(i -> i, dailyRewards::get));

        if (dailyRewards.size() != 15) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Daily rewards pool does not contain 15 entries (2 weeks + grand prize)! Current entries: " + dailyRewards.size());
            throw new IllegalArgumentException("Daily rewards pool must contain 15 entries!");
        }

        startLoop();
    }

    private void startLoop() {
        Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), () -> {
            RuneQuestPlugin.getPlayerManager().getOnlinePlayers().forEach(player -> {
                DailyRewards dailyRewards = player.getDailyRewards();
                if (dailyRewards == null) {
                    return;
                }

                if (dailyRewards.canClaimToday()) {
                    sendReminder(player);
                }
            });
        }, 0L, NOTIFY_PERIOD_MS);
    }

    public void sendReminder(RunePlayer player) {
        DailyRewardEntry todayReward = player.getDailyRewards().getRewardEntries().get(player.getDailyRewards().getCurrentStreak());
        AbstractItemType itemType = RuneQuestPlugin.getItemRegistry().getType(todayReward.itemId());

        if (itemType == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Daily reward item type not found for id: " + todayReward.itemId() + " for player: " + player.asBukkitPlayer().getName());
            return;
        }

        HudService.sendSuccessMessage(player, Component.text("You your daily reward (", Color.SUCCESS.asColor())
                .append(Component.text(todayReward.min() + "x - " + todayReward.max() + "x " + itemType.displayName, Color.ACCENT.asColor())
                        .append(Component.text(") is waiting to be claimed!", Color.SUCCESS.asColor()))));
        SoundType.NOTIFICATION.play(player, false);
    }

    public void savePlayerDailyRewardData(RunePlayer player, boolean async) {
        DailyRewards dailyRewards = player.getDailyRewards();

        if (dailyRewards == null) {
            return;
        }

        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
                repository.upsertRewardData(player.getUUID(), dailyRewards);
            });
            return;
        }
        repository.upsertRewardData(player.getUUID(), dailyRewards);

    }

    public DailyRewards getPlayerDailyRewardDataSync(UUID uuid) {
        return this.repository.getByUUID(uuid);
    }

    public void assignNewDailyRewards(DailyRewards dailyRewards) {
        dailyRewards.getRewardEntries().clear();

        rewardPool.forEach((index, dropTable) -> {
            DropTableEntry randomEntry = null;
            int streakDay = index + 1;

            while (randomEntry == null) {
                for (DropTableEntry drop : dropTable.getDrops()) {
                    boolean isMatch = PlayerUtils.getRandom().nextInt(drop.oneInNChance) == 0;

                    if (!isMatch || randomEntry != null) {
                        continue;
                    }

                    randomEntry = drop;
                }
            }

            dailyRewards.getRewardEntries().put(streakDay, new DailyRewardEntry(randomEntry.id, randomEntry.min, randomEntry.max));
        });
    }
}
