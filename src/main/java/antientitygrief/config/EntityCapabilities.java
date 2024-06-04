package antientitygrief.config;

import net.minecraft.world.entity.EntityType;

import java.util.*;

public class EntityCapabilities {
    private final Map<Capabilities, Boolean> capabilities;

    public EntityCapabilities() {
        this.capabilities = new TreeMap<>();
    }

    public EntityCapabilities with(Capabilities... capabilities) {
        for (Capabilities capability : capabilities) {
            this.capabilities.put(capability, true);
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
