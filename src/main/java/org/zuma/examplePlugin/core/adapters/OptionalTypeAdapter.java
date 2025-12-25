package org.zuma.examplePlugin.core.adapters;

import com.google.gson.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

public class OptionalTypeAdapter<T> implements JsonSerializer<Optional<T>>, JsonDeserializer<Optional<T>> {

    @Override
    public JsonElement serialize(Optional<T> src, Type typeOfSrc, JsonSerializationContext context) {
        return src.map(context::serialize).orElse(JsonNull.INSTANCE);
    }

    @Override
    public Optional<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // Extract the wrapped type
        Type actualType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
        if (json == null || json.isJsonNull()) {
            return Optional.empty();
        } else {
            return Optional.of(context.deserialize(json, actualType));
        }
    }
}