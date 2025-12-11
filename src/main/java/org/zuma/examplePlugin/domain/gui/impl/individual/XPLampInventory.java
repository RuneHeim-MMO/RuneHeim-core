package org.zuma.examplePlugin.domain.gui.impl.individual;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.constants.XpSource;
import org.zuma.examplePlugin.core.handlers.game.IllegalItemWatchdog;
import org.zuma.examplePlugin.core.utils.AnalyticsUtils;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.core.utils.XpUtils;
import org.zuma.examplePlugin.domain.gui.base.IndividualGUI;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.hud.LoadingBarBuilder;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.impl.XPLamp;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.*;


public class XPLampInventory extends IndividualGUI {
    private final Set<Integer> ignoredSlots = new HashSet<>(Set.of(0, 8, 9, 17, 18));

    public XPLampInventory(String title, int id) {
        super(title, id, 3);
    }

    private List<Component> getSkillTypeLore(RunePlayer player, SkillType type, double rewardXp) {
        Skill skill = player.getSkill(type);
        List<Component> result = new ArrayList<>();

        int currentLevel = skill.getBaseLevel();
        int totalXp = (int) Math.round(skill.getXp());
        boolean isMaxed = XpUtils.isMaxLevel(currentLevel);
        int percentToNextLevel = isMaxed ? 100 : XpUtils.getProgressPercent(totalXp, currentLevel);
        String formattedXp = NumberFormat.getNumberInstance().format(totalXp);

        String formattedNextLevelXp;
        if (isMaxed) {
            formattedNextLevelXp = "MAX";
        } else {
            formattedNextLevelXp = NumberFormat.getNumberInstance().format(XpUtils.getXpForLevel(currentLevel + 1));
        }

        Component progressBar = new LoadingBarBuilder()
                .totalSteps(100)
                .currentStep(isMaxed ? 100 : percentToNextLevel)
                .barLength(15)
                .segment(Icon.PROGRESS_SHORT_SEGMENT.get())
                .start(Icon.PROGRESS_SHORT_START.get())
                .stop(Icon.PROGRESS_SHORT_END.get())
                .emptyColor(Color.SKILL_BAR_EMPTY.asColor())
                .fillColor(Color.SKILL_BAR_FILL.asColor())
                .build();

        result.add(Component.text("Click to add " + rewardXp + " experience to this skill.", Color.SUCCESS.asColor()));
        result.add(Component.empty());
        result.add(progressBar.append(Component.text(" " + formattedXp + " XP" + "/" + formattedNextLevelXp + " XP", Color.ACCENT.asColor())));
        result.add(Component.empty());
        result.add(Icon.INFO.asComponent().append(Component.text(type.getDescription(), Color.INFO.asColor())));


        return result;

    }

    private ItemStack getSkillDisplayItem(RunePlayer player, SkillType type, double rewardXp) {
        Skill skill = player.getSkill(type);
        ItemStack item = new ItemStack(type.getMaterial());
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(type.getSymbol(), NamedTextColor.WHITE)
                .append(Component.text("  " + type.getLabel() + ": " + skill.getBaseLevel() + " level", NamedTextColor.GRAY)).decoration(TextDecoration.ITALIC, false));

        meta.lore(getSkillTypeLore(player, type, rewardXp).stream().map(component -> component.decoration(TextDecoration.ITALIC, false)).toList());

        Multimap<Attribute, AttributeModifier> emptyModifiers = ArrayListMultimap.create();
        meta.setAttributeModifiers(emptyModifiers);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_DYE);

        item.setItemMeta(meta);

        return item;
    }

    @Nullable
    public XPLamp getXPLampInHand(RunePlayer player) {
        ItemStack inHand = PlayerUtils.getItemStackInHand(player.asBukkitPlayer());
        AbstractItemType inHandType = RuneQuestPlugin.getItemRegistry().getType(inHand);

        if (!(inHandType instanceof XPLamp lamp)) {
            HudService.sendFailMessage(player, Component.text("Item in hand is not an XP lamp!"));
            return null;
        }

        return lamp;
    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        XPLamp inHandLamp = getXPLampInHand(player);

        if (inHandLamp == null) {
            return false;
        }

        Inventory inventory = getBaseInventory();
        List<SkillType> types = Arrays.stream(SkillType.values()).filter(type -> !inHandLamp.getDisabledSkills().contains(type)).toList();

        int slot = 0;
        int index = 0;
        for (SkillType skillType : types) {
            // Find the next slot that is NOT in ignoredSlots
            while (ignoredSlots.contains(slot)) {
                slot++;
            }

            ItemStack item = getSkillDisplayItem(player, skillType, inHandLamp.getXpReward());
            IllegalItemWatchdog.tagIllegalItem(item);
            super.assignMenuValue(item, index);
            inventory.setItem(slot, item);

            slot++;
            index++;
        }

        SoundType.XP_LAMP_INVENTORY_OPEN.play(player, true);
        player.asBukkitPlayer().openInventory(inventory);
        return true;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        event.setCancelled(true);

        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) {
            return;
        }

        Integer menuValue = getMenuValue(currentItem);

        if (menuValue == null) {
            return;
        }

        XPLamp inHandLamp = getXPLampInHand(player);

        if (inHandLamp == null) {
            return;
        }

        List<SkillType> types = Arrays.stream(SkillType.values()).filter(type -> !inHandLamp.getDisabledSkills().contains(type)).toList();
        SkillType selectedSkill = types.get(menuValue);

        if (selectedSkill == null) {
            return;
        }

        PlayerUtils.subtractOneFromMainHand(player);
        player.addXp(selectedSkill, inHandLamp.getXpReward(), XpSource.XP_LAMP);
        player.asBukkitPlayer().closeInventory();
        SoundType.XP_LAMP_SKILL_SELECT.play(player, true);
        AnalyticsUtils.incrementServerAndPlayer(player, AnalyticsKey.XP_LAMPS_USED, 1);
    }

}
