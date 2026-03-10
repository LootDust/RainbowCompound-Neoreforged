package com.LunaGlaze.rainbowcompound.Linkage.elytraslot;

import com.LunaGlaze.rainbowcompound.Core.Class.ModElytraItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.concurrent.atomic.AtomicBoolean;

public class CuriosModElytraItem extends ModElytraItem implements ICurio {

    public CuriosModElytraItem(Properties pProperties) {
        super(pProperties);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
    }

    @Override
    public ItemStack getStack() {
        return new ItemStack(this);
    }

    @Override
    public void curioTick(SlotContext slotContext) {
        LivingEntity livingEntity = slotContext.entity();
        final ItemStack[] stack = new ItemStack[1];
        if(livingEntity == null || CuriosApi.getCuriosInventory(livingEntity).isEmpty()){ return; }
        ICuriosItemHandler curiosInventory = CuriosApi.getCuriosInventory(livingEntity).get();
        AtomicBoolean hasElytras = new AtomicBoolean(false);
        curiosInventory.getStacksHandler("back").ifPresent(slotInventory -> {
            int slotsNum = slotInventory.getSlots();
            for (int i=0 ; i<slotsNum && !hasElytras.get(); i++){
                ItemStack stack1 = slotInventory.getStacks().getStackInSlot(i);
                if( stack1.getItem() instanceof CuriosModElytraItem){
                    hasElytras.set(true);
                    stack[0] = stack1;
                }
            }
        });
        int ticks = livingEntity.getFallFlyingTicks();

        if (ticks > 0 && livingEntity.isFallFlying() && stack[0] != null) {
            stack[0].elytraFlightTick(livingEntity, ticks);
        }
    }
}
