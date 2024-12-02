package antientitygrief.mixin.behaviours;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EatGrassGoal.class)
public class EatGrassGoalMixin {
	@Final @Shadow private MobEntity mob;

	@Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
	private boolean onGetMobGriefing(GameRules instance, GameRules.Key<GameRules.BooleanRule> rule) {
		// Prevents mobs from removing grass they eat. They still gain the benefits.
		return instance.getBoolean(rule) && Configs.getGriefingOption(this.mob.getType(), Capabilities.EAT_BLOCKS);
	}
}
