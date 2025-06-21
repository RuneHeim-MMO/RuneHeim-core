package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.EquipableItem;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class ToolItem extends EquipableItem {

    public ToolItem(AbstractItemMeta meta) {
        super(meta);
        super.appendItemType("Tool", Icon.TOOL.get());
    }


}
