package antientitygrief.mixin.entities;

import antientitygrief.AntiEntityGrief;
import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TurtleEggBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TurtleEggBlock.class)
public class TurtleEggBlockMixin {
    @Inject(method = "canDestroyEgg", at = @At("HEAD"), cancellable = true)
    private void onCanDestroyEgg(Level level, Entity entity, CallbackInfoReturnable<Boolean> cir) {
        // Prevent entities from trampling turtle eggs.
        if(!Configs.getGriefingOption(entity.getType(), Capabilities.TRAMPLE_EGGS)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
