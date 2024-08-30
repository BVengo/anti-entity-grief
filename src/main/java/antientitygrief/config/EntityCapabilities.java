package antientitygrief.config;

import antientitygrief.Utils;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Zombie;

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
        if (!(entity instanceof Turtle || entity instanceof Bat)) {
            this.with(TRAMPLE_EGGS);
        }
        return this;
    }

    private EntityCapabilities withBreakDoor(Entity entity) {
        // Only Zombie class (and variants) have the breakdoor goal
        if (entity instanceof Zombie) {
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

    public void set(Capabilities capability, boolean value) {
        capabilities.put(capability, value);
    }

    public boolean canDo(Capabilities capability) {
        return capabilities.getOrDefault(capability, true);
    }
}
