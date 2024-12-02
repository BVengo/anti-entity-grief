package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderDragonEntity.class)
public class EnderDragonEntityMixin {
    @Inject(method = "destroyBlocks", at = @At("HEAD"), cancellable = true)
    private void cancelDestroyBlocks(CallbackInfoReturnable<Boolean> cir) {
        // Prevent block destruction when the ender dragon flies through them
        if (!Configs.ENDER_DRAGON.canDo(Capabilities.DESTROY_BLOCKS)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}