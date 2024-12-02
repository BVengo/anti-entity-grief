package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.entity.passive.FoxEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoxEntity.EatBerriesGoal.class)
public class FoxEntityEatBerriesGoalMixin {
	@Inject(method = "eatBerries", at = @At("HEAD"), cancellable = true)
	private void onEatBerries(CallbackInfo ci) {
		if (!Configs.FOX.canDo(Capabilities.EAT_BLOCKS)) {
			ci.cancel();
		}
	}
}
