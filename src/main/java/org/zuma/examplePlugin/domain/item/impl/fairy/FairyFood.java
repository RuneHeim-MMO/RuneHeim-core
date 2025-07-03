package org.zuma.examplePlugin.domain.item.impl.fairy;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class FairyFood extends AbstractItemType {
    private int energyRestoreAmount = 0;

    public FairyFood(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Fairy Food", Icon.FAIRY.get());
    }

    public int getEnergyRestoreAmount() {
        return energyRestoreAmount;
    }

    public void setEnergyRestoreAmount(int energyRestoreAmount) {
        this.energyRestoreAmount = energyRestoreAmount;
    }
}
