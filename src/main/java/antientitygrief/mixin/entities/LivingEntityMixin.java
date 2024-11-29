package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "onKilledBy", at = @At("HEAD"), cancellable = true)
    private void cancelOnKilledBy(CallbackInfo ci) {
        if(!Configs.WITHER.canDo(Capabilities.PLACE_BLOCKS)) {
            ci.cancel();
        }
    }
}
