package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TntMinecartEntity.class)
public class TntMinecartEntityMixin {
    @WrapOperation(method = "explode(Lnet/minecraft/entity/damage/DamageSource;D)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;createExplosion(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/world/explosion/ExplosionBehavior;DDDFZLnet/minecraft/world/World$ExplosionSourceType;)V"))
    private void redirectExplode(ServerWorld world, Entity entity, DamageSource damageSource, ExplosionBehavior behaviour,
                                 double x, double y, double z, float strength, boolean createFire,
                                 World.ExplosionSourceType source, Operation<Boolean> original) {
        // Prevent block destruction from TNT minecart explosion
        if (!Configs.TNT_MINECART.canDo(Capabilities.EXPLODE_BLOCKS)) {
            source = World.ExplosionSourceType.NONE;
        }

        if(!Configs.TNT_MINECART.canDo(Capabilities.SET_FIRE)) {
            // Default false, but included in case it every changes.
            createFire = false;
        }

        original.call(world, entity, damageSource, behaviour, x, y, z, strength, createFire, source);
    }
}
