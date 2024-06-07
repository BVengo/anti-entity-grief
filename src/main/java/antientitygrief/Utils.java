package antientitygrief;

import antientitygrief.config.Capabilities;
import net.minecraft.world.entity.Entity;
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

	public static boolean entityIsOfType(EntityType<?> entityType, Class<?> clazz) {
		Entity entity = entityType.create(AntiEntityGrief.overworld);

		if (entity != null) {
			entity.remove(Entity.RemovalReason.DISCARDED);
			return clazz.isInstance(entity);
		}

		return false;
	}

}
