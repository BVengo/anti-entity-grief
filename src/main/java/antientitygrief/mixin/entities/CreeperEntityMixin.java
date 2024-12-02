package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CreeperEntity.class)
public class CreeperEntityMixin {
	@Redirect(method = "explode",
			at = @At(value = "INVOKE",
				target = "Lnet/minecraft/server/world/ServerWorld;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/World$ExplosionSourceType;)V"))
	private void redirectCreateExplosion(ServerWorld world, Entity entity, double x, double y, double z, float strength, World.ExplosionSourceType interaction) {
		// Prevent block destruction from creeper explosion
		if (!Configs.CREEPER.canDo(Capabilities.EXPLODE_BLOCKS)) {
			interaction = World.ExplosionSourceType.NONE;
		}

		world.createExplosion(entity, x, y, z, strength, interaction);
	}
}
