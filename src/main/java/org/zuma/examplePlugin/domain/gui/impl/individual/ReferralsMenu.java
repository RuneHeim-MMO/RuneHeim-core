package org.zuma.examplePlugin.domain.gui.impl.individual;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.gui.base.PaginatedIndividualGUI;
import org.zuma.examplePlugin.domain.head.HeadGenerator;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.party.Party;
import org.zuma.examplePlugin.domain.party.PartyMember;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.referral.ReferralManager;
import org.zuma.examplePlugin.domain.skill.Skill;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class ReferralsMenu extends PaginatedIndividualGUI {

    public ReferralsMenu(String title, int id) {
        super(title, id, 6, Set.of());
    }

    private ItemStack getPlayerDisplayItem(PartyMember member) {
        Player player = Bukkit.getPlayer(member.getUuid());
        boolean isOnline = player != null && player.isOnline();

        ItemStack icon = isOnline ? HeadGenerator.getPlayerHead(player) : new ItemStack(Material.SKELETON_SKULL);
        ItemMeta meta = icon.getItemMeta();

        List<Component> lore = new ArrayList<>();
        Component name = isOnline ? Component.text(member.getUsername(), Color.SUCCESS.asColor()) : Component.text(member.getUsername(), NamedTextColor.GRAY);
        meta.displayName(name.decoration(TextDecoration.ITALIC, false));
        lore.add(Icon.PARTY.asComponent().append(Component.text(member.getRole().getLabel(), Color.ACCENT.asColor())));
        List<Component> contributionsComponents = new ArrayList<>();

        NumberFormat nf = NumberFormat.getNumberInstance();
        member.getXpContributed().forEach((skill, xp) -> {
            if (xp <= 0) {
                return;
            }

            contributionsComponents.add(skill.getSymbolComponent().append(Component.text(skill.getLabel() + ": ", Color.ITEM_LORE_DESCRIPTION.asColor())
                    .append(Component.text(nf.format(xp) + " XP", Color.ACCENT.asColor()))));
        });

        if (!contributionsComponents.isEmpty()) {
            lore.add(Component.empty());
            lore.add(Icon.EXPERIENCE.asComponent().append(Component.text("Contributions:", Color.INFO.asColor())));
            lore.addAll(contributionsComponents);
        }

        lore.add(Component.empty());
        lore.add(Component.text("§fContributing §b" + member.getContributionMode().getPercent() + "%§f of experience."));
        meta.lore(lore.stream().map(c -> c.decoration(TextDecoration.ITALIC, false)).toList());
        icon.setItemMeta(meta);

        return icon;
    }


    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        int totalLevel = player.getSkills().values().stream().mapToInt(Skill::getBaseLevel).sum();

        if (totalLevel < ReferralManager.MINIMUM_TOTAL_LEVEL_REWARD) {
            HudService.sendFailMessage(player, Component.text("You need a total level of at least " + ReferralManager.MINIMUM_TOTAL_LEVEL_REWARD + " to access the referrals menu."));
            return false;
        }

        try {
            RuneQuestPlugin.getReferralManager().getMenteesForMentor(player.getUUID(), (mentees) -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm");

                List<ItemStack> items = mentees.stream().map(mentee -> {
                    Player menteePlayer = Bukkit.getPlayer(mentee.menteeUUID());
                    boolean isOnline = menteePlayer != null && menteePlayer.isOnline();

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
                    return item;
                }).toList();

                setContent(items, true);
                player.asBukkitPlayer().openInventory(getBaseInventory());
            });
        } catch (Exception e) {
            long incidentId = System.currentTimeMillis();
            HudService.sendFailMessage(player, Component.text("An error occurred while trying to open the referrals menu. Incident #" + incidentId));
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL,"[Incident #" + incidentId + "] Error opening referrals menu for player " + player.asBukkitPlayer().getName(), e);
            onFail.run();
        }

        return true;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        event.setCancelled(true);

        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) {
            return;
        }

        Integer menuValue = getMenuValueInt(currentItem);

        if (menuValue == null) {
            return;
        }

        Party viewerParty = RuneQuestPlugin.getPartyManager().getPlayersParty(player);

        if (viewerParty == null) {
            return;
        }

        PartyMember viewer = viewerParty.getPartyMember(player.getUUID());

        if (viewer == null) {
            return;
        }


    }

    @Override
    public int getPaginationControlsOffset() {
        return 0;
    }
}
