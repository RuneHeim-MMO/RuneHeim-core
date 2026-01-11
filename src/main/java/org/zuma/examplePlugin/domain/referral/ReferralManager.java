package org.zuma.examplePlugin.domain.referral;

import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.referral.persistence.ReferralRepository;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.events.player.RunePlayerLevelUpEvent;
import org.zuma.examplePlugin.events.player.RunePlayerLoadedEvent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class ReferralManager implements Listener {
    public static final int MINIMUM_TOTAL_LEVEL_REWARD = 25;
    private final ReferralRepository referralRepository;

    public ReferralManager(ReferralRepository referralRepository) {
        this.referralRepository = referralRepository;
    }

    public void notifyPlayerOfRewardsMaybe(RunePlayer player) {
        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            try {
                List<ReferralEntry> mentees = referralRepository.getMenteesForMentor(player.getUUID());
                System.out.println("Mentees count: " + new Gson().toJson(mentees));
                List<String> unclaimedUsernames = mentees.stream()
                        .filter(entry -> !entry.isMentorRewardClaimed() && entry.totalLevel() >= MINIMUM_TOTAL_LEVEL_REWARD)
                        .map(ReferralEntry::menteeUsername)
                        .toList();

                if (!unclaimedUsernames.isEmpty()) {
                    String menteeList = String.join(", ", unclaimedUsernames);
                    Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                        HudService.sendInfoMessage(player, Component.text("§aYou have referral rewards to claim for the following mentees who reached total level " + MINIMUM_TOTAL_LEVEL_REWARD + ": §e" + menteeList + "§a. Please visit §eSundering Hand Member§a to claim your rewards."));
                        SoundType.DAILY_REWARD_ADVANCE_STREAK.play(player, false);
                    });
                }

                ReferralEntry playersMentor = referralRepository.getMentorForMentee(player.getUUID());
                if (playersMentor != null && !playersMentor.isMenteeRewardClaimed()) {
                    int playerTotalLevel = player.getSkills().values().stream().mapToInt(Skill::getBaseLevel).sum();

                    if (playerTotalLevel >= MINIMUM_TOTAL_LEVEL_REWARD) {
                        Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                            HudService.sendInfoMessage(player, Component.text("§aYou unclaimed reward for reaching total level " + MINIMUM_TOTAL_LEVEL_REWARD + ". Please visit §eSundering Hand Member§a to claim it."));
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

        int total = player.getSkills().values().stream().mapToInt(Skill::getBaseLevel).sum();

        if (total == MINIMUM_TOTAL_LEVEL_REWARD || ((event.getNewLevel() - event.getOldLevel() > 1) && total >= MINIMUM_TOTAL_LEVEL_REWARD)) {
            HudService.sendInfoMessage(player, Component.text("§aYou have reached a total level of §e" + total + "§a! You can claim your reward from §eSundering Hand Member."));
            SoundType.DAILY_REWARD_ADVANCE_STREAK.play(player, true);
        }
    }

    public void getMenteesForMentor(UUID mentorUUID, Consumer<List<ReferralEntry>> cb) {
        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            try {
                List<ReferralEntry> mentees = referralRepository.getMenteesForMentor(mentorUUID);
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    List<ReferralEntry> sortedMentees = mentees.stream().sorted((a, b) -> Boolean.compare(a.isMentorRewardClaimed(), b.isMentorRewardClaimed())).toList();
                    cb.accept(sortedMentees);
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

    public void setMentorForPlayer(RunePlayer mentee, String mentorUsername) {
        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            int menteeTotalLevel = mentee.getSkills().values().stream().mapToInt(Skill::getBaseLevel).sum();

            if (menteeTotalLevel >= MINIMUM_TOTAL_LEVEL_REWARD) {
                HudService.sendFailMessage(mentee, Component.text("§cYou cannot set a mentor after reaching total level " + MINIMUM_TOTAL_LEVEL_REWARD + "."));
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
                    HudService.sendInfoMessage(mentee,
                            Component.text("§aYou have successfully set §e" + mentorUsername + "§a as your mentor! You can now earn rewards when you reach total level §e" + MINIMUM_TOTAL_LEVEL_REWARD + ".")
                    );
                });
            } catch (SQLException e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN,
                        "Failed to set mentor for player " + mentee.asBukkitPlayer().getName() + " due to a database error.",
                        e
                );
            } catch (IllegalStateException e) {
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    HudService.sendFailMessage(mentee, Component.text("§c" + e.getMessage()));
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
                    HudService.sendInfoMessage(mentee,
                            Component.text("§aYou have successfully abandoned your mentor. You can set a new mentor at any time by typing §e/mentor <username>.")
                    );
                });

            } catch (SQLException e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN,
                        "Failed to abandon mentor for player " + mentee.asBukkitPlayer().getName() + " due to a database error.",
                        e
                );
            } catch (IllegalStateException e) {
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    HudService.sendFailMessage(mentee, Component.text("§c" + e.getMessage()));
                });
            }
        });
    }


}
