package antientitygrief.mixin;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import antientitygrief.AntiEntityGrief;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

@Mixin(targets = "net/minecraft/world/entity/animal/Turtle$TurtleLayEggGoal")
public class TurtleLayEggGoalMixin {
    @Unique
	private boolean antientitygrief$canGrief;

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void onTickStart(CallbackInfo ci) {
        // Grab the config once, rather than multiple times
        antientitygrief$canGrief = Configs.TURTLE.canDo(Capabilities.PLACE_BLOCKS);
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
    private boolean redirectSetBlock(Level level, BlockPos pos, BlockState state, int flags) {
        // Prevent turtles from laying eggs (while still allowing them to dig and lose pregnancy state)
        if (antientitygrief$canGrief) {
            return level.setBlock(pos, state, flags);
        }
        return false;
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;gameEvent(Lnet/minecraft/core/Holder;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/gameevent/GameEvent$Context;)V"))
    private void redirectGameEvent(Level level, Holder<GameEvent> event, BlockPos pos, GameEvent.Context context) {
        // Prevent turtles from laying eggs (while still allowing them to dig and lose pregnancy state)
        if (antientitygrief$canGrief) {
            level.gameEvent(event, pos, context);
        }
    }
}