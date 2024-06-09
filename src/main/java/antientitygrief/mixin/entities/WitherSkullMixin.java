package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WitherSkull.class)
public class WitherSkullMixin {
    @Redirect(method = "onHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;)Lnet/minecraft/world/level/Explosion;"))
    private Explosion redirectExplode(Level level, Entity entity, double x, double y, double z, float strength, boolean hasFire, Level.ExplosionInteraction interaction) {
        // Prevent block destruction from wither explosion
        if (!Configs.WITHER.canDo(Capabilities.EXPLODE_BLOCKS)) {
            interaction = Level.ExplosionInteraction.NONE;
        }

        return level.explode(entity, x, y, z, strength, interaction);
    }
}
