package antientitygrief.config;

import antientitygrief.AntiEntityGrief;
import antientitygrief.Utils;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Turtle;

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
        boolean isLivingEntity = Utils.entityIsOfType(entityType, LivingEntity.class);
        if (isLivingEntity) {
            this.withTrampleCrops().withTrampleEggs();
        }
        return this;
    }

    private EntityCapabilities withTrampleCrops() {
        // From FarmBlock.class: entity.getBbWidth() * entity.getBbWidth() * entity.getBbHeight() > 0.512f
        EntityDimensions dimensions = entityType.getDimensions();
        if (dimensions.width() * dimensions.width() * dimensions.height() > 0.512f) {
            this.with(TRAMPLE_CROPS);
        }
        return this;
    }

    private EntityCapabilities withTrampleEggs() {
        // From TurtleEggBlock.class: LivingEntity, except Turtle or Bat
        if(entityType != EntityType.TURTLE && entityType != EntityType.BAT) {
            this.with(TRAMPLE_EGGS);
        }
        return this;
    }

    public Set<Capabilities> getCapabilities() {
        return capabilities.keySet();
    }

    public void set(Capabilities capability, boolean value) {
        capabilities.put(capability, value);
    }

    public boolean canDo(Capabilities capability) {
        return capabilities.getOrDefault(capability, false);
    }
}
