package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WitherEntity.class)
public class WitherEntityMixin {
    @Redirect(method = "mobTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private boolean redirectDestroyBlocks(GameRules instance, GameRules.Key<GameRules.BooleanRule> rule) {
        // Stop withers from destroying blocks as they move
        return instance.getBoolean(rule) && Configs.WITHER.canDo(Capabilities.DESTROY_BLOCKS);
    }
}
