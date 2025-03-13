package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SmallFireballEntity.class)
public class SmallFireballEntityMixin extends AbstractFireballEntity {

    public SmallFireballEntityMixin(EntityType<? extends AbstractFireballEntity> entityType, World world) {
        super(entityType, world);
    }

    // Redirect `entity instanceof Mob`
    @WrapOperation(method = "onBlockHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private boolean redirectMobGriefing(GameRules gameRules, GameRules.Key<GameRules.BooleanRule> rule, Operation<Boolean> original) {
        // Stop mob from placing fires
        boolean gameRuleResult = original.call(gameRules, rule);

        Entity entity = this.getOwner();
        if (entity == null) {
            return gameRuleResult;
        }
        return gameRuleResult && Configs.getGriefingOption(entity.getType(), Capabilities.SET_FIRE);
    }
}
