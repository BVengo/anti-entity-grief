package antientitygrief.config;

import antientitygrief.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.mob.ZombieEntity;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static antientitygrief.config.Capabilities.TRAMPLE_CROPS;
import static antientitygrief.config.Capabilities.TRAMPLE_EGGS;
import static antientitygrief.config.Capabilities.TRAMPLE_FARMLAND;


public class EntityCapabilities {
    private final Map<Capabilities, Boolean> capabilities;
    private final EntityType<?> entityType;

    public EntityCapabilities(EntityType<?> entityType) {
        this.capabilities = new TreeMap<>();
        this.entityType = entityType;
    }

    public EntityCapabilities with(Capabilities... capabilities) {
        for (Capabilities capability : capabilities) {
            this.capabilities.putIfAbsent(capability, true);
        }
        return this;
    }

    public EntityCapabilities withCalculated() {
        // Attaches generic capabilities based on specific entity attributes or classes.
        Entity entity = Utils.getRemovedEntity(entityType);

        // The player will return a null entity, so we need an explicit check.
        boolean isLiving = (entityType == EntityType.PLAYER || entity instanceof LivingEntity);

        if(isLiving) {
            this
                .withTrampleCropsAndFarmland()
                .withTrampleEggs(entity)
                .withBreakDoor(entity)
                .with(Capabilities.MELT_SNOW);
        }

        return this;
    }

    private EntityCapabilities withTrampleCropsAndFarmland() {
        // Only called if the entity is a living entity

        // From FarmBlock.class: entity.getBbWidth() * entity.getBbWidth() * entity.getBbHeight() > 0.512f
        // the Bb dimensions from entity are the same as the width, height from EntityType
        if (entityType.getWidth() * entityType.getWidth() * entityType.getHeight() > 0.512f) {
            this.with(TRAMPLE_CROPS).with(TRAMPLE_FARMLAND);
        }
        return this;
    }

    private EntityCapabilities withTrampleEggs(Entity entity) {
        // Only called if the entity is a living entity

        // From TurtleEggBlock.class: LivingEntity, except Turtle or Bat
        if (!(entity instanceof TurtleEntity || entity instanceof BatEntity)) {
            this.with(TRAMPLE_EGGS);
        }
        return this;
    }

    private EntityCapabilities withBreakDoor(Entity entity) {
        // Only Zombie class (and variants) have the breakdoor goal
        if (entity instanceof ZombieEntity) {
            this.with(Capabilities.BREAK_DOORS);
        }
        return this;
    }

    public Set<Capabilities> getAvailableCapabilities() {
        return capabilities.keySet();
    }

    public Map<Capabilities, Boolean> getCapabilities() {
        return capabilities;
    }

    public boolean set(Capabilities capability, boolean value) {
        if (!capabilities.containsKey(capability)) {
            return false;
        }
        capabilities.put(capability, value);
        return true;
    }

    public boolean canDo(Capabilities capability) {
        return capabilities.getOrDefault(capability, true);
    }
}
