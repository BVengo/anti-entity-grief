package antientitygrief.mixin.entities;

import antientitygrief.config.Capabilities;
import antientitygrief.config.Configs;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(FireballEntity.class)
public class FireballEntityMixin extends AbstractFireballEntity {

    public FireballEntityMixin(EntityType<? extends AbstractFireballEntity> entityType, World world) {
        super(entityType, world);
    }

    @WrapOperation(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFZLnet/minecraft/world/World$ExplosionSourceType;)V"))
    private void onOnCollision(World world, Entity entity, double x, double y, double z, float power, boolean createFire,
                               World.ExplosionSourceType source, Operation<Boolean> original) {
        // Prevent block destruction and/or fire creation
        Entity owner = this.getOwner();
        if (owner == null) {
            // No owner, so not tied to a specific entity
            return;
        }

        EntityType<?> ownerType = owner.getType();

        if (!Configs.getGriefingOption(ownerType, Capabilities.EXPLODE_BLOCKS)) {
            source = World.ExplosionSourceType.NONE;
        }

        if(!Configs.getGriefingOption(ownerType, Capabilities.SET_FIRE)) {
            createFire = false;
        }

        original.call(world, entity, x, y, z, power, createFire, source);
    }
}
