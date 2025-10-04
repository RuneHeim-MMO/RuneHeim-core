package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

import javax.annotation.Nullable;

public class Material extends AbstractItemType {
    private Integer basePrice = null;
    private Double pastIngredientsPriceMultiplier = null;

    public Material(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Material", Icon.MATERIAL.get());
    }

    @Nullable
    public Integer getBasePrice() {
        if (basePrice == null) return null;
        if (pastIngredientsPriceMultiplier == null) return basePrice;

        return (int) (basePrice * pastIngredientsPriceMultiplier);
    }

    public void setBasePrice(Integer basePrice) {
        this.basePrice = basePrice;
    }

    @Nullable // If null, then base price is used as is - usually for first tier materials
    public Double getPastIngredientsPriceMultiplier() {
        return pastIngredientsPriceMultiplier;
    }

    public void setPastIngredientsPriceMultiplier(Double pastIngredientsPriceMultiplier) {
        this.pastIngredientsPriceMultiplier = pastIngredientsPriceMultiplier;
    }
}
