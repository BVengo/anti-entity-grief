package antientitygrief.mixin.entities;

import antientitygrief.AntiEntityGrief;
import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.entity.ai.brain.task.FarmerVillagerTask;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FarmerVillagerTask.class)
public class FarmerVillagerTaskMixin {
    @Inject(method = "shouldRun(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/VillagerEntity;)Z",
            at = @At("HEAD"), cancellable = true, id = AntiEntityGrief.MOD_ID + ":villagerFarmCrops" )
    private void onShouldRun(ServerWorld serverWorld, VillagerEntity villagerEntity, CallbackInfoReturnable<Boolean> cir) {
        // Prevent villagers from placing or harvesting crops
        if (!Configs.VILLAGER.canDo(Capabilities.FARM_CROPS)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}