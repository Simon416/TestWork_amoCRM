package com.example.test_amocrm.data.pipeline;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PipelinesJsonDeserializer implements JsonDeserializer<Pipelines> {

    @Override
    public Pipelines deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject pipelinesJsonObject = json.getAsJsonObject()
                .get("_embedded").getAsJsonObject()
                .get("pipelines").getAsJsonObject();

        List<Pipeline> listPipeline = new ArrayList<>();
        for(Map.Entry<String, JsonElement> mapPipeline : pipelinesJsonObject.entrySet() ) {
            JsonObject pipelineJsonObject = mapPipeline.getValue().getAsJsonObject();
            String namePipeline = pipelineJsonObject.get("name").getAsString();
            int idPipeline = pipelineJsonObject.get("id").getAsInt();

            JsonObject statusesJsonObject = pipelineJsonObject.get("statuses").getAsJsonObject();
            List<Status> listStatus = new ArrayList<>();
            for (Map.Entry<String, JsonElement> mapStatus :statusesJsonObject.entrySet()){
                JsonObject statusJsonObject = mapStatus.getValue().getAsJsonObject();
                String nameStatus = statusJsonObject.get("name").getAsString();
                int idStatus = statusJsonObject.get("id").getAsInt();
                listStatus.add(new Status(nameStatus,idStatus));
            }
            listPipeline.add(new Pipeline(idPipeline,namePipeline, listStatus));
        }

        return new Pipelines(listPipeline);
    }
}
