package network.json;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import event.ctsevent.CTSEvent;

public class GsonRequestSerializer implements JsonSerializer<CTSEvent> {

	@Override
	public JsonElement serialize(CTSEvent event, Type type, JsonSerializationContext context) {
		JsonElement json = context.serialize(event);
		json.getAsJsonObject().addProperty("type", event.getClass().getSimpleName());
		return json;
	}

}
