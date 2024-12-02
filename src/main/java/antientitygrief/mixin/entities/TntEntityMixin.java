package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.entity.TntEntity;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TntEntity.class)
public class TntEntityMixin {
    @Redirect(method = "explode",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/World$ExplosionSourceType;)Lnet/minecraft/world/explosion/Explosion;"))
    private Explosion redirectExplode(World instance, Entity entity, double x, double y, double z, float power, World.ExplosionSourceType explosionSourceType) {
        // Prevent block destruction from TNT explosion
        if (!Configs.TNT.canDo(Capabilities.EXPLODE_BLOCKS)) {
            explosionSourceType = World.ExplosionSourceType.NONE;
        }

        return instance.createExplosion(entity, x, y, z, power, explosionSourceType);
    }
}
