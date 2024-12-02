package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TntEntity.class)
public class TntEntityMixin {
    @Redirect(method = "explode",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/world/explosion/ExplosionBehavior;DDDFZLnet/minecraft/world/World$ExplosionSourceType;)V"))
    private void redirectExplode(World world, Entity entity, DamageSource damageSource, ExplosionBehavior behaviour, double x, double y, double z, float strength, boolean createFire, World.ExplosionSourceType source) {
        // Prevent block destruction from TNT explosion
        if (!Configs.TNT.canDo(Capabilities.EXPLODE_BLOCKS)) {
            source = World.ExplosionSourceType.NONE;
        }

        if(!Configs.TNT.canDo(Capabilities.SET_FIRE)) {
            // Default false, but included in case it every changes.
            createFire = false;
        }

        world.createExplosion(entity, damageSource, behaviour, x, y, z, strength, createFire, source);
    }
}
