package com.LunaGlaze.rainbowcompound.Linkage.elytraslot;

import com.LunaGlaze.rainbowcompound.Core.Date.KeyBoard.ElytraFlyKey;
import com.LunaGlaze.rainbowcompound.Core.Date.LunaConfig;
import com.LunaGlaze.rainbowcompound.LunaUtils;
import com.LunaGlaze.rainbowcompound.Projects.Items.Basic.ItemsItemRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.concurrent.atomic.AtomicReference;


//@EventBusSubscriber
public class CuriosDynamicElytra extends CuriosModElytraItem {
    private static final int defense = 5;
    private static final float toughness = 2;

    public CuriosDynamicElytra() {
        super(new Properties().fireResistant().durability(1152).rarity(Rarity.UNCOMMON)
                .attributes(ItemAttributeModifiers.builder()
                        .add(Attributes.ARMOR, new AttributeModifier(ResourceLocation.fromNamespaceAndPath(LunaUtils.MOD_ID, "armor_modifier"), defense, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.CHEST)
                        .add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(ResourceLocation.fromNamespaceAndPath(LunaUtils.MOD_ID, "armor_modifier"), toughness, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.CHEST)
                        .build())
        );
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack pToRepair, ItemStack pRepair) {
        return pRepair.is(ItemsItemRegistry.refinedradiancesheet.get());
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    /*
    @SubscribeEvent(priority = EventPriority.LOW)
    @OnlyIn(Dist.CLIENT)
    public static void onPlayerTickClient(PlayerTickEvent event) {
        Player player = event.getEntity();
        if(CuriosApi.getCuriosInventory(player).isEmpty()){ return; }
        Item item = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        ICuriosItemHandler curiosInventory = CuriosApi.getCuriosInventory(player).get();
        AtomicReference<Boolean> curioE = new AtomicReference<>(false);
        curiosInventory.getStacksHandler("back").ifPresent(slotInventory -> {
            int slotsnum = slotInventory.getSlots();
            for (int i=0 ; i<slotsnum ; i++){
                Item eqCurio = slotInventory.getStacks().getStackInSlot(i).getItem();
                if (eqCurio instanceof CuriosDynamicElytra){
                    curioE.set(true);
                }
            }
        });
        if(item instanceof CuriosDynamicElytra || curioE.get()) {
            if (player.isFallFlying() && ElytraFlyKey.ELYTRA_FLY_KEY.isPressed()) {
                Vec3 lookAngle = player.getLookAngle();
                Vec3 flyAngle = player.getDeltaMovement();
                double d = 0.1;
                double i = 1.5;
                double t = 0.4;
                double c = LunaConfig.ELYTRA_SPEED.get();
                player.setDeltaMovement(flyAngle.add(
                        (lookAngle.x * d + (lookAngle.x * i - flyAngle.x) * t) * c,
                        (lookAngle.y * d + (lookAngle.y * i - flyAngle.y) * t) * c,
                        (lookAngle.z * d + (lookAngle.z * i - flyAngle.z) * t) * c));
            }
        }
    }*/

    @Override
    public boolean elytraFlightTick(@NotNull ItemStack stack, net.minecraft.world.entity.LivingEntity entity, int flightTicks) {
        if(!entity.level().isClientSide) {
            int nextFlightTick = flightTicks + 1;
            if (nextFlightTick % 10 == 0) {
                if ((flightTicks) % 20 == 0 && ElytraFlyKey.ELYTRA_FLY_KEY.isPressed()) {
                    stack.hurtAndBreak(1, (ServerLevel) entity.level(), entity, i -> entity.onEquippedItemBroken(i, EquipmentSlot.CHEST));
                }
                entity.gameEvent(GameEvent.ELYTRA_GLIDE);
            }
        }
        return true;
    }
}
