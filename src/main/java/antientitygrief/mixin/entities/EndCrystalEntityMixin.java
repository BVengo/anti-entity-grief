package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EndCrystalEntity.class)
public class EndCrystalEntityMixin {
    @Redirect(method = "damage",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;createExplosion(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/world/explosion/ExplosionBehavior;DDDFZLnet/minecraft/world/World$ExplosionSourceType;)V"))
    private void redirectExplode(ServerWorld world, Entity entity, DamageSource damageSource, ExplosionBehavior behaviour, double x, double y, double z, float power, boolean createFire, World.ExplosionSourceType source) {
        // Prevent block destruction from end crystal explosion
        if (!Configs.END_CRYSTAL.canDo(Capabilities.EXPLODE_BLOCKS)) {
            source = World.ExplosionSourceType.NONE;
        }

        if (!Configs.END_CRYSTAL.canDo(Capabilities.SET_FIRE)) {
            // Default is already false, but this is in case it ever changes.
            createFire = false;
        }

        world.createExplosion(entity, damageSource, behaviour, x, y, z, power, createFire, source);
    }
}
