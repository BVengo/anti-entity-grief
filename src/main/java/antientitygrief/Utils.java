package antientitygrief;

import net.minecraft.world.entity.EntityType;

public abstract class Utils {
	public static String getEntityId(EntityType<?> entityType) {
		return EntityType.getKey(entityType).toString();
	}
}
