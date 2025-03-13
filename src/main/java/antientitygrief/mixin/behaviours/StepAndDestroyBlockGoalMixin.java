package antientitygrief.mixin.behaviours;

import antientitygrief.AntiEntityGrief;
import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.block.TurtleEggBlock;
import net.minecraft.entity.ai.goal.StepAndDestroyBlockGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StepAndDestroyBlockGoal.class)
public class StepAndDestroyBlockGoalMixin {
	@Shadow @Final private Block targetBlock;
	@Shadow @Final private MobEntity stepAndDestroyMob;
	@Shadow private int counter;

	@Inject(method = "tick",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"),
			cancellable = true, id = AntiEntityGrief.MOD_ID + ":targetEgg" )
	private void onRemoveBlock(CallbackInfo ci) {
		EntityType<?> entityType = this.stepAndDestroyMob.getType();

		boolean cannotRemove = (
			(targetBlock instanceof TurtleEggBlock && !Configs.getGriefingOption(entityType, Capabilities.TRAMPLE_EGGS)) ||
			(!Configs.getGriefingOption(entityType, Capabilities.DESTROY_BLOCKS))
		);

		if(cannotRemove) {
			// Skip the block removal. Go to tick 2 for continuous sound.
			this.counter = 2;
			ci.cancel();
		}
	}
}
