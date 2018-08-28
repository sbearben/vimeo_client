package uk.co.victoriajanedavis.vimeo_api_test.data.remote.vimeo;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoConnection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoInteraction;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoMetadataUser;


public class VimeoMetadataUserDeserializer implements JsonDeserializer<VimeoMetadataUser>{

    @Override
    public VimeoMetadataUser deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonObject jsonConnectionsObject = jsonObject.getAsJsonObject("connections");
        VimeoConnection followers = context.deserialize(jsonConnectionsObject.get("followers"), VimeoConnection.class);
        VimeoConnection following = context.deserialize(jsonConnectionsObject.get("following"), VimeoConnection.class);
        VimeoConnection likes = context.deserialize(jsonConnectionsObject.get("likes"), VimeoConnection.class);
        VimeoConnection videos = context.deserialize(jsonConnectionsObject.get("videos"), VimeoConnection.class);

        JsonObject jsonInteractionsObject = jsonObject.getAsJsonObject("interactions");
        if (jsonInteractionsObject != null) {
            VimeoInteraction follow = context.deserialize(jsonInteractionsObject.get("follow"), VimeoInteraction.class);
            return new VimeoMetadataUser(followers, following, likes, videos, follow);
        }

        return new VimeoMetadataUser(followers, following, likes, videos, null);
    }
}
