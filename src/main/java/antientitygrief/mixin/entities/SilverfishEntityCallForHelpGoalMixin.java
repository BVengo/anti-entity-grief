package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.entity.mob.SilverfishEntity$CallForHelpGoal")
public class SilverfishEntityCallForHelpGoalMixin {
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private boolean redirectWake(GameRules gameRules, GameRules.Key<GameRules.BooleanRule> rule, Operation<Boolean> operation) {
        // Stop silverfish from waking up friends
        return operation.call(gameRules, rule) && Configs.SILVERFISH.canDo(Capabilities.DESTROY_BLOCKS);
    }
}
