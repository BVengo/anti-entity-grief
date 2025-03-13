package antientitygrief.mixin.behaviours;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.block.PitcherCropBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PitcherCropBlock.class)
public class PitcherCropBlockMixin {
    @WrapOperation(method = "onEntityCollision", at = @At(
            value = "INVOKE",
            target="Lnet/minecraft/server/world/ServerWorld;breakBlock(Lnet/minecraft/util/math/BlockPos;ZLnet/minecraft/entity/Entity;)Z"
    ))
    private boolean redirectBreakBlock(ServerWorld world, BlockPos blockPos, boolean drop, Entity entity, Operation<Boolean> original) {
        // Prevent entities from trampling pitcher crops
        return Configs.getGriefingOption(entity.getType(), Capabilities.TRAMPLE_CROPS) && original.call(world, blockPos, drop, entity);
    }
}
