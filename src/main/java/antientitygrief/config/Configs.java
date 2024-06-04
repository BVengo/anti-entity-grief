package antientitygrief.config;

import antientitygrief.Utils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static antientitygrief.config.Capabilities.*;

public class Configs {
    /**
     * Registers all entity types against their 'griefing' capabilities. These will be configurable
     * by the user through commands, and are checked in mixins to allow / disallow behaviours.
     */
    public final static int CONFIG_VERSION = 1;
    private static Configs instance;

    private final static Map<String, EntityCapabilities> configDict = new TreeMap<>();
    private final static List<EntityType<?>> entityTypes = new ArrayList<>();


    public static final EntityCapabilities ALLAY = register(EntityType.ALLAY, false, MELT_SNOW, PICKUP_ITEMS);
    // AREA_EFFECT_CLOUD - N/A
    // TODO: Test all mobs below this point
    public static final EntityCapabilities ARMADILLO = register(EntityType.ARMADILLO, true);
    public static final EntityCapabilities ARMOR_STAND = register(EntityType.ARMOR_STAND, true);
    public static final EntityCapabilities ARROW = register(EntityType.ARROW, false);
    public static final EntityCapabilities AXOLOTL = register(EntityType.AXOLOTL, true);
    // BAT - N/A
    public static final EntityCapabilities BEE = register(EntityType.BEE, true);
    public static final EntityCapabilities BLAZE = register(EntityType.BLAZE, true, SET_FIRE);
    // BLOCK_DISPLAY - N/A
    // BOAT - N/A
    public static final EntityCapabilities BOGGED = register(EntityType.BOGGED, true);
    public static final EntityCapabilities BREEZE = register(EntityType.BREEZE, true);
    // BREEZE_WIND_CHARGE - Controlled by breeze.
    public static final EntityCapabilities CAMEL = register(EntityType.CAMEL, true);
    public static final EntityCapabilities CAT = register(EntityType.CAT, true);
    public static final EntityCapabilities CAVE_SPIDER = register(EntityType.CAVE_SPIDER, true);
    // CHEST_BOAT - N/A
    // CHEST_MINECART - N/A
    public static final EntityCapabilities CHICKEN = register(EntityType.CHICKEN, true);
    public static final EntityCapabilities COD = register(EntityType.COD, true);
    // COMMAND_BLOCK_MINECART - Not planned. Too complex working around commands.
    public static final EntityCapabilities COW = register(EntityType.COW, true);
    public static final EntityCapabilities CREEPER = register(EntityType.CREEPER, true, EXPLODE_BLOCKS);
    public static final EntityCapabilities DOLPHIN = register(EntityType.DOLPHIN, true);
    public static final EntityCapabilities DONKEY = register(EntityType.DONKEY, true);
    // DRAGON_FIREBALL - Controlled by ender dragon.
    public static final EntityCapabilities DROWNED = register(EntityType.DROWNED, true);
    // EGG - N/A
    public static final EntityCapabilities ELDER_GUARDIAN = register(EntityType.ELDER_GUARDIAN, true);
    public static final EntityCapabilities END_CRYSTAL = register(EntityType.END_CRYSTAL, false, EXPLODE_BLOCKS);
    public static final EntityCapabilities ENDER_DRAGON = register(EntityType.ENDER_DRAGON, true, DESTROY_BLOCKS);
    // ENDER_PEARL - N/A
    public static final EntityCapabilities ENDERMAN = register(EntityType.ENDERMAN, true, DESTROY_BLOCKS, PLACE_BLOCKS);
    public static final EntityCapabilities ENDERMITE = register(EntityType.ENDERMITE, true);
    public static final EntityCapabilities EVOKER = register(EntityType.EVOKER, true);
    // EVOKER_FANGS - Controlled by evoker.
    // EXPERIENCE_BOTTLE - N/A
    // EXPERIENCE_ORB - N/A
    // EYE_OF_ENDER - N/A
    // FALLING_BLOCK - N/A
    // FIREWORK_ROCKET - N/A
    public static final EntityCapabilities FOX = register(EntityType.FOX, true);
    public static final EntityCapabilities FROG = register(EntityType.FROG, true);
    // FURNACE_MINECART - N/A
    public static final EntityCapabilities GHAST = register(EntityType.GHAST, true, DESTROY_BLOCKS, SET_FIRE);
    public static final EntityCapabilities GIANT = register(EntityType.GIANT, true);  // TODO: Check if can break doors
    // GLOW_ITEM_FRAME - N/A
    public static final EntityCapabilities GLOW_SQUID = register(EntityType.GLOW_SQUID, true);
    public static final EntityCapabilities GOAT = register(EntityType.GOAT, true);
    public static final EntityCapabilities GUARDIAN = register(EntityType.GUARDIAN, true);
    public static final EntityCapabilities HOGLIN = register(EntityType.HOGLIN, true);
    // HOPPER_MINECART - N/A
    public static final EntityCapabilities HORSE = register(EntityType.HORSE, true);
    public static final EntityCapabilities HUSK = register(EntityType.HUSK, true);
    public static final EntityCapabilities ILLUSIONER = register(EntityType.ILLUSIONER, true);
    // INTERACTION - N/A
    public static final EntityCapabilities IRON_GOLEM = register(EntityType.IRON_GOLEM, true);
    // ITEM - N/A
    // ITEM_DISPLAY - N/A
    // ITEM_FRAME - N/A
    // OMINOUS_ITEM_SPAWNER - N/A
    // FIREBALL - Controlled by ghast.
    // LEASH_KNOT - N/A
    public static final EntityCapabilities LIGHTNING_BOLT = register(EntityType.LIGHTNING_BOLT, false, SET_FIRE);
    public static final EntityCapabilities LLAMA = register(EntityType.LLAMA, true);
    // LLAMA_SPIT - Controlled by llama.
    public static final EntityCapabilities MAGMA_CUBE = register(EntityType.MAGMA_CUBE, true);
    // MARKER - N/A
    // MINECART - N/A
    public static final EntityCapabilities MOOSHROOM = register(EntityType.MOOSHROOM, true);
    public static final EntityCapabilities MULE = register(EntityType.MULE, true);
    public static final EntityCapabilities OCELOT = register(EntityType.OCELOT, true);
    // PAINTING - N/A
    public static final EntityCapabilities PANDA = register(EntityType.PANDA, true);
    public static final EntityCapabilities PARROT = register(EntityType.PARROT, true);
    public static final EntityCapabilities PHANTOM = register(EntityType.PHANTOM, true);
    public static final EntityCapabilities PIG = register(EntityType.PIG, true);
    public static final EntityCapabilities PIGLIN = register(EntityType.PIGLIN, true);
    public static final EntityCapabilities PIGLIN_BRUTE = register(EntityType.PIGLIN_BRUTE, true);
    public static final EntityCapabilities PILLAGER = register(EntityType.PILLAGER, true);
    public static final EntityCapabilities POLAR_BEAR = register(EntityType.POLAR_BEAR, true);
    // POTION - N/A
    public static final EntityCapabilities PUFFERFISH = register(EntityType.PUFFERFISH, true);
    public static final EntityCapabilities RABBIT = register(EntityType.RABBIT, true);
    public static final EntityCapabilities RAVAGER = register(EntityType.RAVAGER, true);
    public static final EntityCapabilities SALMON = register(EntityType.SALMON, true);
    public static final EntityCapabilities SHEEP = register(EntityType.SHEEP, true);
    public static final EntityCapabilities SHULKER = register(EntityType.SHULKER, true);
    // SHULKER_BULLET - Controlled by shulker.
    public static final EntityCapabilities SILVERFISH = register(EntityType.SILVERFISH, true, DESTROY_BLOCKS);
    public static final EntityCapabilities SKELETON = register(EntityType.SKELETON, true);
    public static final EntityCapabilities SKELETON_HORSE = register(EntityType.SKELETON_HORSE, true);
    public static final EntityCapabilities SLIME = register(EntityType.SLIME, true);
    // SMALL_FIREBALL - Controlled by blaze.
    public static final EntityCapabilities SNIFFER = register(EntityType.SNIFFER, true);
    public static final EntityCapabilities SNOW_GOLEM = register(EntityType.SNOW_GOLEM, true, PLACE_BLOCKS);
    // SNOWBALL - N/A
    // SPAWNER_MINECART - N/A
    // SPECTRAL_ARROW - N/A
    public static final EntityCapabilities SPIDER = register(EntityType.SPIDER, true);
    public static final EntityCapabilities SQUID = register(EntityType.SQUID, true);
    public static final EntityCapabilities STRAY = register(EntityType.STRAY, true);
    public static final EntityCapabilities STRIDER = register(EntityType.STRIDER, true);
    public static final EntityCapabilities TADPOLE = register(EntityType.TADPOLE, true);
    // TEXT_DISPLAY - N/A
    public static final EntityCapabilities TNT = register(EntityType.TNT, false, EXPLODE_BLOCKS);
    public static final EntityCapabilities TNT_MINECART = register(EntityType.TNT_MINECART, false, EXPLODE_BLOCKS);
    // TRADER_LLAMA - Controlled by llama. (TODO: Don't miss this)
    // TRIDENT - Lightning controlled by LIGHTNING_BOLT
    public static final EntityCapabilities TROPICAL_FISH = register(EntityType.TROPICAL_FISH, true);
    public static final EntityCapabilities TURTLE = register(EntityType.TURTLE, true, PLACE_BLOCKS);
    public static final EntityCapabilities VEX = register(EntityType.VEX, true);
    public static final EntityCapabilities VILLAGER = register(EntityType.VILLAGER, true, FARM_CROPS); // TODO: Should this be split into place / destroy?
    public static final EntityCapabilities VINDICATOR = register(EntityType.VINDICATOR, true);
    // WANDERING_TRADER - Controlled by villager. (TODO: Don't miss this)
    public static final EntityCapabilities WARDEN = register(EntityType.WARDEN, true);
    public static final EntityCapabilities WIND_CHARGE = register(EntityType.WIND_CHARGE, false);
    public static final EntityCapabilities WITCH = register(EntityType.WITCH, true);
    public static final EntityCapabilities WITHER = register(EntityType.WITHER, true, DESTROY_BLOCKS, EXPLODE_BLOCKS);
    public static final EntityCapabilities WITHER_SKELETON = register(EntityType.WITHER_SKELETON, true);
    // WITHER_SKULL - Controlled by wither.
    public static final EntityCapabilities WOLF = register(EntityType.WOLF, true);
    public static final EntityCapabilities ZOGLIN = register(EntityType.ZOGLIN, true);
    public static final EntityCapabilities ZOMBIE = register(EntityType.ZOMBIE, true);
    public static final EntityCapabilities ZOMBIE_HORSE = register(EntityType.ZOMBIE_HORSE, true);
    public static final EntityCapabilities ZOMBIE_VILLAGER = register(EntityType.ZOMBIE_VILLAGER, true);
    public static final EntityCapabilities ZOMBIFIED_PIGLIN = register(EntityType.ZOMBIFIED_PIGLIN, true);
    public static final EntityCapabilities PLAYER = register(EntityType.PLAYER, false);
    // FISHING_BOBBER - N/A

