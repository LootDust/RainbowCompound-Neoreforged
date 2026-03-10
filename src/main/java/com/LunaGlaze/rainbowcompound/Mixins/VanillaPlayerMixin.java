package com.LunaGlaze.rainbowcompound.Mixins;

import com.LunaGlaze.rainbowcompound.Linkage.elytraslot.CuriosModElytraItem;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(Player.class)
public class VanillaPlayerMixin {

    @Inject(method = "tryToStartFallFlying", at = @At("RETURN"))
    public void tryToStartFallFlying(CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() == false) {
            if (CuriosApi.getCuriosInventory((Player)(Object)this).isPresent()) {
                boolean hasElytras = CuriosApi.getCuriosInventory((Player)(Object)this).get().isEquipped(item -> item.getItem() instanceof CuriosModElytraItem);
                cir.setReturnValue(hasElytras);
            }
        }
    }
}
