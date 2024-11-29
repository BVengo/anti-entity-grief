package antientitygrief.mixin.behaviours;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.block.PitcherCropBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PitcherCropBlock.class)
public class PitcherCropBlockMixin {
    @Redirect(method = "onEntityCollision", at = @At(
            value = "INVOKE",
            target="Lnet/minecraft/server/world/ServerWorld;breakBlock(Lnet/minecraft/util/math/BlockPos;ZLnet/minecraft/entity/Entity;)Z"
    ))
    private boolean redirectBreakBlock(ServerWorld world, BlockPos blockPos, boolean drop, Entity entity) {
        // Prevent entities from trampling pitcher crops
        return Configs.getGriefingOption(entity.getType(), Capabilities.TRAMPLE_CROPS) && world.breakBlock(blockPos, drop, entity);
    }
}
