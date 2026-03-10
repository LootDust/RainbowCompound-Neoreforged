package com.LunaGlaze.rainbowcompound.Mixins;

import com.LunaGlaze.rainbowcompound.Linkage.elytraslot.CuriosModElytraItem;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

@Mixin(LocalPlayer.class)
public class VanillaLocalPlayerMixin {
    @ModifyVariable(method = "aiStep", at = @At(value = "STORE", target = "itemStack"))
    ItemStack aiStep(ItemStack value) {
        if (!value.canElytraFly((LocalPlayer)(Object)this)) {
            if (CuriosApi.getCuriosInventory((LocalPlayer)(Object)this).isPresent()) {
                ICuriosItemHandler handler = CuriosApi.getCuriosInventory((LivingEntity) (Object)this).get();
                if (handler.findFirstCurio(item -> item.getItem() instanceof CuriosModElytraItem).isPresent()) {
                    return handler.findFirstCurio(item -> item.getItem() instanceof CuriosModElytraItem).get().stack();
                }
            }
        }
        return value;
    }
}
