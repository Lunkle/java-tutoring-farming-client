package network.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import event.stcevent.STCEvent;


public class GsonResponseDeserializer implements JsonDeserializer<STCEvent> {
	private static final String SESSION_PATH;
	private static final String GAME_PATH;

	static {
		String stcPath = STCEvent.class.getName();
		String path = stcPath.substring(0, stcPath.lastIndexOf('.') + 1);
		SESSION_PATH = path + "session.";
		GAME_PATH = path + "game.";
	}

	@Override
	public STCEvent deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		String clazzString = jsonObject.get("type").getAsString();
		if (clazzString.contains(".")) {
			throw new JsonParseException("Class not recognized");
		}
		Class<?> clazz = null;
		try {
			clazz = Class.forName(SESSION_PATH + clazzString);
		} catch (ClassNotFoundException e1) {
			try {
				clazz = Class.forName(GAME_PATH + clazzString);
			} catch (ClassNotFoundException e2) {
				throw new JsonParseException("Class not recognized");
			}
		}
		STCEvent deserialize = (STCEvent) context.deserialize(json, clazz);
		if (!jsonObject.has("extraMessages")) {
			deserialize.setExtraMessages(new String[0]);
		}
		return deserialize;
	}

}
