package antientitygrief.mixin.entities;

import antientitygrief.AntiEntityGrief;
import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.world.entity.monster.Silverfish$SilverfishMergeWithStoneGoal")
public class SilverfishMergeWithStoneGoalMixin {
    @Redirect(method = "canUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z"))
    private boolean redirectMerge(GameRules gameRules, GameRules.Key<GameRules.BooleanValue> key) {
        // Stop silverfish from turning stone into infested stone
        boolean gameRuleResult = gameRules.getBoolean(key);

        if(key != GameRules.RULE_MOBGRIEFING) {
            AntiEntityGrief.LOGGER.warn("Unexpected GameRules.Key in `SilverfishMergeWithStoneGoalMixin.redirectMerge`: {}", key);
            return gameRuleResult;
        };

        return gameRuleResult && Configs.SILVERFISH.canDo(Capabilities.DESTROY_BLOCKS);
    }
}