    private Configs() { }

    public static Configs getInstance() {
        // Purely for serialization purposes.
        if(instance == null) {
            instance = new Configs();
        }
        return instance;
    }

    public static Map<String, EntityCapabilities> getConfigDict() {
        return configDict;
    }

    public static List<EntityType<?>> getEntityTypes() {
        return entityTypes;
    }

    public static List<Capabilities> getEntityCapabilities(EntityType<?> entityType) {
        EntityCapabilities entityCapabilities = configDict.get(Utils.getEntityId(entityType));
        if (entityCapabilities == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(entityCapabilities.getCapabilities());
    }

    public static boolean getGriefingOption(String entityId, Capabilities capability) {
        EntityCapabilities entityCapabilities = configDict.get(entityId);
        if (entityCapabilities == null) {
            return false;
        }
        return entityCapabilities.canDo(capability);
    }

    public static boolean getGriefingOption(EntityType<?> entityType, Capabilities capability) {
        return getGriefingOption(Utils.getEntityId(entityType), capability);
    }

    public static void setGriefingOption(String entityId, String capabilityString, boolean enabled) {
        EntityCapabilities entityCapabilities = configDict.get(entityId);
        if (entityCapabilities == null) {
            return;  // Entity not in config. TODO: Handle this better
        }

        Capabilities capability = Capabilities.valueOf(capabilityString);
        entityCapabilities.set(capability, enabled);
        ConfigParser.saveConfig();
    }

    public static EntityCapabilities register(EntityType<?> entityType, boolean mobDefaults, Capabilities... capabilities) {
        String entityId = Utils.getEntityId(entityType);
        EntityCapabilities entityCapabilities = new EntityCapabilities();

        entityCapabilities = entityCapabilities.with(capabilities);

        if (mobDefaults) {
            entityCapabilities = entityCapabilities.with(MELT_SNOW, TRAMPLE_CROPS, TRAMPLE_EGGS);
        }

        entityTypes.add(entityType);
        configDict.put(entityId, entityCapabilities);
        return entityCapabilities;
    }
}