package antientitygrief.mixin.behaviours;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.block.CropBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CropBlock.class)
public class CropBlockMixin {
    @WrapOperation(method = "onEntityCollision", at = @At(value = "INVOKE",
            target="Lnet/minecraft/server/world/ServerWorld;breakBlock(Lnet/minecraft/util/math/BlockPos;ZLnet/minecraft/entity/Entity;)Z"))
    private boolean redirectDestroyBlock(ServerWorld instance, BlockPos blockPos, boolean drop, Entity entity, Operation<Boolean> original) {
        // Prevent entities from trampling crops
        return Configs.getGriefingOption(entity.getType(), Capabilities.TRAMPLE_CROPS) && original.call(instance, blockPos, drop, entity);
    }
}
