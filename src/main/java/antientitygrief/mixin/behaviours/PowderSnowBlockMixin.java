package antientitygrief.mixin.behaviours;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.block.PowderSnowBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {
    @WrapOperation(method = "method_67681", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/Entity;canModifyAt(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;)Z"))
    private static boolean redirectDestroyBlock(Entity entity, ServerWorld world, BlockPos pos, Operation<Boolean> original) {
        // Prevent burning entities from breaking powdered snow. Does not stop them from being doused.
        return (
            Configs.getGriefingOption(entity.getType(), Capabilities.MELT_SNOW) &&
            original.call(entity, world, pos)
        );
    }
}
