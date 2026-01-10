package org.zuma.examplePlugin.domain.gui.impl.individual;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.gui.base.PaginatedIndividualGUI;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.SkillModifier;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.Skill;

import java.util.*;

public class ModifiersInventory extends PaginatedIndividualGUI {
    private static final Set<Integer> IGNORED_SLOTS = new HashSet<>();

    public ModifiersInventory(String title, int id) {
        super(title, id, 4, IGNORED_SLOTS);
    }

    private ItemStack getModifierItemStack(RunePlayer player, SkillModifier modifier) {
        Skill matchingSkill = player.getSkills().get(modifier.type());

        if (matchingSkill == null) {
            return new ItemStack(Material.AIR);
        }

        int baseLevelBonus = modifier.amount();
        double percentBonus = modifier.percent() != null
                ? modifier.percent() * 0.01 * matchingSkill.getBaseLevel()
                : 0;

        int amount = baseLevelBonus + (int) Math.floor(percentBonus);

        ItemStack itemStack = amount > 0 ? GUIControl.BUFF.asItemstack() : GUIControl.DEBUFF.asItemstack();
        ItemMeta meta = itemStack.getItemMeta();
        List<Component> lore = new ArrayList<>();

        TextColor titleColor = amount > 0 ? Color.SUCCESS.asColor() : Color.ERROR.asColor();
        meta.displayName(modifier.type().getSymbolComponent().append(Component.text((amount > 0 ? "+" : "") + amount + " " + modifier.type().getLabel(), titleColor)).decoration(TextDecoration.ITALIC, false));

        lore.add(Component.text("Increases your " + modifier.type().getLabel() + " level.", Color.ITEM_LORE_DESCRIPTION.asColor()).decoration(TextDecoration.ITALIC, false));
        meta.lore(lore);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    private ItemStack getModifierItemStack(Modifier modifier) {
        ItemStack itemStack = modifier.amount() > 0 ? GUIControl.BUFF.asItemstack() : GUIControl.DEBUFF.asItemstack();
        ItemMeta meta = itemStack.getItemMeta();

        meta.displayName(modifier.type().getTitle(modifier.amount()).decoration(TextDecoration.ITALIC, false));
        List<Component> lore = new ArrayList<>(TextUtils.splitLore(modifier.type().getDescription(), 12, Color.ITEM_LORE_DESCRIPTION.asColor()));
        meta.lore(lore);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    private void updateInventory(RunePlayer player) {
        List<Object> modifiers = new ArrayList<>(player.getModifiers().values());
        modifiers.addAll(player.getSkillModifiers().values());

        // Yeah it's ugly, but idgaf
        List<ItemStack> icons = modifiers.stream()
                .sorted(Comparator.comparingDouble(mod -> {
                    if (mod instanceof SkillModifier sm) return sm.amount();
                    if (mod instanceof Modifier m) return m.amount();
                    return 0.0;
                }).reversed()).map(modifier -> {
                    if (modifier instanceof SkillModifier skillModifier && (skillModifier.percent() != null && skillModifier.percent() > 0 || skillModifier.amount() > 0))
                        return getModifierItemStack(player,skillModifier);
                    if (modifier instanceof Modifier m) return getModifierItemStack(m);
                    return null;
                }).toList();

        setContent(icons, true);
        getBaseInventory();
    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        updateInventory(player);
        player.asBukkitPlayer().openInventory(getBaseInventory());
        return true;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        event.setCancelled(true);
    }

    @Override
    public int getPaginationControlsOffset() {
        return 0;
    }

}
