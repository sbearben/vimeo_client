package uk.co.victoriajanedavis.vimeo_api_test.data.remote.vimeo;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoConnection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoMetadataVideo;

public class VimeoMetadataVideoDeserializer implements JsonDeserializer<VimeoMetadataVideo>{

    @Override
    public VimeoMetadataVideo deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonObject jsonConnectionsObject = jsonObject.getAsJsonObject("connections");
        VimeoConnection comments = context.deserialize(jsonConnectionsObject.get("comments"), VimeoConnection.class);
        VimeoConnection likes = context.deserialize(jsonConnectionsObject.get("likes"), VimeoConnection.class);
        VimeoConnection recommendations = context.deserialize(jsonConnectionsObject.get("recommendations"), VimeoConnection.class);

        return new VimeoMetadataVideo(comments, likes, recommendations);
    }
}
