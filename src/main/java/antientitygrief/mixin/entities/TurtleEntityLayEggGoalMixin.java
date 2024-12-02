package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.block.BlockState;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.util.math.BlockPos;

@Mixin(targets = "net/minecraft/entity/passive/TurtleEntity$LayEggGoal")
public class TurtleEntityLayEggGoalMixin {
    /**
     * Controls if turtles can place their eggs or not. Does not exit from the 'onUse' function,
     * because we want the turtle to still lose its pregnancy state and show the digging animation.
     */
    @Unique
	private boolean antientitygrief$canGrief;

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void onTickStart(CallbackInfo ci) {
        // Grab the config once, rather than multiple times
        antientitygrief$canGrief = Configs.TURTLE.canDo(Capabilities.PLACE_EGGS);
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
    private boolean redirectSetBlock(World world, BlockPos pos, BlockState state, int flags) {
        // Handle the block change
        if (antientitygrief$canGrief) {
            return world.setBlockState(pos, state, flags);
        }
        return false;
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/World;emitGameEvent(Lnet/minecraft/registry/entry/RegistryEntry;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/event/GameEvent$Emitter;)V"))
    private void redirectGameEvent(World world, RegistryEntry<GameEvent> registryEntry, BlockPos blockPos, GameEvent.Emitter emitter) {
        // Handle the game event triggered by the block change
        if (antientitygrief$canGrief) {
            world.emitGameEvent(registryEntry, blockPos, emitter);
        }
    }
}