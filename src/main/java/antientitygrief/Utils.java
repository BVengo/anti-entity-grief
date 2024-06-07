package antientitygrief;

import antientitygrief.config.Capabilities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
		/**
		 * This method is used to check if the generic component of the EntityType (i.e. the actual entity class)
		 * is of the same type as the provided class or any of its superclasses.
		 */
		Type entityTypeType = entityType.getClass().getGenericSuperclass();
		if (entityTypeType instanceof ParameterizedType) {
			Type[] typeArguments = ((ParameterizedType) entityTypeType).getActualTypeArguments();
			if (typeArguments.length > 0) {
				Class<?> actualType = (Class<?>) typeArguments[0];
				return isAssignableFrom(actualType, clazz);
			}
		}
		return false;
	}

	private static boolean isAssignableFrom(Class<?> actualType, Class<?> clazz) {
		/**
		 * This method is used to check if the provided class is the same as the actual type or any of its superclasses.
		 */
		while (actualType != null) {
			if (actualType.equals(clazz)) {
				return true;
			}
			actualType = actualType.getSuperclass();
		}
		return false;
	}
}
