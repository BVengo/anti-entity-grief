package antientitygrief.mixin.entities;

import antientitygrief.AntiEntityGrief;
import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.RemoveBlockGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TurtleEggBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RemoveBlockGoal.class)
public class RemoveBlockGoalMixin {
	// TODO: Stop zombies from breaking turtle eggs when the rule is disabled mid-trample. Still allows targeting of the
	// 	turtle egg, but the egg will not be broken.
	@Shadow @Final private Block blockToRemove;
	@Shadow @Final private Mob removerMob;
	@Shadow private int ticksSinceReachedGoal;

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z"), cancellable = true)
	private void skipDestroyBlock(CallbackInfo ci) {
		EntityType<?> entityType = this.removerMob.getType();

		boolean cannotRemove = (
			(blockToRemove instanceof TurtleEggBlock && !Configs.getGriefingOption(entityType, Capabilities.TRAMPLE_EGGS)) ||
			(!Configs.getGriefingOption(entityType, Capabilities.DESTROY_BLOCKS))
		);

		if(cannotRemove) {
			// Skip the block removal. Go to tick 2 for continuous sound.
			this.ticksSinceReachedGoal = 2;
			ci.cancel();
		}
	}
}
