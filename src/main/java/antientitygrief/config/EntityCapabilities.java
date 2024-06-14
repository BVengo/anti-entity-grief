package antientitygrief.config;

import antientitygrief.Utils;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Zombie;

import java.util.*;

import static antientitygrief.config.Capabilities.TRAMPLE_CROPS;
import static antientitygrief.config.Capabilities.TRAMPLE_EGGS;

public class EntityCapabilities {
    private final Map<Capabilities, Boolean> capabilities;
    private final EntityType<?> entityType;

    public EntityCapabilities(EntityType<?> entityType) {
        this.capabilities = new TreeMap<>();
        this.entityType = entityType;
    }

    public EntityCapabilities with(Capabilities... capabilities) {
        for (Capabilities capability : capabilities) {
            this.capabilities.put(capability, true);
        }
        return this;
    }

    public EntityCapabilities withCalculated() {
        /**
         * Attaches generic capabilities based on specific entity attributes or classes.
         */
        Entity entity = Utils.getRemovedEntity(entityType);

        return (this
                .withTrampleCrops(entity)
                .withTrampleEggs(entity)
                .withMeltSnow(entity)
                .withBreakDoor(entity)
        );
    }

    private EntityCapabilities withTrampleCrops(Entity entity) {
        // From FarmBlock.class: entity.getBbWidth() * entity.getBbWidth() * entity.getBbHeight() > 0.512f
        EntityDimensions dimensions = entityType.getDimensions();

        boolean isLiving = entity instanceof LivingEntity;
        boolean isLarge = dimensions.width() * dimensions.width() * dimensions.height() > 0.512f;

        if (isLiving && isLarge) {
            this.with(TRAMPLE_CROPS);
        }
        return this;
    }

    private EntityCapabilities withTrampleEggs(Entity entity) {
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

    private EntityCapabilities withMeltSnow(Entity entity) {
        // From PowerSnowBlock.class: Any LivingEntity
        if(entity instanceof LivingEntity) {
            this.with(Capabilities.MELT_SNOW);
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
