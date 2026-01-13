package org.zuma.examplePlugin.domain.gui.impl.individual;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.gui.base.PaginatedIndividualGUI;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.head.HeadGenerator;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.referral.ReferralEntry;
import org.zuma.examplePlugin.domain.referral.ReferralManager;
import org.zuma.examplePlugin.domain.skill.Skill;

import java.time.format.DateTimeFormatter;
import java.util.*;


public class ReferralsMenu extends PaginatedIndividualGUI {
    private final static int PLAYER_HEAD_SLOT = 31;
    private final static int PLAYER_BUTTON_SLOT = 40;
    private ReferralEntry playersMentor = null;
    private boolean isLoading = true;

    public ReferralsMenu(String title, int id) {
        super(title, id, 6, Set.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56));
    }

    private List<Component> getRewards(List<ItemRecord> rewards) {
        List<Component> rewardComponents = new ArrayList<>();
        if (rewards.isEmpty()) {
            rewardComponents.add(Component.text("No rewards available.", Color.ITEM_LORE_DESCRIPTION.asColor()));
            return rewardComponents;
        }

        for (ItemRecord reward : rewards) {
            AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(reward.getItemId());


            if (type == null) {
                continue;
            }

            TextColor color = reward.getItemId() == ItemId.COINS.asInt() ? Color.ACCENT.asColor() : TextColor.fromHexString(type.rarity.getHexColor());
            rewardComponents.add(Component.text("- " + reward.getAmount() + "x " + type.displayName, color));
        }
        return rewardComponents;
    }

    private void updateInventory(RunePlayer player, Runnable onFail, Runnable onSuccess) {
        int totalLevel = player.getSkills().values().stream().mapToInt(Skill::getBaseLevel).sum();
        isLoading = true;

        try {
            RuneQuestPlugin.getReferralManager().getMenteesForMentor(player.getUUID(), (mentees) -> {
                RuneQuestPlugin.getReferralManager().getPlayersMentor(player, mentorEntry -> {
                    HashMap<Integer, ItemStack> overrides = new HashMap<>();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm");
                    List<ItemStack> items = new ArrayList<>();
                    this.playersMentor = mentorEntry;

                    mentees.forEach(mentee -> {
                        Player menteePlayer = Bukkit.getPlayer(mentee.menteeUUID());
                        boolean isOnline = menteePlayer != null && menteePlayer.isOnline();
                        boolean menteeReachedLevel = mentee.totalLevel() >= ReferralManager.MENTEE_REWARD_THRESHOLD_LEVEL; // Player who is a mentee has reached the reward claim condition
                        boolean canClaimReward = menteeReachedLevel && !mentee.isMentorRewardClaimed();

                        ItemStack item;

                        if (canClaimReward) {
                            item = GUIControl.CLAIM_BUTTON.asItemstack();
                        } else {
                            if (!isOnline) {
                                item = new ItemStack(Material.SKELETON_SKULL);
                            } else {
                                item = HeadGenerator.getPlayerHead(menteePlayer);
                            }
                        }

                        ItemMeta meta = item.getItemMeta();
                        meta.displayName(Component.text(mentee.menteeUsername(), Color.SUCCESS.asColor()).decoration(TextDecoration.ITALIC, false));

                        List<Component> lore = new ArrayList<>();
                        lore.add(Component.text("Mentee", Color.ACCENT.asColor()));
                        lore.add(Component.empty());
                        lore.add(Icon.LEVEL.asComponent().append(Component.text("Total Level: ").append(Component.text(mentee.totalLevel(), Color.ACCENT.asColor()))));
                        lore.add(Icon.AGE.asComponent().append(Component.text("Started mentoring at: ").append(Component.text(mentee.creationDate().toLocalDateTime().format(formatter), Color.ACCENT.asColor()))));

                        if (!menteeReachedLevel) {
                            lore.add(Icon.QUESTION_MARK.asComponent().append(Component.text("Status: ").append(Component.text("Ineligible for Reward", Color.ERROR.asColor()))));

                            lore.add(Component.empty());
                            lore.add(Component.text("Rewards at the threshold: ", Color.ITEM_LORE_DESCRIPTION.asColor()));
                            lore.addAll(getRewards(ReferralManager.MENTORING_REWARDS));

                            lore.add(Component.empty());
                            lore.add(Component.text("This mentee has not reached the required total level of " + ReferralManager.MENTEE_REWARD_THRESHOLD_LEVEL + " yet.", Color.ITEM_LORE_DESCRIPTION.asColor()));
                        }

                        if (canClaimReward) {
                            lore.add(Icon.QUESTION_MARK.asComponent().append(Component.text("Status: ").append(Component.text("Claim Reward", Color.SUCCESS.asColor()))));

                            lore.add(Component.empty());
                            lore.add(Component.text("Unclaimed rewards: ", Color.ITEM_LORE_DESCRIPTION.asColor()));
                            lore.addAll(getRewards(ReferralManager.MENTORING_REWARDS));

                            lore.add(Component.empty());
                            lore.addAll(List.of(Component.text("Click to claim your reward for", Color.ITEM_LORE_DESCRIPTION.asColor()), Component.text(mentee.menteeUsername() + "'s referral.", Color.ITEM_LORE_DESCRIPTION.asColor())));
                        }

                        if (menteeReachedLevel && mentee.isMentorRewardClaimed()) {
                            lore.add(Icon.QUESTION_MARK.asComponent().append(Component.text("Status: ").append(Component.text("Reward Claimed", Color.SUCCESS.asColor()))));

                            lore.add(Component.empty());
                            lore.addAll(List.of(Component.text("You have already claimed your reward for", Color.ITEM_LORE_DESCRIPTION.asColor()), Component.text(mentee.menteeUsername() + "'s referral.", Color.ITEM_LORE_DESCRIPTION.asColor())));
                        }


                        meta.lore(lore.stream().map(c -> c.decoration(TextDecoration.ITALIC, false)).toList());
                        item.setItemMeta(meta);

                        if (canClaimReward) {
                            assignMenuValue(item, "claim_mentor_reward_" + mentee.menteeUUID());
                        }

                        items.add(item);
                    });

                    ItemStack item = HeadGenerator.getPlayerHead(player.asBukkitPlayer());
                    ItemMeta meta = item.getItemMeta();
                    meta.displayName(Component.text(player.asBukkitPlayer().getName(), Color.SUCCESS.asColor()).decoration(TextDecoration.ITALIC, false));

                    List<Component> lore = new ArrayList<>();
                    lore.add(Component.text("Your Profile", Color.ACCENT.asColor()));
                    lore.add(Component.empty());
                    lore.add(Icon.LEVEL.asComponent().append(Component.text("Total Level: ").append(Component.text(totalLevel, Color.ACCENT.asColor()))));

                    if (playersMentor != null) {
                        lore.add(Icon.BOOK.asComponent().append(Component.text("Mentor: ").append(Component.text(playersMentor.mentorUsername(), Color.ACCENT.asColor()))));
                        lore.add(Icon.AGE.asComponent().append(Component.text("Obtained mentorship: ").append(Component.text(playersMentor.creationDate().toLocalDateTime().format(formatter), Color.ACCENT.asColor()))));

                        if (!playersMentor.isMenteeRewardClaimed()) {
                            ItemStack claimMentorRewardButton = GUIControl.CLAIM_BUTTON.asItemstack(Component.text("Claim Reward", Color.SUCCESS.asColor()), List.of(Component.text("Click to claim your reward.", Color.ITEM_LORE_DESCRIPTION.asColor())));
                            assignMenuValue(claimMentorRewardButton, "claim_mentee_reward");
                            overrides.put(PLAYER_BUTTON_SLOT, claimMentorRewardButton);

                            lore.add(Component.empty());
                            lore.add(Component.text("Unclaimed rewards: ", Color.ITEM_LORE_DESCRIPTION.asColor()));
                            lore.addAll(getRewards(ReferralManager.MENTEE_REWARDS));
                            lore.add(Component.empty());

                            lore.add(Icon.GREEN_GEM.asComponent().append(Component.text("You have a reward to claim!", Color.SUCCESS.asColor())));
                        } else {
                            lore.add(Component.empty());
                            lore.add(Component.text("You have claimed your reward.", Color.ERROR.asColor()));
                        }

                    } else {
                        lore.add(Component.empty());
                        lore.add(Component.text("You do not have a mentor.", Color.ERROR.asColor()));
                    }
                    meta.lore(lore.stream().map(c -> c.decoration(TextDecoration.ITALIC, false)).toList());
                    item.setItemMeta(meta);

                    overrides.put(PLAYER_HEAD_SLOT, item);

                    setContent(items, true);
                    setOverrideItems(overrides, true);
                    isLoading = false;
                    getBaseInventory();

                    if (onSuccess != null) {
                        onSuccess.run();
                    }
                });
            });
        } catch (Exception e) {
            long incidentId = System.currentTimeMillis();
            HudService.sendFailMessage(player, Component.text("An error occurred while trying to open the referrals menu. Incident #" + incidentId));
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "[Incident #" + incidentId + "] Error opening referrals menu for player " + player.asBukkitPlayer().getName(), e);
            if (onFail != null) {
                onFail.run();
            }
        }
    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        int totalLevel = player.getSkills().values().stream().mapToInt(Skill::getBaseLevel).sum();

        if (totalLevel < ReferralManager.MENTEE_REWARD_THRESHOLD_LEVEL) {
            HudService.sendFailMessage(player, Component.text("You need a total level of at least " + ReferralManager.MENTEE_REWARD_THRESHOLD_LEVEL + " to be able to refer players."));
            return false;
        }

        updateInventory(player, onFail, () -> {
            player.asBukkitPlayer().openInventory(getBaseInventory());
        });
        return true;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        event.setCancelled(true);

        ItemStack currentItem = event.getCurrentItem();

        if (isLoading) {
            return;
        }

        if (currentItem == null) {
            return;
        }

        String menuValue = getMenuValueStr(currentItem);

        System.out.println(menuValue);

        if (menuValue == null) {
            return;
        }

        try {
            SoundType.UI_CLICK.play(player, false);

            if (menuValue.startsWith("claim_mentee_reward") && playersMentor != null) {
                this.isLoading = true;
                RuneQuestPlugin.getReferralManager().claimReward(player, playersMentor.mentorUUID(), playersMentor.menteeUUID(), () -> {
                    updateInventory(player, null, null);
                });

                return;
            }

            if (menuValue.startsWith("claim_mentor_reward_")) {
                String[] parts = menuValue.split("claim_mentor_reward_");

                if (parts.length < 2) {
                    return;
                }

                UUID menteeUUID = UUID.fromString(parts[1]);
                this.isLoading = true;

                RuneQuestPlugin.getReferralManager().claimReward(player, player.getUUID(), menteeUUID, () -> {
                    updateInventory(player, null, null);
                });
            }
        } catch (Exception e) {
            // Ignore
        }


    }

    @Override
    public int getPaginationControlsOffset() {
        return 0;
    }
}
