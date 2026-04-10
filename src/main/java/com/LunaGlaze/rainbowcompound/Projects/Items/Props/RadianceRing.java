package com.LunaGlaze.rainbowcompound.Projects.Items.Props;

import com.LunaGlaze.rainbowcompound.LunaUtils;
import com.LunaGlaze.rainbowcompound.Projects.Effect.EffectRegistry;
import com.LunaGlaze.rainbowcompound.Projects.Items.Armors.ArmorsItemRegistry;
import com.google.common.collect.Multimap;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class RadianceRing extends Item implements ICurioItem {
    public RadianceRing() {
        super(new Properties().rarity(Rarity.UNCOMMON).stacksTo(1));
    }

    // Refined Radiance Item Like
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        Level world = entity.level();
        Vec3 pos = entity.position();
        CompoundTag persistentData = entity.getPersistentData();

        if (world.isClientSide) {
            if (world.random.nextFloat() < getIdleParticleChance(entity)) {
                Vec3 ppos = VecHelper.offsetRandomly(pos, world.random, .5f);
                world.addParticle(ParticleTypes.END_ROD, ppos.x, pos.y, ppos.z, 0, -.1f, 0);
            }

            if (entity.isSilent() && !persistentData.getBoolean("PlayEffects")) {
                Vec3 basemotion = new Vec3(0, 1, 0);
                world.addParticle(ParticleTypes.FLASH, pos.x, pos.y, pos.z, 0, 0, 0);
                for (int i = 0; i < 20; i++) {
                    Vec3 motion = VecHelper.offsetRandomly(basemotion, world.random, 1);
                    world.addParticle(ParticleTypes.WITCH, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                    world.addParticle(ParticleTypes.END_ROD, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                }
                persistentData.putBoolean("PlayEffects", true);
            }

            return false;
        }

        entity.setNoGravity(true);

        if (!persistentData.contains("JustCreated"))
            return false;
        onCreated(entity, persistentData);
        return false;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    protected float getIdleParticleChance(ItemEntity entity) {
        return Mth.clamp(entity.getItem()
                .getCount() - 10, 5, 100) / 64f;
    }

    protected void onCreated(ItemEntity entity, CompoundTag persistentData) {
        entity.lifespan = 6000;
        persistentData.remove("JustCreated");

        // just a flag to tell the client to play an effect
        entity.setSilent(true);

        entity.setDeltaMovement(entity.getDeltaMovement()
                .add(0, .25f, 0));
    }


    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> result = ICurioItem.super.getAttributeModifiers(slotContext, id, stack);
        result.put(Attributes.ARMOR_TOUGHNESS,
                new AttributeModifier(ResourceLocation.fromNamespaceAndPath(LunaUtils.MOD_ID, "power_glove_armor_toughness"),
                        0.25, AttributeModifier.Operation.ADD_VALUE));
        return result;
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return slotContext.identifier().equals("ring");
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        int i = RainbowKit(slotContext);
        if(i>0 && livingEntity.level().isDay()){
            livingEntity.addEffect(new MobEffectInstance(EffectRegistry.resonance_rainbow,20,i-1));
        }else removeAttributeModifiers(livingEntity);
    }

    public int RainbowKit(SlotContext slotContext) {
        int i = 0;
        LivingEntity livingEntity = slotContext.entity();
        Item head = livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item chest = livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item legs = livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item feet = livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem();
        if(head == ArmorsItemRegistry.rainbowhelmet.get()){
            i=i+1;
        }
        if(chest == ArmorsItemRegistry.rainbowchestplate.get()){
            i=i+1;
        }
        if(legs == ArmorsItemRegistry.rainbowleggings.get()){
            i=i+1;
        }
        if(feet == ArmorsItemRegistry.rainbowboots.get()){
            i=i+1;
        }
        return i;
    }

    public void removeAttributeModifiers(LivingEntity pLivingEntity) {
        if (pLivingEntity.getHealth() > pLivingEntity.getMaxHealth()) {
            pLivingEntity.setHealth(pLivingEntity.getMaxHealth());
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced){
        tooltip.add(Component.translatable(LunaUtils.MOD_ID + ".tooltip.radiancering", new Object[0]).withStyle(ChatFormatting.BLUE));
    }
}
