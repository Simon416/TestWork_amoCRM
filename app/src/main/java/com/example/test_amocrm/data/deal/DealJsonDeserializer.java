package com.example.test_amocrm.data.deal;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DealJsonDeserializer implements JsonDeserializer<Deals> {

    @Override
    public Deals deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonElement jsonElementEmb = json.getAsJsonObject().get("_embedded");
        JsonArray jsonArrayItems = jsonElementEmb.getAsJsonObject().get("items").getAsJsonArray();

        List<Deal> listDeal = new ArrayList<>();
        for (JsonElement jsonItem : jsonArrayItems) {
            Deal deal = new Deal();
            deal.setName(jsonItem.getAsJsonObject().get("name").getAsString());
            deal.setSale(jsonItem.getAsJsonObject().get("sale").getAsInt());
            deal.setStatusId(jsonItem.getAsJsonObject().get("status_id").getAsInt());
            deal.setPipelineId(jsonItem.getAsJsonObject().get("pipeline_id").getAsInt());
            deal.setDataCreate(jsonItem.getAsJsonObject().get("created_at").getAsLong());
            listDeal.add(deal);
        }
        return new Deals(listDeal);
    }
}
