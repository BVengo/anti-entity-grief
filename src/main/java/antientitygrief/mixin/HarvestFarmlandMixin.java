package antientitygrief.mixin;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.HarvestFarmland;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HarvestFarmland.class)
public class HarvestFarmlandMixin {
    @Inject(method = "checkExtraStartConditions(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/npc/Villager;)Z",
            at = @At("HEAD"), cancellable = true)
    private void onCheckExtraStartConditions(ServerLevel serverLevel, Villager villager, CallbackInfoReturnable<Boolean> cir) {
        // Prevent villagers from placing or harvesting crops
        if (!Configs.VILLAGER.canDo(Capabilities.FARM_CROPS)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}