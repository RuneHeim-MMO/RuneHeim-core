package org.zuma.examplePlugin.domain.gui.constants;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zuma.examplePlugin.core.handlers.game.IllegalItemWatchdog;
import org.zuma.examplePlugin.domain.player.inventory.QuiverHandler;

import java.util.ArrayList;
import java.util.List;

public enum GUIControl {
    BLANK(0),
    CHEVRON_RIGHT(1),
    CHEVRON_LEFT(2),
    YES(3),
    NO(4),
    TRANSPARENT(5),
    INFO(6),

    NUM_1(7),
    NUM_2(8),
    NUM_3(9),
    NUM_4(10),
    NUM_5(11),
    NUM_6(12),
    NUM_7(13),
    NUM_8(14),
    NUM_9(15),
    NUM_10(16),
    NUM_PLUS10(17),
    NUM_0(18),
    NUM_UNKNOWN(19),

    CHEVRON_RIGHT_DISABLED(20),
    CHEVRON_LEFT_DISABLED(21),

    PARTY_XP_SHARING_ON(22),
    PARTY_XP_SHARING_OFF(23),
    PARTY_0_CONTRIBUTION(24),
    PARTY_10_CONTRIBUTION(25),
    PARTY_20_CONTRIBUTION(26),
    PARTY_30_CONTRIBUTION(27),

    COMBAT_XP_BOMB_ICON(28),
    MEGAPHONE_ICON(29),
    MOB_DROPS_BOMB_ICON(30),
    PERFECT_STRIKE_BOMB_ICON(31),
    RESOURCE_DROPS_BOMB_ICON(32),
    SKILL_XP_BOMB_ICON(33),

    SETTINGS_BASE_PREFIX(34),
    SETTINGS_BOMB_TRACKER(35),
    SETTINGS_DIALOG_HOLOGRAMS(36),
    SETTINGS_MEGAPHONE_MESSAGES(37),
    SETTINGS_LOCATION_HISCORES(38),
    SETTINGS_SUPPORTER_PREFIX(39),
    SETTINGS_UPGRADED_PREFIX(40),
    SETTINGS_TRACK_QUESTS(41),
    SETTINGS_DIALOG_SPEED(42),
    SETTINGS_MUSIC(43),

    LOADING_1(50),
    LOADING_2(51),
    LOADING_3(52),
    LOADING_4(53),
    LOADING_5(54),

    BUFF(55),
    DEBUFF(56),
    PLAYER_STATS_BUTTON(57),

    HOUSE_BUTTON(58),
    VISIBILITY(59),
    HOUSE_MODE_PUBLIC(60),
    HOUSE_MODE_PRIVATE(61),
    HOUSE_EDIT_MODE_ON(62),
    HOUSE_EDIT_MODE_OFF(63),
    HOUSE_ROTATE_FURNITURE(64),
    HOUSE_REMOVE_FURNITURE(65),

    PRAYER_0_ACTIVE(1001),
    PRAYER_0_INACTIVE(1000),
    PRAYER_1_ACTIVE(1011),
    PRAYER_1_INACTIVE(1010),
    PRAYER_2_ACTIVE(1021),
    PRAYER_2_INACTIVE(1020),
    PRAYER_3_ACTIVE(1031),
    PRAYER_3_INACTIVE(1030),
    PRAYER_4_ACTIVE(1041),
    PRAYER_4_INACTIVE(1040),
    PRAYER_5_ACTIVE(1051),
    PRAYER_5_INACTIVE(1050),
    PRAYER_6_ACTIVE(1061),
    PRAYER_6_INACTIVE(1060),
    PRAYER_7_ACTIVE(1071),
    PRAYER_7_INACTIVE(1070),
    PRAYER_8_ACTIVE(1081),
    PRAYER_8_INACTIVE(1080),
    PRAYER_9_ACTIVE(1091),
    PRAYER_9_INACTIVE(1090),
    PRAYER_10_ACTIVE(1101),
    PRAYER_10_INACTIVE(1100),
    PRAYER_11_ACTIVE(1111),
    PRAYER_11_INACTIVE(1110),
    PRAYER_12_ACTIVE(1121),
    PRAYER_12_INACTIVE(1120);

    private final int modelId;

    GUIControl(int modelId) {
        this.modelId = modelId;
    }

    public static boolean isGuiItem(ItemStack item) {
        if (item == null) {
            return false;
        }
        return IllegalItemWatchdog.getItemStackStatus(item) != IllegalItemWatchdog.LegalityType.LEGAL || QuiverHandler.isFakeArrow(item);
    }

    public static GUIControl fromNumber(int number) {
        if (number < 0) {
            return NUM_UNKNOWN;
        }

        return switch (number) {
            case 0 -> NUM_0;
            case 1 -> NUM_1;
            case 2 -> NUM_2;
            case 3 -> NUM_3;
            case 4 -> NUM_4;
            case 5 -> NUM_5;
            case 6 -> NUM_6;
            case 7 -> NUM_7;
            case 8 -> NUM_8;
            case 9 -> NUM_9;
            case 10 -> NUM_10;
            default -> NUM_PLUS10;
        };
    }

    public ItemStack asItemstack() {
        return asItemstack(Component.empty());
    }

    public ItemStack asItemstack(Component displayName, boolean bypassItemWatchdog) {
        return asItemstack(displayName, new ArrayList<>(), !bypassItemWatchdog);
    }

    public ItemStack asItemstack(Component displayName) {
        return asItemstack(displayName, new ArrayList<>(), true);
    }

    public ItemStack asItemstack(Component displayName, List<Component> lore) {
        return this.asItemstack(displayName, lore, true);
    }

    public ItemStack asItemstack(Component displayName, List<Component> lore, boolean tagIllegal) {
        ItemStack icon = new ItemStack(Material.POTATO);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(displayName.decoration(TextDecoration.ITALIC, false));
        meta.lore(lore.stream().map(comp -> comp.decoration(TextDecoration.ITALIC, false)).toList());
        meta.setCustomModelData(modelId);

        if (tagIllegal) {
            IllegalItemWatchdog.tagIllegalItem(meta);
        }

        icon.setItemMeta(meta);
        return icon;
    }
}
