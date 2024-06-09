package antientitygrief.mixin.entities;

import antientitygrief.AntiEntityGrief;
import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Redirect(method = "createWitherRose", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z"))
    private boolean redirectCreateWitherRose(GameRules gameRules, GameRules.Key<GameRules.BooleanValue> key) {
        // Stop withers from placing wither roses
        boolean gameRuleResult = gameRules.getBoolean(key);

        if(key != GameRules.RULE_MOBGRIEFING) {
            AntiEntityGrief.LOGGER.warn("Unexpected GameRules.Key in `LivingEntityMixin.createWitherRose`: {}", key);
            return gameRuleResult;
        };

        return gameRuleResult && Configs.WITHER.canDo(Capabilities.PLACE_BLOCKS);
    }
}
