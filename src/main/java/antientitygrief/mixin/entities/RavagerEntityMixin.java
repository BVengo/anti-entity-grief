package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RavagerEntity.class)
public class RavagerEntityMixin {
    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target="Lnet/minecraft/world/World;breakBlock(Lnet/minecraft/util/math/BlockPos;ZLnet/minecraft/entity/Entity;)Z"))
    private boolean redirectBreakBlock(World instance, BlockPos blockPos, boolean b, Entity entity) {
        // Prevent ravager from destroying blocks
        return (Configs.RAVAGER.canDo(Capabilities.DESTROY_BLOCKS) && instance.breakBlock(blockPos, b, entity));
    }
}
