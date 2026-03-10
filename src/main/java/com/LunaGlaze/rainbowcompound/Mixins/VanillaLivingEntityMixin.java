package com.LunaGlaze.rainbowcompound.Mixins;

import com.LunaGlaze.rainbowcompound.Linkage.elytraslot.CuriosModElytraItem;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(LivingEntity.class)
public abstract class VanillaLivingEntityMixin extends Entity {
    public VanillaLivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot equipmentSlot);

    @Inject(method = "updateFallFlying", at = @At("HEAD"))
    private void updateFallFlying(CallbackInfo ci) {
        if (CuriosApi.getCuriosInventory((LivingEntity)(Object)this).isPresent()) {
            boolean hasElytras = CuriosApi.getCuriosInventory((LivingEntity) (Object)this).get().isEquipped(item -> item.getItem() instanceof CuriosModElytraItem);
            boolean flag = this.getSharedFlag(7);
            if (hasElytras && flag && !this.onGround() && !this.isPassenger() && !((LivingEntity)(Object)this).hasEffect(MobEffects.LEVITATION)) {
                ci.cancel();
            }
        }
    }
}
