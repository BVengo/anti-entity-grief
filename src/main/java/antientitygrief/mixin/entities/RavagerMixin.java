package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Ravager.class)
public class RavagerMixin {
    @Redirect(method = "aiStep", at = @At(value = "INVOKE", target="Lnet/minecraft/world/level/Level;destroyBlock(Lnet/minecraft/core/BlockPos;ZLnet/minecraft/world/entity/Entity;)Z"))
    private boolean redirectDestroyBlock(Level level, BlockPos blockPos, boolean bl, Entity entity) {
        // Prevent ravager from destroying blocks
        return (Configs.RAVAGER.canDo(Capabilities.DESTROY_BLOCKS) && level.destroyBlock(blockPos, bl, entity));
    }
}
