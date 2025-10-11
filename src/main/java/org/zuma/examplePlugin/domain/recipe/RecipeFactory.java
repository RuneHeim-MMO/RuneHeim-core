package org.zuma.examplePlugin.domain.recipe;

import com.google.gson.Gson;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.core.exception.UnsupportedJsonVersionException;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.recipe.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipe.impl.*;
import org.zuma.examplePlugin.domain.recipe.meta.AbstractRecipeMeta;
import org.zuma.examplePlugin.domain.recipe.meta.v1.CampfireRecipeMeta;

import javax.annotation.Nullable;

public class RecipeFactory {

    public AbstractRecipe createRecipe(ItemStack displayItem, AbstractRecipeMeta meta, @Nullable VersionedJson json) {
        return switch (meta.type()) {
            case SMELTING_RECIPE -> new SmeltingRecipe(displayItem, meta);
            case SMITHING_RECIPE -> new SmithingRecipe(displayItem, meta);
            case COOKING_RECIPE -> new CookingRecipe(displayItem, meta);
            case CAMPFIRE_RECIPE -> createCampfireRecipe(meta, json);
            case SPINNING_WHEEL_RECIPE -> new SpinningWheelRecipe(displayItem, meta);
            case CRAFTING_TABLE_RECIPE -> new CraftingTableRecipe(displayItem, meta);
            case TANNER_RECIPE -> new TannerRecipe(displayItem, meta);
            case FLETCHING_TABLE_RECIPE -> new FletchingTableRecipe(displayItem, meta);
            default -> new InventoryRecipe(displayItem, meta);

        };
    }

    private CampfireRecipe createCampfireRecipe(AbstractRecipeMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create campfire recipe recipe " + meta.id() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                CampfireRecipeMeta deserialized = new Gson().fromJson(json.meta(), CampfireRecipeMeta.class);
                CampfireRecipe item = new CampfireRecipe(meta, deserialized);
                return item;
            }
            default -> throw new UnsupportedJsonVersionException("campfire", json.version());
        }

    }

}
