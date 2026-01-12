package org.zuma.examplePlugin.domain.gui.impl.individual;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.gui.base.PaginatedIndividualGUI;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.head.HeadGenerator;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.referral.ReferralEntry;
import org.zuma.examplePlugin.domain.referral.ReferralManager;
import org.zuma.examplePlugin.domain.skill.Skill;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class ReferralsMenu extends PaginatedIndividualGUI {
    private final static int PLAYER_HEAD_SLOT = 31;
    private final static int PLAYER_BUTTON_SLOT = 40;
    private ReferralEntry playersMentor = null;
    private boolean isLoading = true;

    public ReferralsMenu(String title, int id) {
        super(title, id, 6, Set.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56));
    }

    private void updateInventory(RunePlayer player, Runnable onFail, Runnable onSuccess) {
        int totalLevel = player.getSkills().values().stream().mapToInt(Skill::getBaseLevel).sum();
        isLoading = true;

        try {
            RuneQuestPlugin.getReferralManager().getMenteesForMentor(player.getUUID(), (mentees) -> {
                RuneQuestPlugin.getReferralManager().getPlayersMentor(player, mentorEntry -> {
                    HashMap<Integer, ItemStack> overrides = new HashMap<>();
                    boolean canClaimMenteeReward = mentorEntry != null && !mentorEntry.isMenteeRewardClaimed(); // Player has a mentor and hasn't claimed the reward yet
                    this.playersMentor = mentorEntry;

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm");

                    List<ItemStack> items = new ArrayList<>();
                    AtomicInteger index = new AtomicInteger(8); // Starting row for mentees

                    mentees.forEach(mentee -> {
                        Player menteePlayer = Bukkit.getPlayer(mentee.menteeUUID());
                        boolean isOnline = menteePlayer != null && menteePlayer.isOnline();
                        boolean menteeReachedLevel = mentee.totalLevel() > ReferralManager.MINIMUM_TOTAL_LEVEL_REWARD; // Player who is a mentee has reached the reward claim condition

                        ItemStack item = isOnline ? HeadGenerator.getPlayerHead(menteePlayer) : new ItemStack(Material.SKELETON_SKULL);
                        ItemMeta meta = item.getItemMeta();
                        meta.displayName(Component.text(mentee.menteeUsername(), Color.SUCCESS.asColor()).decoration(TextDecoration.ITALIC, false));

                        List<Component> lore = new ArrayList<>();
                        lore.add(Component.text("Mentee", Color.ACCENT.asColor()));
                        lore.add(Component.empty());
                        lore.add(Component.text("Total Level: " + mentee.totalLevel(), Color.INFO.asColor()));
                        lore.add(Component.text("Joined on: " + mentee.creationDate().toLocalDateTime().format(formatter), Color.INFO.asColor()));
                        meta.lore(lore.stream().map(c -> c.decoration(TextDecoration.ITALIC, false)).toList());
                        item.setItemMeta(meta);


                        ItemStack actionButton = null;

                        if (!menteeReachedLevel) {
                            actionButton = GUIControl.NO.asItemstack(Component.text("Ineligible for Reward", Color.ERROR.asColor()), List.of(Component.text("This mentee has not reached the required total level of " + ReferralManager.MINIMUM_TOTAL_LEVEL_REWARD + " yet.", Color.ITEM_LORE_DESCRIPTION.asColor())));
                        }

                        if (menteeReachedLevel && !mentee.isMentorRewardClaimed()) {
                            actionButton = GUIControl.YES.asItemstack(Component.text("Claim Reward", Color.SUCCESS.asColor()), List.of(Component.text("Click to claim your mentor reward for " + mentee.menteeUsername() + "'s referral.", Color.ITEM_LORE_DESCRIPTION.asColor())));
                            assignMenuValue(actionButton, "claim_mentor_reward_" + mentee.menteeUUID());
                        }

                        if (menteeReachedLevel && mentee.isMentorRewardClaimed()) {
                            actionButton = GUIControl.NO.asItemstack(Component.text("Reward Claimed", Color.SUCCESS.asColor()), List.of(Component.text("You have already claimed your mentor reward for " + mentee.menteeUsername() + "'s referral.", Color.ITEM_LORE_DESCRIPTION.asColor())));
                        }

                        items.add(item);
                        index.getAndIncrement();
                        overrides.put(index.get() + 9, actionButton);
                    });

                    ItemStack item = HeadGenerator.getPlayerHead(player.asBukkitPlayer());
                    ItemMeta meta = item.getItemMeta();
                    meta.displayName(Component.text(player.asBukkitPlayer().getName(), Color.SUCCESS.asColor()).decoration(TextDecoration.ITALIC, false));

                    List<Component> lore = new ArrayList<>();
                    lore.add(Component.text("Your Profile", Color.ACCENT.asColor()));
                    lore.add(Component.empty());
                    lore.add(Component.text("Total Level: " + totalLevel, Color.INFO.asColor()));

                    if (playersMentor != null) {
                        lore.add(Component.text("Mentor: " + playersMentor.mentorUsername(), Color.INFO.asColor()));
                        lore.add(Component.text("Joined on: " + playersMentor.creationDate().toLocalDateTime().format(formatter), Color.INFO.asColor()));

                        if (!playersMentor.isMenteeRewardClaimed()) {
                            ItemStack claimMentorRewardButton = GUIControl.YES.asItemstack(Component.text("Claim Reward", Color.SUCCESS.asColor()), List.of(Component.text("Click to claim your reward.", Color.ITEM_LORE_DESCRIPTION.asColor())));
                            assignMenuValue(claimMentorRewardButton, "claim_mentee_reward");
                            overrides.put(PLAYER_BUTTON_SLOT, claimMentorRewardButton);

                            lore.add(Component.empty());
                            lore.add(Component.text("You have a reward to claim!", Color.SUCCESS.asColor()));
                        } else {
                            lore.add(Component.empty());
                            lore.add(Component.text("You have claimed your mentor reward.", Color.INFO.asColor()));
                        }

                    } else {
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

        if (totalLevel < ReferralManager.MINIMUM_TOTAL_LEVEL_REWARD) {
            HudService.sendFailMessage(player, Component.text("You need a total level of at least " + ReferralManager.MINIMUM_TOTAL_LEVEL_REWARD + " to access the referrals menu."));
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
