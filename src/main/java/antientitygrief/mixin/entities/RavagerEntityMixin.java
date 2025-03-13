package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.RavagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RavagerEntity.class)
public class RavagerEntityMixin {
    @WrapOperation(method = "tickMovement", at = @At(value = "INVOKE", target="Lnet/minecraft/server/world/ServerWorld;breakBlock(Lnet/minecraft/util/math/BlockPos;ZLnet/minecraft/entity/Entity;)Z"))
    private boolean redirectBreakBlock(ServerWorld world, BlockPos blockPos, boolean b, Entity entity, Operation<Boolean> original) {
        // Prevent ravager from destroying blocks
        return (Configs.RAVAGER.canDo(Capabilities.DESTROY_BLOCKS) && original.call(world, blockPos, b, entity));
    }
}
