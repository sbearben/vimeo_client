package uk.co.victoriajanedavis.vimeo_api_test.data.remote.vimeo;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoConnection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoInteraction;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoMetadataChannel;


public class VimeoMetadataChannelDeserializer implements JsonDeserializer<VimeoMetadataChannel>{

    @Override
    public VimeoMetadataChannel deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonObject jsonConnectionsObject = jsonObject.getAsJsonObject("connections");
        VimeoConnection users = context.deserialize(jsonConnectionsObject.get("users"), VimeoConnection.class);
        VimeoConnection videos = context.deserialize(jsonConnectionsObject.get("videos"), VimeoConnection.class);

        /* The format here has changed from the other deserializers because of inconsistencies with Vimeo's backend
         * and the treatment of the interaction metadata. For example, when a user isn't logged in, a User object
         * has no interactions object in its metadata, which means that in the UserDeserializer, the bellow line will
         * result in jsonInteractionsObject being null. However, with Channel objects when the user isn't logged in the
         * channel Vimeo object will contain an interactions object in its metadata however it will be set to JsonNull.
         * When a Gson Json object is JsonNull, trying to cast it to a JsonObject causes an exception to be
         * thrown (this happens in getAsJsonObject). Thus, below we had to change the code to use get("interactions") - which
         * returns a JsonElement (a supertype of sorts to JsonObject and JsonNull), check if jsonObject.get("interactions")
         * is JsonNull before setting it to jsonInteractionsObject in order to prevent this error from occurring during runtime.
         */
        if (!jsonObject.get("interactions").isJsonNull()) {
            JsonObject jsonInteractionsObject = jsonObject.getAsJsonObject("interactions").getAsJsonObject();
            VimeoInteraction follow = context.deserialize(jsonInteractionsObject.get("follow"), VimeoInteraction.class);
            return new VimeoMetadataChannel(users, videos, follow);
        }

        return new VimeoMetadataChannel(users, videos, null);
    }
}
