package antientitygrief.mixin.behaviours;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.ai.goal.BreakDoorGoal;
import net.minecraft.entity.ai.goal.DoorInteractGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BreakDoorGoal.class)
public class BreakDoorGoalMixin extends DoorInteractGoal {
	public BreakDoorGoalMixin(MobEntity mob) {
		super(mob);
	}

	@Inject(method = "canStart", at = @At("HEAD"), cancellable = true)
	private void onCanUse(CallbackInfoReturnable<Boolean> cir) {
		// Prevent entities from breaking doors
		if(!Configs.getGriefingOption(this.mob.getType(), Capabilities.BREAK_DOORS)) {
			cir.setReturnValue(false);
			cir.cancel();
		}
	}

	@Inject(method = "shouldContinue", at = @At("HEAD"), cancellable = true)
	private void canContinueToUse(CallbackInfoReturnable<Boolean> cir) {
		if(!Configs.getGriefingOption(this.mob.getType(), Capabilities.BREAK_DOORS)) {
			cir.setReturnValue(false);
			cir.cancel();
		}
	}
}
