package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(targets = "net/minecraft/entity/passive/RabbitEntity$EatCarrotCropGoal")
public class RabbitEntityEatCarrotCropGoalMixin {
	@Redirect(method = "canStart", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
	private boolean onGetMobGriefing(GameRules instance, GameRules.Key<GameRules.BooleanRule> rule) {
		// Prevents mobs from removing grass they eat. They still gain the benefits.
		return instance.getBoolean(rule) && Configs.RABBIT.canDo(Capabilities.EAT_BLOCKS);
	}
}
