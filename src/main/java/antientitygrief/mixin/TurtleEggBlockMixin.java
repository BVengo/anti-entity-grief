package antientitygrief.mixin;

import antientitygrief.AntiEntityGrief;
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
        if(!AntiEntityGrief.CONFIGS.getGriefingOption(entity)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
