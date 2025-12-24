package org.zuma.examplePlugin.domain.gui.impl.individual.house;

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
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.CurrencyUtils;
import org.zuma.examplePlugin.domain.effect.EffectHandler;
import org.zuma.examplePlugin.domain.effect.effects.HouseBuyEffect;
import org.zuma.examplePlugin.domain.gui.base.PaginatedIndividualGUI;
import org.zuma.examplePlugin.domain.head.HeadGenerator;
import org.zuma.examplePlugin.domain.housing.HousingRegion;
import org.zuma.examplePlugin.domain.housing.instance.HouseInstance;
import org.zuma.examplePlugin.domain.housing.template.HouseTemplate;
import org.zuma.examplePlugin.domain.housing.template.TemplateMeta;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.*;

public class HouseBuyingMenu extends PaginatedIndividualGUI {
    private static final Set<Integer> IGNORED_SLOTS = new HashSet<>();
    private final HousingRegion region;

    public HouseBuyingMenu(String title, int id, HousingRegion region) {
        super(title, id, 2, IGNORED_SLOTS);
        this.region = region;
    }

    private ItemStack getTemplateItem(HouseTemplate template) {
        ItemStack itemStack = new ItemStack(Material.PAPER);
        TemplateMeta metaData = RuneQuestPlugin.getHousingManager().getTemplateMeta(template);
        ItemMeta meta = itemStack.getItemMeta();
        List<Component> lore = new ArrayList<>();

        if (meta == null) {
            return new ItemStack(Material.AIR);
        }

        meta.displayName(Component.text(template.getTemplateName(), Color.SERVER_BRAND.asColor()).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Interactive Slots: ", Color.ITEM_LORE_DESCRIPTION.asColor()).append(Component.text(metaData.interactiveSlotCount(), Color.ACCENT.asColor())));
        lore.add(Component.text("Decoration Slots: ", Color.ITEM_LORE_DESCRIPTION.asColor()).append(Component.text(metaData.decorationSlotCount(), Color.ACCENT.asColor())));
        lore.add(Component.empty());
        lore.add(Component.text("Price: ", Color.ITEM_LORE_DESCRIPTION.asColor()).append(CurrencyUtils.getCoinsCurrencyComponent(template.getPrice()).append(Component.text(" coins"))));
        lore.add(Component.empty());
        lore.add(Component.text("Left Click to preview this house.", Color.SUCCESS.asColor()));
        lore.add(Component.text("Right Click + shift to purchase this house.", Color.WARNING.asColor()));
        lore = lore.stream().map(c -> c.decoration(TextDecoration.ITALIC, false)).toList();

        meta.lore(lore);
        itemStack.setItemMeta(meta);
        assignMenuValue(itemStack, "buy_house_" + template.name());

        return itemStack;
    }


    private void updateInventory() {
        List<ItemStack> content = Arrays.stream(HouseTemplate.values()).filter(t -> t.getRegion().equals(this.region)).map(this::getTemplateItem).toList();

        setContent(content, true);
        getBaseInventory();
    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        RuneQuestPlugin.getHousingManager().playHasHouseInRegion(player.getUUID(), region);

        updateInventory();
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

        String menuValue = getMenuValueStr(currentItem);

        if (menuValue == null) {
            return;
        }


        SoundType.UI_CLICK.play(player, false);

        if (menuValue.startsWith("buy_house_")) {
            String templateName = menuValue.replace("buy_house_", "");
            HouseTemplate template = HouseTemplate.valueOf(templateName);

            if (event.isLeftClick()) {
                HouseInstance previewInstance = RuneQuestPlugin.getHousingManager().getTemplatePreviewMap().get(template);

                if (previewInstance == null) {
                    return;
                }

                previewInstance.getInvitedPlayers().add(player.getUUID());
                previewInstance.teleportToHouse(player);
                HudService.sendInfoMessage(player, Component.text("You are previewing " + template.getTemplateName()));
            } else if (event.isRightClick() && event.isShiftClick()) {
                // Purchase house
                try {
                    RuneQuestPlugin.getHousingManager().purchaseHouse(player, region, template);
                    RuneQuestPlugin.getHousingManager().teleportToOwnHouse(player, region);
                    player.asBukkitPlayer().closeInventory();

                    Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                        if (player == null || player.asBukkitPlayer() == null || !player.asBukkitPlayer().isOnline()) {
                            return;
                        }
                        EffectHandler.playAt(player.asBukkitPlayer().getLocation(), new HouseBuyEffect(player, template));
                    }, 10L);
                } catch (Exception e) {
                    HudService.sendFailMessage(player, Component.text(e.getMessage()));
                }
            }
        }


    }

    @Override
    public int getPaginationControlsOffset() {
        return 0;
    }


}
