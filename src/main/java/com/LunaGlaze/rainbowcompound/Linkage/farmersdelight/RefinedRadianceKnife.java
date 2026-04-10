package com.LunaGlaze.rainbowcompound.Linkage.farmersdelight;

import com.LunaGlaze.rainbowcompound.Core.Tiers.ToolTiers;
import com.LunaGlaze.rainbowcompound.LunaUtils;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.item.KnifeItem;

import javax.annotation.Nullable;
import java.util.List;


public class RefinedRadianceKnife extends KnifeItem {
    public RefinedRadianceKnife() {
        super(ToolTiers.RefinedRadiance, new Properties().rarity(Rarity.UNCOMMON).attributes(createAttributes(ToolTiers.RefinedRadiance, 0.5f, -2f)));
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


    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced){
        tooltip.add(Component.translatable(LunaUtils.MOD_ID + ".tooltip.refinedradiancetool", new Object[0]).withStyle(ChatFormatting.AQUA));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, @NotNull LivingEntity pAttacker) {
        stack.hurtAndBreak(1, (ServerLevel) pAttacker.level(), pAttacker, (i) -> {
            pAttacker.onEquippedItemBroken(i, EquipmentSlot.MAINHAND);
        });
        target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,4*20,0));
        return true;
    }
}
