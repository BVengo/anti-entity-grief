package antientitygrief.mixin.behaviours;

import antientitygrief.AntiEntityGrief;
import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.block.TurtleEggBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TurtleEggBlock.class)
public class TurtleEggBlockMixin {
    @Inject(method = "breaksEgg", at = @At("HEAD"), cancellable = true, id = AntiEntityGrief.MOD_ID + ":trampleEgg" )
    private void onBreaksEgg(ServerWorld world, Entity entity, CallbackInfoReturnable<Boolean> cir) {
        // Prevent entities from trampling turtle eggs.
        if(!Configs.getGriefingOption(entity.getType(), Capabilities.TRAMPLE_EGGS)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
