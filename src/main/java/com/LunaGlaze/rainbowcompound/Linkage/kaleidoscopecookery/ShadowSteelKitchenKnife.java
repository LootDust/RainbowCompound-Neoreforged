package com.LunaGlaze.rainbowcompound.Linkage.kaleidoscopecookery;

import com.LunaGlaze.rainbowcompound.Core.Tiers.ToolTiers;
import com.github.ysbbbbbb.kaleidoscopecookery.item.KitchenKnifeItem;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ShadowSteelKitchenKnife extends KitchenKnifeItem {
    public ShadowSteelKitchenKnife() {
        super(ToolTiers.Shadowsteeltool, new Properties().rarity(Rarity.UNCOMMON).attributes(createAttributes(ToolTiers.Shadowsteeltool, 0.5f, -1.75f)));
    }

    // Shadow Steel Item Like
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

        float yMotion = (entity.fallDistance + 3) / 50f;
        entity.setDeltaMovement(0, yMotion, 0);
    }

    protected float getIdleParticleChance(ItemEntity entity) {
        return (float) (Mth.clamp(entity.getItem()
                .getCount() - 10, Mth.clamp(entity.getDeltaMovement().y * 20, 5, 20), 100) / 64f);
    }
}
