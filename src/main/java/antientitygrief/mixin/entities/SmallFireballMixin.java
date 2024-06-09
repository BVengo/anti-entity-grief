package antientitygrief.mixin.entities;

import antientitygrief.AntiEntityGrief;
import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SmallFireball.class)
public class SmallFireballMixin extends Fireball {

    public SmallFireballMixin(EntityType<? extends Fireball> entityType, Level level) {
        super(entityType, level);
    }

    // Redirect `entity instanceof Mob`
    @Redirect(method = "onHitBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z"))
    private boolean redirectMobGriefing(GameRules gameRules, GameRules.Key<GameRules.BooleanValue> key) {
        // Stop blaze from placing fires
        boolean gameRuleResult = gameRules.getBoolean(key);

        if(key != GameRules.RULE_MOBGRIEFING) {
            AntiEntityGrief.LOGGER.warn("Unexpected GameRules.Key in `SmallFireballMixin.redirectMobGriefing`: {}", key);
            return gameRuleResult;
        };

        Entity entity = this.getOwner();
        if (entity == null) {
            return gameRuleResult;
        }
        return gameRuleResult && Configs.getGriefingOption(entity.getType(), Capabilities.SET_FIRE);
    }
}
