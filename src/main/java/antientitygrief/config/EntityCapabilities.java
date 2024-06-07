package antientitygrief.config;

import antientitygrief.AntiEntityGrief;
import antientitygrief.Utils;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import java.util.*;

import static antientitygrief.config.Capabilities.TRAMPLE_CROPS;

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
        return this.withTrampleCrops();
    }

    public EntityCapabilities withTrampleCrops() {
        // From FarmBlock.class: entity.getBbWidth() * entity.getBbWidth() * entity.getBbHeight() > 0.512f
        EntityDimensions dimensions = entityType.getDimensions();
        boolean isLivingEntity = Utils.entityIsOfType(entityType, LivingEntity.class);
        boolean isLargeEntity = dimensions.width() * dimensions.width() * dimensions.height() > 0.512f;
        if (isLivingEntity && isLargeEntity) {
            return this.with(TRAMPLE_CROPS);
        }
        AntiEntityGrief.LOGGER.info("{} living ? {}.", Utils.getEntityId(entityType), isLivingEntity);
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
