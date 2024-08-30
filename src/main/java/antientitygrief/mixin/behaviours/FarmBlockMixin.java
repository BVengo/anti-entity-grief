package antientitygrief.mixin.behaviours;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FarmBlock.class)
public class FarmBlockMixin {
    @Inject(method = "turnToDirt", at = @At("HEAD"), cancellable = true)
    private static void onTurnToDirt(Entity entity, BlockState blockState, Level level, BlockPos blockPos, CallbackInfo ci) {
        /**
         * Handle trampling of farmland and crops.
         * - FC: Normal behaviour, since destruction of farmland and crops are both allowed. Trampling farmland will turn it into dirt and the crops will be dropped.
         * - F : Cannot destroy crops. Will only turn farmland into dirt if it has no crops above it.
         * - C : Cannot destroy farmland. Will still destroy crops when farmland is (normally) supposed to be turned into dirt.
         */

        // Can only check with a valid entity.
        if(entity == null) {
            return;
        }

        // Check block above for crop information.
        BlockPos aboveBlockPos = blockPos.above();
        BlockState aboveBlockState = level.getBlockState(aboveBlockPos);
        boolean hasCrops = aboveBlockState.getBlock() instanceof CropBlock;

        boolean canTrampleFarmland = Configs.getGriefingOption(entity.getType(), Capabilities.TRAMPLE_FARMLAND);
        boolean canTrampleCrops = Configs.getGriefingOption(entity.getType(), Capabilities.TRAMPLE_CROPS);

        if(canTrampleFarmland && (canTrampleCrops || !hasCrops)) {
            // Has permission to turn this block to dirt. Also has permission to trample crops (or there are no crops).
            return;
        }
        else if(canTrampleCrops && hasCrops) {
            // Can't trample the farmland, but can trample crops. Destroy the crop block and drop items.
            level.destroyBlock(aboveBlockPos, true);
        }

        // Unable to destroy the farmland.
        ci.cancel();
    }
}
