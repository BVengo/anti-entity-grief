package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SnowGolemEntity.class)
public class SnowGolemEntityMixin {
    @WrapOperation(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private boolean redirectSnowPlacement(GameRules gameRules, GameRules.Key<GameRules.BooleanRule> rule, Operation<Boolean> original) {
        // Stop snow golems from placing snow
        return original.call(gameRules, rule) && Configs.SNOW_GOLEM.canDo(Capabilities.PLACE_BLOCKS);
    }
}
