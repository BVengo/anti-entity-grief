package antientitygrief.mixin;

import antientitygrief.AntiEntityGrief;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecartTNT.class)
public class MinecartTNTMixin {
    @Redirect(method = "explode(Lnet/minecraft/world/damagesource/DamageSource;D)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;)Lnet/minecraft/world/level/Explosion;"))
    private Explosion redirectExplode(Level level, Entity entity, DamageSource damageSource, @Nullable ExplosionDamageCalculator explosionDamageCalculator, double x, double y, double z, float strength, boolean hasFire, Level.ExplosionInteraction interaction) {
        if (!AntiEntityGrief.CONFIGS.getGriefingOption(EntityType.TNT_MINECART)) {
            interaction = Level.ExplosionInteraction.NONE;
        }

        return level.explode(entity, damageSource, explosionDamageCalculator, x, y, z, strength, hasFire, interaction);
    }
}
