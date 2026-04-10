package com.LunaGlaze.rainbowcompound.Linkage.elytraslot;

import com.LunaGlaze.rainbowcompound.Core.Date.KeyBoard.ElytraFlyKey;
import com.LunaGlaze.rainbowcompound.Core.Date.LunaConfig;
import com.LunaGlaze.rainbowcompound.LunaUtils;
import com.LunaGlaze.rainbowcompound.Projects.Items.Basic.ItemsItemRegistry;
import com.illusivesoulworks.caelus.api.CaelusApi;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class CuriosRainbowElytra extends CuriosModElytraItem {

    private final ItemAttributeModifiers defaultModifiers;

    public CuriosRainbowElytra() {
        super(new Properties().fireResistant().durability(1632).rarity(Rarity.UNCOMMON));
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
        this.defaultModifiers = ItemAttributeModifiers.builder()
                .add(Attributes.ARMOR, new AttributeModifier(ResourceLocation.fromNamespaceAndPath(LunaUtils.MOD_ID, "base_arm"), this.getDefense(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.CHEST)
                .add(Attributes.ARMOR, new AttributeModifier(ResourceLocation.fromNamespaceAndPath(LunaUtils.MOD_ID, "base_at"), this.getToughness(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.CHEST)
                .build();
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return pRepair.is(ItemsItemRegistry.rainbowcompound.get());
    }
    private int getDefense(){
        return 6;
    }

    private float getToughness(){
        return 3;
    }

    @Override
    public int getEnchantmentValue() {
        return 18;
    }

    @Override
    public @NotNull ItemAttributeModifiers getDefaultAttributeModifiers() {
        return this.defaultModifiers;
    }

    /*
    @Override
    public boolean elytraFlightTick(@NotNull ItemStack stack, net.minecraft.world.entity.LivingEntity entity, int flightTicks) {
        if(!entity.level().isClientSide) {
            int nextFlightTick = flightTicks + 1;
            if (nextFlightTick % 10 == 0) {
                if ((flightTicks) % 25 == 0 && ElytraFlyKey.ELYTRA_FLY_KEY.isPressed()) {
                    stack.hurtAndBreak(1, (ServerLevel) entity.level(), entity, (i) -> entity.onEquippedItemBroken(i, EquipmentSlot.CHEST));
                }
                entity.gameEvent(GameEvent.ELYTRA_GLIDE);
            }
        }
        return true;
    }
    */
}