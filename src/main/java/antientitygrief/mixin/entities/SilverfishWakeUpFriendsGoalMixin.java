package antientitygrief.mixin.entities;

import antientitygrief.AntiEntityGrief;
import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.world.entity.monster.Silverfish$SilverfishWakeUpFriendsGoal")
public class SilverfishWakeUpFriendsGoalMixin {
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z"))
    private boolean redirectWake(GameRules gameRules, GameRules.Key<GameRules.BooleanValue> key) {
        // Stop silverfish from waking up infested stone around them
        boolean gameRuleResult = gameRules.getBoolean(key);

        if(key != GameRules.RULE_MOBGRIEFING) {
            AntiEntityGrief.LOGGER.warn("Unexpected GameRules.Key in `SilverfishWakeUpFriendsGoalMixin.redirectWake`: {}", key);
            return gameRuleResult;
        };

        return gameRuleResult && Configs.SILVERFISH.canDo(Capabilities.DESTROY_BLOCKS);
    }
}
