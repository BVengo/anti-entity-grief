package antientitygrief.mixin;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnderDragon.class)
public class EnderDragonMixin {
    @Redirect(method = "checkWalls",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z"))
    private boolean redirectRemoveBlock(Level level, BlockPos blockPos, boolean bl) {
        // Prevent block destruction when the ender dragon flies through them
        if (!Configs.ENDER_DRAGON.canDo(Capabilities.DESTROY_BLOCKS)) {
            return false;
        }

        return level.removeBlock(blockPos, bl);
    }
}