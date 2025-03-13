package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.entity.mob.SilverfishEntity$WanderAndInfestGoal")
public class SilverfishEntityWanderAndInfestGoalMixin {
    @WrapOperation(method = "canStart", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private boolean redirectMerge(GameRules gameRules, GameRules.Key<GameRules.BooleanRule> rule, Operation<Boolean> original) {
        // Stop silverfish from turning stone into infested stone
        return original.call(gameRules, rule) && Configs.SILVERFISH.canDo(Capabilities.DESTROY_BLOCKS);
    }
}
