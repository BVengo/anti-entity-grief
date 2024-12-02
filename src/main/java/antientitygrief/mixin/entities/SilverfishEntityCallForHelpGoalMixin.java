package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.entity.mob.SilverfishEntity$CallForHelpGoal")
public class SilverfishEntityCallForHelpGoalMixin {
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private boolean redirectWake(GameRules instance, GameRules.Key<GameRules.BooleanRule> rule) {
        // Stop silverfish from waking up friends
        return instance.getBoolean(rule) && Configs.SILVERFISH.canDo(Capabilities.DESTROY_BLOCKS);
    }
}
