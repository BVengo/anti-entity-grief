package antientitygrief.mixin.behaviours;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CropBlock.class)
public class CropBlockMixin {
    @Redirect(method = "entityInside", at = @At(value = "INVOKE", target="Lnet/minecraft/world/level/Level;destroyBlock(Lnet/minecraft/core/BlockPos;ZLnet/minecraft/world/entity/Entity;)Z"))
    private boolean redirectDestroyBlock(Level level, BlockPos blockPos, boolean bl, Entity entity) {
        // Prevent entities from trampling crops
        if(!Configs.getGriefingOption(entity.getType(), Capabilities.TRAMPLE_CROPS)) {
            return false;
        }

        return level.destroyBlock(blockPos, bl, entity);
    }
}
