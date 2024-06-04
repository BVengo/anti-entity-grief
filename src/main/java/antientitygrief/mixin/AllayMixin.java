package antientitygrief.mixin;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.world.entity.animal.allay.Allay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Allay.class)
public class AllayMixin {
    @Inject(method = "wantsToPickUp", at = @At("HEAD"), cancellable = true)
    private void onWantsToPickUp(CallbackInfoReturnable<Boolean> cir) {
        if(!Configs.ALLAY.canDo(Capabilities.PICKUP_ITEMS)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
