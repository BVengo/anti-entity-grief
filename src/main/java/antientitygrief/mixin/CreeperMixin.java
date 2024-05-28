package antientitygrief.mixin;

import antientitygrief.AntiEntityGrief;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Creeper.class)
public class CreeperMixin {
	@Redirect(method = "explodeCreeper",
			at = @At(value = "INVOKE",
				target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;DDDFLnet/minecraft/world/level/Level$ExplosionInteraction;)Lnet/minecraft/world/level/Explosion;"))
	private Explosion redirectExplosion(Level level, Entity entity, double x, double y, double z, float strength, Level.ExplosionInteraction interaction) {
		if (!AntiEntityGrief.CONFIGS.getGriefingOption(EntityType.CREEPER)) {
			return null;
		}

		return level.explode(entity, x, y, z, strength, interaction);
	}
}
