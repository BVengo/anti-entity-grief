package antientitygrief.config;

import antientitygrief.SuggestionController;
import antientitygrief.Utils;
import net.minecraft.world.entity.EntityType;

import java.util.Map;
import java.util.TreeMap;

public class EntityConfigs {
	private static EntityConfigs instance;

	public static final int CONFIG_VERSION = 1;
	private final Map<String, Boolean> configList;

	private EntityConfigs() {
		configList = new TreeMap<>();
		initConfigs();
	}

	public static EntityConfigs getInstance() {
		if (instance == null) {
			instance = new EntityConfigs();
		}
		return instance;
	}

	public Map<String, Boolean> getConfigList() {
		return configList;
	}

	public boolean contains(String entityId) {
		return configList.containsKey(entityId);
	}

	public boolean contains(EntityType<?> entityType) {
		String entityId = Utils.getEntityId(entityType);
		return contains(entityId);
	}

	public boolean getGriefingOption(String entityId) {
		return configList.getOrDefault(entityId, true);
	}

	public boolean getGriefingOption(EntityType<?> entityType) {
		String entityId = Utils.getEntityId(entityType);
		return getGriefingOption(entityId);
	}

	public void setGriefingOption(String entityId, boolean enabled) {
		configList.put(entityId, enabled);
		ConfigParser.saveConfig();
	}

	public void setGriefingOption(EntityType<?> entityType, boolean enabled) {
		String entityId = Utils.getEntityId(entityType);
		setGriefingOption(entityId, enabled);
	}

	private void initConfigs() {
		SuggestionController.ENTITY_SELECTION.forEach(entityType -> {
			String entityId = Utils.getEntityId(entityType); //Component.translatable(Util.makeDescriptionId("entity", EntityType.getKey(entityType))).getString();
			configList.put(entityId, true);
		});
	}
}
