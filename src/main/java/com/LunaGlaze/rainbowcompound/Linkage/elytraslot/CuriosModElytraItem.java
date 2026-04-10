package com.LunaGlaze.rainbowcompound.Linkage.elytraslot;

import com.LunaGlaze.rainbowcompound.Core.Class.ModElytraItem;
import com.LunaGlaze.rainbowcompound.LunaUtils;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.illusivesoulworks.caelus.api.CaelusApi;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CuriosModElytraItem extends ModElytraItem implements ICurioItem {

    public CuriosModElytraItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        if (slotContext.identifier().equals("back")) {
            Multimap<Holder<Attribute>, AttributeModifier> modifiers = LinkedHashMultimap.create();
            modifiers.put(CaelusApi.getInstance().getFallFlyingAttribute(), new AttributeModifier(ResourceLocation.fromNamespaceAndPath(LunaUtils.MOD_ID, "elytra"), 1, AttributeModifier.Operation.ADD_VALUE));
            return modifiers;
        }
        return ((ICurio) () -> ItemStack.EMPTY).getAttributeModifiers(slotContext, id);
    }
}
