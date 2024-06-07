package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(targets = "net/minecraft/world/entity/animal/Rabbit$RaidGardenGoal")
public class RaidGardenGoalMixin {
	@Inject(method = "canUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Rabbit;level()Lnet/minecraft/world/level/Level;"), cancellable = true)
	private void cancelRaidGardenGoal(CallbackInfoReturnable<Boolean> cir) {
		if(!Configs.RABBIT.canDo(Capabilities.EAT_BLOCKS)) {
			cir.setReturnValue(false);
			cir.cancel();
		}
	}
}
