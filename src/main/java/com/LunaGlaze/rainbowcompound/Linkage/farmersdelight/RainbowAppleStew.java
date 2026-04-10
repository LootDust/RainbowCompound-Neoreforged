package com.LunaGlaze.rainbowcompound.Linkage.farmersdelight;

import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class RainbowAppleStew extends ConsumableItem {
    private static final FoodProperties food = (new FoodProperties.Builder())
            .saturationModifier(1.8F)
            .nutrition(10)
            .effect(() ->new MobEffectInstance(MobEffects.REGENERATION, 200, 1), 1.0F)
            .effect(() ->new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3600, 0), 1.0F)
            .effect(() ->new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 4800, 0), 1.0F)
            .effect(() ->new MobEffectInstance(MobEffects.ABSORPTION, 3600, 2), 1.0F)
            .effect(() ->new MobEffectInstance(MobEffects.HEALTH_BOOST, 4800, 0), 1.0F)
            .effect(() ->new MobEffectInstance(ModEffects.COMFORT,3600,0),1.0F)
            .alwaysEdible()
            .build();
    public RainbowAppleStew() {
        super(new Properties().food(food).rarity(Rarity.EPIC),true);
    }


    // Rainbow Item Like
    @Override
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

    protected void onCreated(ItemEntity entity, CompoundTag persistentData) {
        entity.lifespan = 6000;
        persistentData.remove("JustCreated");

        // just a flag to tell the client to play an effect
        entity.setSilent(true);

        float yMotion = Math.max((entity.fallDistance + 3) / 50f, .25f);
        entity.setDeltaMovement(0, yMotion, 0);
    }

    protected float getIdleParticleChance(ItemEntity entity) {
        return Mth.clamp(entity.getItem()
                .getCount() - 10, (float) Math.min(5, Mth.clamp(entity.getDeltaMovement().y * 20, 5, 20)), 100) / 64f;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) { return new ItemStack(Items.BOWL); }
}
