package org.zuma.examplePlugin.domain.recipe.meta.v1;

import org.zuma.examplePlugin.domain.item.ItemRecord;

import javax.annotation.Nullable;

public record CampfireRecipeMeta(String initialName, String burningName, int burnTime, boolean isSoulCampfire,
                                 @Nullable ItemRecord despawnDrop) {
}
