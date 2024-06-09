package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.world.entity.LightningBolt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningBolt.class)
public class LightningBoltMixin {
    @Inject(method = "spawnFire", at = @At("HEAD"), cancellable = true)
    private void onSpawnFire(CallbackInfo ci) {
        if(!Configs.LIGHTNING_BOLT.canDo(Capabilities.SET_FIRE)) {
            ci.cancel();
        }
    }
}
