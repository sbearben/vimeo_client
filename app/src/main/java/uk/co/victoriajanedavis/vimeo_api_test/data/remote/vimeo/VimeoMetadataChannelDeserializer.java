package uk.co.victoriajanedavis.vimeo_api_test.data.remote.vimeo;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoConnection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoMetadataChannel;

public class VimeoMetadataChannelDeserializer implements JsonDeserializer<VimeoMetadataChannel>{

    @Override
    public VimeoMetadataChannel deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonObject jsonConnectionsObject = jsonObject.getAsJsonObject("connections");
        VimeoConnection users = context.deserialize(jsonConnectionsObject.get("users"), VimeoConnection.class);
        VimeoConnection videos = context.deserialize(jsonConnectionsObject.get("videos"), VimeoConnection.class);

        return new VimeoMetadataChannel(users, videos);
    }
}
