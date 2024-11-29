package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/entity/mob/EndermanEntity$PickUpBlockGoal")
public class EndermanEntityTakeBlockGoalMixin {
	@Inject(method = "canStart", at = @At("HEAD"), cancellable = true)
	private void onCanUse(CallbackInfoReturnable<Boolean> cir) {
		// Prevent endermen from taking blocks
		if (!Configs.ENDERMAN.canDo(Capabilities.DESTROY_BLOCKS)) {
			cir.setReturnValue(false); // or true depending on your logic
			cir.cancel();
		}
	}
}