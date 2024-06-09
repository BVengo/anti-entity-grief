package antientitygrief.mixin.entities;

import antientitygrief.AntiEntityGrief;
import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WitherBoss.class)
public class WitherBossMixin {
    @Redirect(method = "customServerAiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z"))
    private boolean redirectDestroyBlocks(GameRules gameRules, GameRules.Key<GameRules.BooleanValue> key) {
        // Stop withers from destroying blocks as they move
        boolean gameRuleResult = gameRules.getBoolean(key);

        if(key != GameRules.RULE_MOBGRIEFING) {
            AntiEntityGrief.LOGGER.warn("Unexpected GameRules.Key in `WitherBossMixin.redirectDestroyBlocks`: {}", key);
            return gameRuleResult;
        };

        return gameRuleResult && Configs.WITHER.canDo(Capabilities.DESTROY_BLOCKS);
    }
}
