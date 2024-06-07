package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.RemoveBlockGoal;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TurtleEggBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RemoveBlockGoal.class)
public class RemoveBlockGoalMixin {
	// TODO: Stop zombies from breaking turtle eggs when the rule is disabled mid-trample.
	@Shadow @Final private Block blockToRemove;
	@Shadow @Final private Mob removerMob;

	@Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
	private void cancelRemoveBlockGoal(CallbackInfoReturnable<Boolean> cir) {
		EntityType<?> entityType = this.removerMob.getType();

		boolean cannotRemove = (
			(blockToRemove instanceof TurtleEggBlock && !Configs.getGriefingOption(entityType, Capabilities.TRAMPLE_EGGS)) ||
			(Configs.getGriefingOption(entityType, Capabilities.DESTROY_BLOCKS))
		);

		if (cannotRemove) {
			cir.setReturnValue(false);
			cir.cancel();
		}
	}
}
