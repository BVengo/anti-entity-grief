package antientitygrief.mixin;

import antientitygrief.AntiEntityGrief;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PowderSnowBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {
    @Redirect(method = "entityInside", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;mayInteract(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Z"))
    private boolean redirectEntityMayInteract(Entity entity, Level level, BlockPos pos) {
        // Prevent burning entities from breaking powdered snow.
        if(AntiEntityGrief.CONFIGS.getGriefingOption(entity)) {
            return false;
        }
        return entity.mayInteract(level, pos);
    }
}
