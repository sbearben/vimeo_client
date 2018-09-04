package uk.co.victoriajanedavis.vimeo_api_test.data.remote.vimeo;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoConnection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoMetadataComment;


public class VimeoMetadataCommentDeserializer implements JsonDeserializer<VimeoMetadataComment>{

    @Override
    public VimeoMetadataComment deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonObject jsonConnectionsObject = jsonObject.getAsJsonObject("connections");
        VimeoConnection replies = context.deserialize(jsonConnectionsObject.get("replies"), VimeoConnection.class);

        return new VimeoMetadataComment(replies);
    }
}
