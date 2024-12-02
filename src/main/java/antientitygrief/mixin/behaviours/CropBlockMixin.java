package antientitygrief.mixin.behaviours;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.block.CropBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CropBlock.class)
public class CropBlockMixin {
    @Redirect(method = "onEntityCollision", at = @At(value = "INVOKE",
            target="Lnet/minecraft/world/World;breakBlock(Lnet/minecraft/util/math/BlockPos;ZLnet/minecraft/entity/Entity;)Z"))
    private boolean redirectDestroyBlock(World instance, BlockPos blockPos, boolean drop, Entity entity) {
        // Prevent entities from trampling crops
        return Configs.getGriefingOption(entity.getType(), Capabilities.TRAMPLE_CROPS) && instance.breakBlock(blockPos, drop, entity);
    }
}
