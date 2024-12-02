package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TntMinecartEntity.class)
public class TntMinecartEntityMixin {
    @Redirect(method = "explode(Lnet/minecraft/entity/damage/DamageSource;D)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/world/explosion/ExplosionBehavior;DDDFZLnet/minecraft/world/World$ExplosionSourceType;)Lnet/minecraft/world/explosion/Explosion;"))
    private Explosion redirectExplode(World instance, Entity entity, DamageSource damageSource, ExplosionBehavior behavior, double x, double y, double z, float power, boolean createFire, World.ExplosionSourceType explosionSourceType) {
        // Prevent block destruction from TNT minecart explosion
        if (!Configs.TNT_MINECART.canDo(Capabilities.EXPLODE_BLOCKS)) {
            explosionSourceType = World.ExplosionSourceType.NONE;
        }

        if(!Configs.TNT_MINECART.canDo(Capabilities.SET_FIRE)) {
            // Default false, but included in case it every changes.
            createFire = false;
        }

        return instance.createExplosion(entity, damageSource, behavior, x, y, z, power, createFire, explosionSourceType);
    }
}
