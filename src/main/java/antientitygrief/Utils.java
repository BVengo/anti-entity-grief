package antientitygrief;

import antientitygrief.config.Capabilities;
import net.minecraft.world.entity.EntityType;

public abstract class Utils {
	public static String getEntityId(EntityType<?> entityType) {
		return EntityType.getKey(entityType).toString();
	}

	public static Capabilities getCapability(String capability) {
		try {
			return Capabilities.valueOf(capability.toUpperCase());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}
