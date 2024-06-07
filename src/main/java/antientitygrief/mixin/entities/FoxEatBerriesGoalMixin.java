package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/world/entity/animal/Fox$FoxEatBerriesGoal")
public class FoxEatBerriesGoalMixin {
	@Inject(method = "onReachedTarget", at = @At("HEAD"), cancellable = true)
	private void onOnReachedTarget(CallbackInfo ci) {
		if (!Configs.FOX.canDo(Capabilities.EAT_BLOCKS)) {
			ci.cancel();
		}
	}
}
