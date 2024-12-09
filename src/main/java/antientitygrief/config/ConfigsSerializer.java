package antientitygrief.config;

import antientitygrief.AntiEntityGrief;
import antientitygrief.Utils;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

public class ConfigsSerializer implements JsonSerializer<Configs>, JsonDeserializer<Configs> {
	public JsonElement serialize(Configs src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("version", Configs.CONFIG_VERSION);

		Map<String, EntityCapabilities> configDict = Configs.getConfigDict();
		JsonObject entities = new JsonObject();

		for (Map.Entry<String, EntityCapabilities> entry : configDict.entrySet()) {
			String entityId = entry.getKey();
			EntityCapabilities entityCap = entry.getValue();
			Set<Capabilities> capabilities = entityCap.getAvailableCapabilities();

			JsonObject entityObject = new JsonObject();
			for (Capabilities capability : capabilities) {
				boolean enabled = entityCap.canDo(capability);
				
				if(!enabled) {
					// Only include capabilities that are disabled. This will reduce the size of the config file.
					entityObject.addProperty(capability.name(), false);
				}
			}

			if(!entityObject.isEmpty()) {
				// Only include entities that have disabled capabilities.
				entities.add(entityId, entityObject);
			}
		}

		jsonObject.add("entities", entities);
		return jsonObject;
	}


	@Override
	public Configs deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();

		int version = jsonObject.get("version").getAsInt();

		if (version == 1) {
			deserializeV1(jsonObject, context);
		} else {
			String msg = "Config version mismatch. Expected an integer between 1 and " + Configs.CONFIG_VERSION +
					" (inclusive), got " + version;

			AntiEntityGrief.LOGGER.error(msg);
			throw new JsonParseException(msg);
		}

		return Configs.getInstance();
	}


	private static void deserializeV1(JsonObject jsonObject, JsonDeserializationContext context) {

		Map<String, Map<String, Boolean>> entities = context.deserialize(
				jsonObject.get("entities"),
				new TypeToken<Map<String, Map<String, Boolean>>>() {}.getType());

		entities.forEach((entityId, capabilities) -> {
			if(!Configs.getConfigDict().containsKey(entityId)) {
                AntiEntityGrief.LOGGER.warn("Dropping unknown entity in config: {}", entityId);
				return;
			}

			capabilities.forEach((capabilityName, enabled) -> {
				Capabilities capability = Utils.getCapability(capabilityName);
				if(capability == null) {
					AntiEntityGrief.LOGGER.warn("Dropping unknown capability in config: {}", capabilityName);
					return;
				}

				boolean success = Configs.setGriefingOption(entityId, capabilityName, enabled);
				if(!success) {
					AntiEntityGrief.LOGGER.warn("Ignored invalid capability {} for entity {}", capabilityName, entityId);
				}
			});
		});
	}
}