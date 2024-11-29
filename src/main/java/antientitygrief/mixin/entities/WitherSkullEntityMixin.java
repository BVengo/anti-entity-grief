package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WitherSkullEntity.class)
public class WitherSkullEntityMixin {
    @Redirect(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFZLnet/minecraft/world/World$ExplosionSourceType;)V"))
    private void onOnCollision(World world, Entity entity, double x, double y, double z, float power, boolean createFire, World.ExplosionSourceType source) {
        // Prevent block destruction and/or fire from Ghast. Needs updating if FireballEntity is ever used elsewhere.
        if (!Configs.WITHER.canDo(Capabilities.EXPLODE_BLOCKS)) {
            source = World.ExplosionSourceType.NONE;
        }

        if(!Configs.WITHER.canDo(Capabilities.SET_FIRE)) {
            // Default is already false, but this is in case it ever changes.
            createFire = false;
        }

        world.createExplosion(entity, x, y, z, power, createFire, source);
    }
}
