package antientitygrief.mixin.behaviours;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
import net.minecraft.world.entity.ai.goal.DoorInteractGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BreakDoorGoal.class)
public class BreakDoorGoalMixin extends DoorInteractGoal {
	public BreakDoorGoalMixin(Mob mob) {
		super(mob);
	}

	@Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
	private void onCanUse(CallbackInfoReturnable<Boolean> cir) {
		// Prevent entities from breaking doors
		if(!Configs.getGriefingOption(this.mob.getType(), Capabilities.BREAK_DOORS)) {
			cir.setReturnValue(false);
			cir.cancel();
		}
	}

	@Inject(method = "canContinueToUse", at = @At("HEAD"), cancellable = true)
	private void canContinueToUse(CallbackInfoReturnable<Boolean> cir) {
		if(!Configs.getGriefingOption(this.mob.getType(), Capabilities.BREAK_DOORS)) {
			cir.setReturnValue(false);
			cir.cancel();
		}
	}
}
