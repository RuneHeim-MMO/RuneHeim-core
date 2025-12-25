package org.zuma.examplePlugin.domain.item.impl;

import org.bukkit.event.player.PlayerInteractEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;

public class XPLamp extends AbstractItemType {
    private List<SkillType> disabledSkills;
    private double xpReward;

    public XPLamp(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("XP Reward", Icon.CLOVER.get());
    }

    public void setXpReward(double amount) {
        this.xpReward = amount;
    }

    public void setDisabledSkills(List<SkillType> skills) {
        this.disabledSkills = skills;
    }


    @Override
    public void onPlayerInteract(PlayerInteractEvent event, RunePlayer player, StateManager itemStateManager) {
        RuneQuestPlugin.getGUIHandler().openInventory(InventoryId.XP_LAMP.asInt(), player, null);
    }

    public List<SkillType> getDisabledSkills() {
        return disabledSkills;
    }

    public double getXpReward() {
        return xpReward;
    }
}
