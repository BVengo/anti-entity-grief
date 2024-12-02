package antientitygrief.mixin.behaviours;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {
    @Redirect(method = "onEntityCollision", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/Entity;canModifyAt(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z"))
    private boolean redirectEntityCanModify(Entity entity, World world, BlockPos pos) {
        // Prevent burning entities from breaking powdered snow. Does not stop them from being doused.
        return (
            Configs.getGriefingOption(entity.getType(), Capabilities.MELT_SNOW) &&
            entity.canModifyAt(world, pos)
        );
    }
}
