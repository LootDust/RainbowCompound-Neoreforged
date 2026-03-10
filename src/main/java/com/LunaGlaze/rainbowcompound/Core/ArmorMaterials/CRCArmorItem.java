package com.LunaGlaze.rainbowcompound.Core.ArmorMaterials;


import com.LunaGlaze.rainbowcompound.LunaUtils;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import static net.minecraft.world.entity.EquipmentSlot.LEGS;

public class CRCArmorItem extends ArmorItem {

    public CRCArmorItem(ArmorMaterial pMaterial, Type pSlot, Properties pProperties) {
        super(Holder.direct(pMaterial), pSlot, pProperties.stacksTo(1));
    }

}
