package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(LargeFireball.class)
public class LargeFireballMixin {
    @Redirect(method = "onHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;)Lnet/minecraft/world/level/Explosion;"))
    private Explosion redirectExplode(Level level, Entity entity, double x, double y, double z, float strength, boolean setFire, Level.ExplosionInteraction interaction) {
        // Prevent block destruction and/or fire from ghast
        if (!Configs.GHAST.canDo(Capabilities.EXPLODE_BLOCKS)) {
            interaction = Level.ExplosionInteraction.NONE;
        }

        if(!Configs.GHAST.canDo(Capabilities.SET_FIRE)) {
            setFire = false;
        }

        return level.explode(entity, x, y, z, strength, setFire, interaction);
    }
}
