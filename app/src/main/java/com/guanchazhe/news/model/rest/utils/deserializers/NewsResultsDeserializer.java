package com.guanchazhe.news.model.rest.utils.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by ranzh on 12/28/2015.
 */
public class NewsResultsDeserializer<T> implements JsonDeserializer<List<T>> {

    @Override
    public List<T> deserialize(JsonElement je, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        return new Gson().fromJson(je, typeOfT);
    }
}