package antientitygrief.config;

import antientitygrief.AntiEntityGrief;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class EntityConfigsSerializer implements JsonSerializer<EntityConfigs>, JsonDeserializer<EntityConfigs> {
	@Override
	public JsonElement serialize(EntityConfigs src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("version", EntityConfigs.CONFIG_VERSION);
		jsonObject.add("entities", context.serialize(src.getConfigList()));
		return jsonObject;
	}

	@Override
	public EntityConfigs deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();

		int version = jsonObject.get("version").getAsInt();

		if (version == 1) {
			return deserializeV1(jsonObject, context);
		} else {
			String msg = "Config version mismatch. Expected an integer between 1 and " + EntityConfigs.CONFIG_VERSION +
					" (inclusive), got " + version;

			AntiEntityGrief.LOGGER.error(msg);
			throw new JsonParseException(msg);
		}
	}


	private static EntityConfigs deserializeV1(JsonObject jsonObject, JsonDeserializationContext context) {
		Map<String, Boolean> entities = context.deserialize(jsonObject.get("entities"), new TypeToken<Map<String, Boolean>>() {}.getType());

		entities.forEach((entityId, enabled) -> {
			if (AntiEntityGrief.CONFIGS.contains(entityId)) {
				AntiEntityGrief.CONFIGS.setGriefingOption(entityId, enabled);
			} else {
				AntiEntityGrief.LOGGER.warn("Discarding unknown entity ID in config: " + entityId);
			}
		});

		return AntiEntityGrief.CONFIGS;
	}
}