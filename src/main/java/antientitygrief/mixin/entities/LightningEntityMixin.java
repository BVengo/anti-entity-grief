package antientitygrief.mixin.entities;

import antientitygrief.AntiEntityGrief;
import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.entity.LightningEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningEntity.class)
public class LightningEntityMixin {
    @Inject(method = "spawnFire", at = @At("HEAD"), cancellable = true, id = AntiEntityGrief.MOD_ID + ":lightningFire" )
    private void onSpawnFire(CallbackInfo ci) {
        if(!Configs.LIGHTNING_BOLT.canDo(Capabilities.SET_FIRE)) {
            ci.cancel();
        }
    }
}
