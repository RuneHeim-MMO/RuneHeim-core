package org.zuma.examplePlugin.domain.gui.impl.individual;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.gui.base.PaginatedIndividualGUI;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.head.HeadGenerator;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.party.Party;
import org.zuma.examplePlugin.domain.party.PartyMember;
import org.zuma.examplePlugin.domain.party.constants.ContributionMode;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.party.PartyDisbandedEvent;
import org.zuma.examplePlugin.events.party.PartyUpdateEvent;

import java.text.NumberFormat;
import java.util.*;


public class PartyMenu extends PaginatedIndividualGUI {
    private final static int OWNER_HEAD_SLOT = 4;
    private final static int XP_CONTRIBUTION_BUTTON = 44;
    private final static int XP_SHARING_TOGGLE_BUTTON = 53;

    private UUID partyUUID;
    private long lastUpdate;

    public PartyMenu(String title, int id) {
        super(title, id, 6, Set.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 24, 25, 26, 27, 28, 29, 33, 34, 35, 36, 37, 38, 42, 43, 44));
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

    private void reloadInventory(Party party) {
        PartyMember viewer = party.getPartyMember(this.getOwner());
        PartyMember partyOwner = party.getPartyMember(party.getOwnerUuid());

        if (viewer == null || partyOwner == null) {
            return;
        }

        this.lastUpdate = System.currentTimeMillis();

        ItemStack ownerHead = getPlayerDisplayItem(partyOwner);
        List<ItemStack> otherIcons = party.getAllMembers().stream()
                .filter(u -> !u.getUuid().equals(party.getOwnerUuid()))
                .sorted(Comparator.comparing(a -> a.getRunePlayer() == null))
                .map(this::getPlayerDisplayItem).toList();

        ItemStack xpContributionButton = viewer.getContributionMode().getGuiControl().asItemstack(
                Component.text("You are contributing " + viewer.getContributionMode().getPercent() + "% of your XP", Color.INFO.asColor()),
                List.of(
                        Component.text("Click to adjust how much XP you ", Color.ITEM_LORE_DESCRIPTION.asColor()),
                        Component.text("contribute to your party members.", Color.ITEM_LORE_DESCRIPTION.asColor())
                )
        );

        ItemStack xpSharingToggleButton = (party.isXpSharingEnabled() ? GUIControl.PARTY_XP_SHARING_ON : GUIControl.PARTY_XP_SHARING_OFF).asItemstack(
                Component.text("XP Sharing is " + (party.isXpSharingEnabled() ? "Enabled" : "Disabled"), Color.INFO.asColor()),
                List.of(
                        Component.text("Click to " + (party.isXpSharingEnabled() ? "disable" : "enable") + " XP sharing", Color.ITEM_LORE_DESCRIPTION.asColor()),
                        Component.text("among party members.", Color.ITEM_LORE_DESCRIPTION.asColor())
                )
        );

        assignMenuValue(xpContributionButton, -1);
        assignMenuValue(xpSharingToggleButton, -2);

        HashMap<Integer, ItemStack> overrideIcons = new HashMap<>();
        overrideIcons.put(OWNER_HEAD_SLOT, ownerHead);
        overrideIcons.put(XP_CONTRIBUTION_BUTTON, xpContributionButton);
        overrideIcons.put(XP_SHARING_TOGGLE_BUTTON, xpSharingToggleButton);

        setContent(otherIcons, true);
        setOverrideItems(overrideIcons, true);

        getBaseInventory();
    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        if (player.getParty() == null) {
            HudService.sendFailMessage(player, Component.text("You are not in a party.", Color.ERROR.asColor()));
            return false;
        }

        this.partyUUID = player.getParty().getUuid();
        reloadInventory(player.getParty());
        player.asBukkitPlayer().openInventory(getBaseInventory());
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

        if (menuValue == -1) {
            ContributionMode[] allModes = ContributionMode.values();
            int currentIndex = Arrays.asList(allModes).indexOf(viewer.getContributionMode());
            int nextIndex = (currentIndex + 1) % allModes.length;

            viewerParty.setContributionMode(player, allModes[nextIndex]);
            player.asBukkitPlayer().sendMessage(Component.text("You are now contributing " + allModes[nextIndex].getPercent() + "% of your XP to your party members.", Color.SUCCESS.asColor()));
            SoundType.NOTIFICATION.play(player, false);
            return;
        }

        if (menuValue == -2) {
            boolean nextMode = !viewerParty.isXpSharingEnabled();

            try {
                viewerParty.setXPSharing(player, nextMode);
                SoundType.NOTIFICATION.play(player, false);
            } catch (Exception e) {
                HudService.sendFailMessage(player, Component.text(e.getMessage()));
            }

        }


    }

    @Override
    public int getPaginationControlsOffset() {
        return 0;
    }

    @EventHandler
    public void onPartyUpdate(PartyUpdateEvent event) {
        RunePlayer owner = RuneQuestPlugin.getPlayerManager().getRunePlayer(this.getOwner());
        Party party = RuneQuestPlugin.getPartyManager().getPartyByUUID(this.partyUUID);

        if (owner == null || party == null || !party.getMemberUUIDs().contains(this.getOwner())) {
            RuneQuestPlugin.getGUIHandler().forceCloseOpenedInventories(this.getUUID());
            HandlerList.unregisterAll(this);
            return;
        }

      /*  if (System.currentTimeMillis() - this.lastUpdate < 1000) {
            return;
        } */

        Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
            Party ownerParty = owner.getParty();

            if (ownerParty != null) {
                reloadInventory(party);
            }

        });
    }

    @EventHandler
    public void onPartyDisband(PartyDisbandedEvent event) {
        RunePlayer owner = RuneQuestPlugin.getPlayerManager().getRunePlayer(this.getOwner());
        Party party = RuneQuestPlugin.getPartyManager().getPartyByUUID(this.partyUUID);

        if (owner == null || party == null || !party.getMemberUUIDs().contains(this.getOwner())) {
            RuneQuestPlugin.getGUIHandler().forceCloseOpenedInventories(this.getUUID());
            HandlerList.unregisterAll(this);
            return;
        }

        RuneQuestPlugin.getGUIHandler().forceCloseOpenedInventories(this.getUUID());
        HandlerList.unregisterAll(this);
    }

}
