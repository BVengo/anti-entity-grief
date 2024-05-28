package antientitygrief.mixin;

import antientitygrief.AntiEntityGrief;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/world/entity/monster/EnderMan$EndermanLeaveBlockGoal")
public class EndermanLeaveBlockGoalMixin {
	@Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
	private void onCanUse(CallbackInfoReturnable<Boolean> cir) {
		if (!AntiEntityGrief.CONFIGS.getGriefingOption(EntityType.ENDERMAN)) {
			cir.setReturnValue(false); // or true depending on your logic
			cir.cancel();
		}
	}
}