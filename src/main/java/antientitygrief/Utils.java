package antientitygrief;

import antientitygrief.config.Capabilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;

public abstract class Utils {
	public static String getEntityId(EntityType<?> entityType) {
		return EntityType.getId(entityType).toString();
	}

	public static Capabilities getCapability(String capability) {
		try {
			return Capabilities.valueOf(capability.toUpperCase());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	public static Entity getRemovedEntity(EntityType<?> entityType) {
		Entity entity = entityType.create(AntiEntityGrief.overworld, SpawnReason.LOAD);
		if (entity != null) {
			entity.remove(Entity.RemovalReason.DISCARDED);
		}

		return entity;
	}
}
