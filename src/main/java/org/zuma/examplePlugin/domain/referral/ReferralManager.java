package org.zuma.examplePlugin.domain.referral;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.referral.persistence.ReferralRepository;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.events.player.RunePlayerLevelUpEvent;
import org.zuma.examplePlugin.events.player.RunePlayerLoadedEvent;
import org.zuma.examplePlugin.events.referral.ReferralReachedThresholdEvent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class ReferralManager implements Listener {
    public static final int MINIMUM_TOTAL_LEVEL_REWARD = 25;
    private static final List<ItemRecord> mentorRewards = List.of(new ItemRecord(ItemId.RECRUIT_TOKEN, 1));
    private static final List<ItemRecord> menteeRewards = List.of(new ItemRecord(ItemId.COINS, 5000));

    private final ReferralRepository referralRepository;

    public ReferralManager(ReferralRepository referralRepository) {
        this.referralRepository = referralRepository;
    }

    public void notifyPlayerOfRewardsMaybe(RunePlayer player) {
        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            try {
                List<ReferralEntry> mentees = referralRepository.getMenteesForMentor(player.getUUID());
                List<String> unclaimedUsernames = mentees.stream()
                        .filter(entry -> !entry.isMentorRewardClaimed() && entry.totalLevel() >= MINIMUM_TOTAL_LEVEL_REWARD)
                        .map(ReferralEntry::menteeUsername)
                        .toList();

                if (!unclaimedUsernames.isEmpty()) {
                    String menteeList = String.join(", ", unclaimedUsernames);
                    Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                        HudService.sendSuccessMessage(player, Component.text("You have referral rewards to claim for the following mentees who reached total level ", Color.SUCCESS.asColor())
                                .append(Component.text(MINIMUM_TOTAL_LEVEL_REWARD, Color.ACCENT.asColor()))
                                .append(Component.text(": ", Color.SUCCESS.asColor()))
                                .append(Component.text(menteeList, Color.ACCENT.asColor()))
                                .append(Component.text(". Please visit ", Color.SUCCESS.asColor()))
                                .append(Component.text("Sundering Hand Member", Color.ACCENT.asColor()))
                                .append(Component.text(" to claim your rewards.", Color.SUCCESS.asColor())));
                        SoundType.DAILY_REWARD_ADVANCE_STREAK.play(player, false);
                    });
                }

                ReferralEntry playersMentor = referralRepository.getMentorForMentee(player.getUUID());
                if (playersMentor != null && !playersMentor.isMenteeRewardClaimed()) {
                    int playerTotalLevel = player.getSkills().values().stream().mapToInt(Skill::getBaseLevel).sum();

                    if (playerTotalLevel >= MINIMUM_TOTAL_LEVEL_REWARD) {
                        Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                            HudService.sendSuccessMessage(player, Component.text("You have an unclaimed reward for reaching total level ", Color.SUCCESS.asColor())
                                    .append(Component.text(MINIMUM_TOTAL_LEVEL_REWARD, Color.ACCENT.asColor()))
                                    .append(Component.text(". Please visit ", Color.SUCCESS.asColor()))
                                    .append(Component.text("Sundering Hand Member", Color.ACCENT.asColor()))
                                    .append(Component.text(" to claim it.", Color.SUCCESS.asColor())));
                            SoundType.DAILY_REWARD_ADVANCE_STREAK.play(player, false);
                        });
                    }
                }

            } catch (SQLException e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN,
                        "Failed to notify player " + player.asBukkitPlayer().getName() + " of referral rewards due to a database error.",
                        e
                );
            }

        });
    }

    @EventHandler
    public void handlePlayerJoin(RunePlayerLoadedEvent event) {
        RunePlayer player = event.getPlayer();
        notifyPlayerOfRewardsMaybe(player);
    }

    @EventHandler
    public void handlePlayerLevelUp(RunePlayerLevelUpEvent event) {
        RunePlayer player = event.getPlayer();

        if (!player.hasMentor()) {
            return;
        }

        int before = player.getSkills().values().stream()
                .mapToInt(Skill::getBaseLevel)
                .sum() - event.getLevelDelta();

        int after = before + event.getLevelDelta();

        if (before < MINIMUM_TOTAL_LEVEL_REWARD && after >= MINIMUM_TOTAL_LEVEL_REWARD) {
            HudService.sendSuccessMessage(
                    player,
                    Component.text("You have reached a total level of ", Color.SUCCESS.asColor())
                            .append(Component.text(after, Color.ACCENT.asColor()))
                            .append(Component.text("! You can claim your reward from ", Color.SUCCESS.asColor()))
                            .append(Component.text("Sundering Hand Member", Color.ACCENT.asColor()))
                            .append(Component.text(".", Color.SUCCESS.asColor()))
            );
            SoundType.DAILY_REWARD_ADVANCE_STREAK.play(player, true);

            Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
                try {
                    ReferralEntry mentorEntry = referralRepository.getMentorForMentee(player.getUUID());
                    if (mentorEntry == null) {
                        return;
                    }

                    Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                        ReferralReachedThresholdEvent redisEvent = new ReferralReachedThresholdEvent(new ReferralReachedThresholdEvent.Payload(
                                player.getUUID(),
                                mentorEntry.mentorUUID(),
                                player.asBukkitPlayer().getName()
                        ));

                        Bukkit.getServer().getPluginManager().callEvent(redisEvent);
                    });

                } catch (SQLException e) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN,
                            "Failed to publish ReferralReachedThreshold event for player " + player.asBukkitPlayer().getName() + " due to a database error.",
                            e
                    );
                }
            });
        }
    }

    public void getMenteesForMentor(UUID mentorUUID, Consumer<List<ReferralEntry>> cb) {
        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            try {
                List<ReferralEntry> pagination = new ArrayList<>();
                List<ReferralEntry> mentees = referralRepository.getMenteesForMentor(mentorUUID);

                pagination.addAll(mentees);
                pagination.addAll(mentees);
                pagination.addAll(mentees);
                pagination.addAll(mentees);
                pagination.addAll(mentees);
                pagination.addAll(mentees);
                pagination.addAll(mentees);
                pagination.addAll(mentees);
                pagination.addAll(mentees);
                pagination.addAll(mentees);
                pagination.addAll(mentees);
                pagination.addAll(mentees);
                pagination.addAll(mentees);
                pagination.addAll(mentees);
                pagination.addAll(mentees);
                pagination.addAll(mentees);
                pagination.addAll(mentees);
                pagination.addAll(mentees);
                pagination.addAll(mentees);
                pagination.addAll(mentees);
                pagination.addAll(mentees);
                pagination.addAll(mentees);

                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    List<ReferralEntry> sortedMentees = mentees.stream().sorted((a, b) -> Boolean.compare(a.isMentorRewardClaimed(), b.isMentorRewardClaimed())).toList();
                    cb.accept(pagination);
                });
            } catch (SQLException e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN,
                        "Failed to retrieve mentees for mentor UUID " + mentorUUID + " due to a database error.",
                        e
                );
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    cb.accept(new ArrayList<>());
                });
            }
        });

    }

    public void getPlayersMentor(RunePlayer player, Consumer<ReferralEntry> cb) {
        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            try {
                ReferralEntry mentorEntry = referralRepository.getMentorForMentee(player.getUUID());
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    cb.accept(mentorEntry);
                });
            } catch (SQLException e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN,
                        "Failed to retrieve mentor for player " + player.asBukkitPlayer().getName() + " due to a database error.",
                        e
                );
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    cb.accept(null);
                });
            }
        });
    }

    public void claimReward(RunePlayer claimedBy, UUID mentorUUID, UUID menteeUUID, Runnable finallyCb) {
        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            boolean isMentor = claimedBy.getUUID().equals(mentorUUID);

            try {
                if (isMentor) {
                    referralRepository.claimMentorReward(mentorUUID, menteeUUID);
                } else {
                    referralRepository.claimMenteeReward(menteeUUID, mentorUUID);
                }

                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    List<ItemRecord> rewards = isMentor ? mentorRewards : menteeRewards;
                    rewards.forEach(claimedBy::giveItemOrDrop);

                    HudService.sendSuccessMessage(claimedBy,
                            Component.text("You have successfully claimed your reward!", Color.SUCCESS.asColor())
                    );
                    SoundType.DAILY_REWARD_CLAIM.play(claimedBy, false);
                });
            } catch (SQLException e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN,
                        "Failed to claim " + (isMentor ? "mentor" : "mentee") + " reward for player " + claimedBy.asBukkitPlayer().getName() + " due to a database error.",
                        e
                );
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    HudService.sendFailMessage(claimedBy, Component.text("An error occurred while trying to claim your reward. Please try again later."));
                });
            } catch (IllegalStateException e) {
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    HudService.sendFailMessage(claimedBy, Component.text(e.getMessage()));
                });
            } finally {
                finallyCb.run();
            }
        });
    }

    public void setMentorForPlayer(RunePlayer mentee, String mentorUsername) {
        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            int menteeTotalLevel = mentee.getSkills().values().stream().mapToInt(Skill::getBaseLevel).sum();

            if (menteeTotalLevel >= MINIMUM_TOTAL_LEVEL_REWARD) {
                HudService.sendFailMessage(mentee, Component.text("You cannot set a mentor after reaching total level ", Color.ERROR.asColor())
                        .append(Component.text(MINIMUM_TOTAL_LEVEL_REWARD, Color.ACCENT.asColor()))
                        .append(Component.text(".", Color.ERROR.asColor())));
                return;
            }

            try {
                UUID matchingPlayer = referralRepository.getPlayerUUIDByUsername(mentorUsername);

                if (matchingPlayer == null) {
                    throw new IllegalStateException("No player found with the username " + mentorUsername + ".");
                }

                if (matchingPlayer.equals(mentee.getUUID())) {
                    throw new IllegalStateException("You cannot set yourself as your own mentor.");
                }

                int totalLevel = referralRepository.getPlayerTotalLevel(matchingPlayer);

                if (totalLevel < MINIMUM_TOTAL_LEVEL_REWARD) {
                    throw new IllegalStateException("The player " + mentorUsername + " does not meet the minimum total level requirement of " + MINIMUM_TOTAL_LEVEL_REWARD + " to be a mentor.");
                }

                referralRepository.addPlayerAsMentor(matchingPlayer, mentee.getUUID());
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    mentee.setHasMentor(true);
                    HudService.sendSuccessMessage(mentee,
                            Component.text("You have successfully set ", Color.SUCCESS.asColor())
                                    .append(Component.text(mentorUsername, Color.ACCENT.asColor()))
                                    .append(Component.text(" as your mentor! You can now earn rewards when you reach total level ", Color.SUCCESS.asColor()))
                                    .append(Component.text(MINIMUM_TOTAL_LEVEL_REWARD, Color.ACCENT.asColor()))
                                    .append(Component.text(".", Color.SUCCESS.asColor()))
                    );
                });
            } catch (SQLException e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN,
                        "Failed to set mentor for player " + mentee.asBukkitPlayer().getName() + " due to a database error.",
                        e
                );
            } catch (IllegalStateException e) {
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    HudService.sendFailMessage(mentee, Component.text(e.getMessage()));
                });
            }
        });
    }

    public void abandonMentorForPlayer(RunePlayer mentee) {
        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            try {
                referralRepository.abandonMentorForMentee(mentee.getUUID());

                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    mentee.setHasMentor(false);
                    HudService.sendSuccessMessage(mentee,
                            Component.text("You have successfully abandoned your mentor. You can set a new mentor at any time by typing ", Color.SUCCESS.asColor())
                                    .append(Component.text("/mentor <username>", Color.ACCENT.asColor()))
                                    .append(Component.text(".", Color.SUCCESS.asColor()))
                    );
                });

            } catch (SQLException e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN,
                        "Failed to abandon mentor for player " + mentee.asBukkitPlayer().getName() + " due to a database error.",
                        e
                );
            } catch (IllegalStateException e) {
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    HudService.sendFailMessage(mentee, Component.text(e.getMessage()));
                });
            }
        });
    }

    public void notifyMentorOfMenteeThreshold(UUID mentorUUID, String menteeUsername) {
        RunePlayer player = RuneQuestPlugin.getPlayerManager().getRunePlayer(mentorUUID);

        if (player == null) {
            return;
        }

        player.asBukkitPlayer().sendMessage(Component.text("Your mentee ", Color.SUCCESS.asColor())
                .append(Component.text(menteeUsername, Color.ACCENT.asColor()))
                .append(Component.text(" has reached total level ", Color.SUCCESS.asColor()))
                .append(Component.text(MINIMUM_TOTAL_LEVEL_REWARD, Color.ACCENT.asColor()))
                .append(Component.text("! You can claim your reward from ", Color.SUCCESS.asColor()))
                .append(Component.text("Sundering Hand Member", Color.ACCENT.asColor()))
                .append(Component.text(".", Color.SUCCESS.asColor())));
        SoundType.NOTIFICATION.play(player, false);
    }
}
