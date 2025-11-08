package org.zuma.examplePlugin.domain.recipe.persistence;

import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.recipe.meta.AbstractRecipeMeta;

import javax.annotation.Nullable;

public record RecipeEntity(AbstractRecipeMeta meta, @Nullable VersionedJson json) {
}