package antientitygrief.mixin;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PrimedTnt.class)
public class PrimedTntMixin {
    @Redirect(method = "explode",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;DDDFLnet/minecraft/world/level/Level$ExplosionInteraction;)Lnet/minecraft/world/level/Explosion;"))
    private Explosion redirectExplode(Level level, Entity entity, double x, double y, double z, float strength, Level.ExplosionInteraction interaction) {
        // Prevent block destruction from TNT explosion
        if (!Configs.TNT.canDo(Capabilities.EXPLODE_BLOCKS)) {
            interaction = Level.ExplosionInteraction.NONE;
        }

        return level.explode(entity, x, y, z, strength, interaction);
    }
}
