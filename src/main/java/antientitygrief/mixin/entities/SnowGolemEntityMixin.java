package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SnowGolemEntity.class)
public class SnowGolemEntityMixin {
    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private boolean redirectSnowPlacement(GameRules instance, GameRules.Key<GameRules.BooleanRule> rule) {
        // Stop snow golems from placing snow
        return instance.getBoolean(rule) && Configs.SNOW_GOLEM.canDo(Capabilities.PLACE_BLOCKS);
    }
}
