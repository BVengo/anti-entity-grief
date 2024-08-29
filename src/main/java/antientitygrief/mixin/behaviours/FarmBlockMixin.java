package antientitygrief.mixin.behaviours;

import antientitygrief.AntiEntityGrief;
import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FarmBlock.class)
public class FarmBlockMixin {
    @Inject(method = "turnToDirt", at = @At("HEAD"), cancellable = true)
    private static void onTurnToDirt(Entity entity, BlockState state, Level level, BlockPos pos, CallbackInfo ci) {
        // Prevent entities from turning farmland into dirt.
        if(entity != null && !Configs.getGriefingOption(entity.getType(), Capabilities.TRAMPLE_FARMLAND)) {
            // TODO: Check if the block above is a crop block. If TRAMPLE_CROPS is enabled, destroy the crops but
            //  keep the farmland

            // Alternatively, only have TRAMPLE_CROPS for ravager and the rest of the mobs have the default farmland
            // trampling behaviour.
            ci.cancel();
        }
    }
}
