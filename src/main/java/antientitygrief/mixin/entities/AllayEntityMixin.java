package antientitygrief.mixin.entities;

import antientitygrief.AntiEntityGrief;
import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.entity.passive.AllayEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AllayEntity.class)
public class AllayEntityMixin {
    @Inject(method = "canPickUpLoot", at = @At("HEAD"), cancellable = true, id = AntiEntityGrief.MOD_ID + ":allayPickup" )
    private void onWantsToPickUp(CallbackInfoReturnable<Boolean> cir) {
        if(!Configs.ALLAY.canDo(Capabilities.PICKUP_ITEMS)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
