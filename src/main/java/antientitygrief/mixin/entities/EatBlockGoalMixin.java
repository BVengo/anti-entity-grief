package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EatBlockGoal.class)
public class EatBlockGoalMixin {
	@Shadow private Mob mob;

	@Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
	private void onCanUse(CallbackInfoReturnable<Boolean> cir) {
		// Prevent Sheep from eating blocks
		if(!Configs.getGriefingOption(this.mob.getType(), Capabilities.EAT_BLOCKS)) {
			cir.setReturnValue(false);
			cir.cancel();
		}
	}
}
