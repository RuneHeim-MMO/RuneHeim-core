package org.zuma.examplePlugin.domain.gui.impl.individual;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.handlers.game.IllegalItemWatchdog;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.gui.base.PaginatedIndividualGUI;
import org.zuma.examplePlugin.domain.head.HeadGenerator;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.party.Party;
import org.zuma.examplePlugin.domain.party.PartyMember;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.territory.Territory;
import org.zuma.examplePlugin.events.party.PartyDisbandedEvent;
import org.zuma.examplePlugin.events.party.PartyPlayerJoinedEvent;
import org.zuma.examplePlugin.events.party.PartyUpdateEvent;

import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class PartyMenu extends PaginatedIndividualGUI {
    private final static int OWNER_HEAD_SLOT = 4;
    private UUID partyUUID;
    private long lastUpdate;

    public PartyMenu(String title, int id) {
        super(title, id, 6, Set.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 24, 25, 26, 27, 28, 29, 33, 34, 35, 36, 37, 38, 42, 43, 44));
    }

    private ItemStack getPlayerDisplayItem(PartyMember member) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(member.getUuid());

        if (!offlinePlayer.hasPlayedBefore() || offlinePlayer.getName() == null) {
            return new ItemStack(Material.BARRIER);
        }

        ItemStack icon = offlinePlayer.isOnline() ? HeadGenerator.getPlayerHead(offlinePlayer) : new ItemStack(Material.SKELETON_SKULL);
        ItemMeta meta = icon.getItemMeta();

        List<Component> lore = new ArrayList<>();
        Component name = offlinePlayer.isOnline() ? Component.text(offlinePlayer.getName(), Color.SUCCESS.asColor()) : Component.text(offlinePlayer.getName(), NamedTextColor.GRAY);
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

        meta.lore(lore.stream().map(c -> c.decoration(TextDecoration.ITALIC, false)).toList());
        icon.setItemMeta(meta);

        return icon;
    }

    private void reloadInventory(Party party) {
        this.lastUpdate = System.currentTimeMillis();

        ItemStack ownerHead = getPlayerDisplayItem(party.getPartyMember(party.getOwnerUuid()));
        List<ItemStack> otherIcons = party.getAllMembers().stream()
                .filter(u -> !u.getUuid().equals(party.getOwnerUuid()))
                .sorted(Comparator.comparing(a -> a.getRunePlayer() == null))
                .map(this::getPlayerDisplayItem).toList();

        HashMap<Integer, ItemStack> ownerIconOverride = new HashMap<>();
        ownerIconOverride.put(OWNER_HEAD_SLOT, ownerHead);

        setContent(otherIcons, true);
        setOverrideItems(ownerIconOverride, true);

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

        Integer menuValue = getMenuValue(currentItem);

        if (menuValue == null) {
            return;
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
            RuneQuestPlugin.getGUIHandler().forceCloseOpenedInventories(this.getUuid());
            return;
        }

        if (System.currentTimeMillis() - this.lastUpdate < 1000) {
            return;
        }

        Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> reloadInventory(owner.getParty()));
    }

    @EventHandler
    public void onPartyDisband(PartyDisbandedEvent event) {
        RunePlayer owner = RuneQuestPlugin.getPlayerManager().getRunePlayer(this.getOwner());
        Party party = RuneQuestPlugin.getPartyManager().getPartyByUUID(this.partyUUID);

        if (owner == null || party == null || !party.getMemberUUIDs().contains(this.getOwner())) {
            RuneQuestPlugin.getGUIHandler().forceCloseOpenedInventories(this.getUuid());
            return;
        }

        RuneQuestPlugin.getGUIHandler().forceCloseOpenedInventories(this.getUuid());
    }

}
