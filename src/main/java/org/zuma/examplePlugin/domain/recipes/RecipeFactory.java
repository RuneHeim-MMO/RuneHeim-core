package org.zuma.examplePlugin.domain.recipes;

import com.google.gson.Gson;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.core.exception.UnsupportedJsonVersionException;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.recipes.base.AbstractRecipe;
import org.zuma.examplePlugin.domain.recipes.impl.*;
import org.zuma.examplePlugin.domain.recipes.meta.AbstractRecipeMeta;
import org.zuma.examplePlugin.domain.recipes.meta.ConcreteRecipeMeta;
import org.zuma.examplePlugin.domain.recipes.meta.v1.CampfireRecipeMeta;

import javax.annotation.Nullable;

public class RecipeFactory {

    public AbstractRecipe createRecipe(ItemStack displayItem, AbstractRecipeMeta meta, @Nullable VersionedJson json) {
        final ConcreteRecipeMeta recipeMeta = new ConcreteRecipeMeta(meta.id(), meta.craftTimeMs(), meta.requirements(), meta.ingredients(), meta.successResult(), meta.failResult(), meta.successXp());

        return switch (meta.type()) {
            case SMELTING_RECIPE -> new SmeltingRecipe(displayItem, recipeMeta);
            case SMITHING_RECIPE -> new SmithingRecipe(displayItem, recipeMeta);
            case COOKING_RECIPE -> new CookingRecipe(displayItem, recipeMeta);
            case CAMPFIRE_RECIPE -> createCampfireRecipe(recipeMeta, json);
            default -> new InventoryRecipe(displayItem, recipeMeta);

        };
    }

    private CampfireRecipe createCampfireRecipe(ConcreteRecipeMeta meta, @Nullable VersionedJson json) {
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
