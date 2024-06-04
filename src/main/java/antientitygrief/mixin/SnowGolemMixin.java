package antientitygrief.mixin;

import antientitygrief.AntiEntityGrief;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.SnowGolem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowGolem.class)
public class SnowGolemMixin {
    @Inject(method = "aiStep",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;defaultBlockState()Lnet/minecraft/world/level/block/state/BlockState;"),
            cancellable = true)
    private void cancelSnowPlacing(CallbackInfo ci) {
        if(!AntiEntityGrief.CONFIGS.getGriefingOption(EntityType.SNOW_GOLEM)) {
            ci.cancel();
        }
    }
}
