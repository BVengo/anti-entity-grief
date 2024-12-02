package antientitygrief.mixin.behaviours;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.block.CropBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FarmlandBlock.class)
public class FarmBlockMixin {
    @Inject(method = "setToDirt", at = @At("HEAD"), cancellable = true)
    private static void onSetToDirt(Entity entity, BlockState blockState, World world, BlockPos blockPos, CallbackInfo ci) {
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
        BlockPos aboveBlockPos = blockPos.up();
        BlockState aboveBlockState = world.getBlockState(aboveBlockPos);
        boolean hasCrops = aboveBlockState.getBlock() instanceof CropBlock;

        boolean canTrampleFarmland = Configs.getGriefingOption(entity.getType(), Capabilities.TRAMPLE_FARMLAND);
        boolean canTrampleCrops = Configs.getGriefingOption(entity.getType(), Capabilities.TRAMPLE_CROPS);

        if(canTrampleFarmland && (canTrampleCrops || !hasCrops)) {
            // Has permission to turn this block to dirt. Also has permission to trample crops (or there are no crops).
            return;
        }
        else if(canTrampleCrops && hasCrops) {
            // Can't trample the farmland, but can trample crops. Destroy the crop block and drop items.
            world.breakBlock(aboveBlockPos, true);
        }

        // Unable to destroy the farmland.
        ci.cancel();
    }
}
