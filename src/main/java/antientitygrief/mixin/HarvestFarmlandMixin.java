package antientitygrief.mixin;

import antientitygrief.AntiEntityGrief;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
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
        if (!AntiEntityGrief.CONFIGS.getGriefingOption(EntityType.VILLAGER)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}