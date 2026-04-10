package com.LunaGlaze.rainbowcompound.Projects.Items.Props;

import com.LunaGlaze.rainbowcompound.LunaUtils;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.apache.commons.lang3.RandomUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;


public class HomeMagicMirror extends Item {
    public HomeMagicMirror() {
        super(new Properties().rarity(Rarity.UNCOMMON)
                .stacksTo( 1 ).durability(16) );
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

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand pUsedHand) {
        ItemStack itemStack = player.getItemInHand(pUsedHand);
        if(itemStack.getDamageValue() < 16) {
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, itemStack);

                var spawnPoint = serverPlayer.getRespawnPosition();
                if (spawnPoint == null || level.isClientSide) {
                    return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemStack);
                }
                    serverPlayer.teleportTo(
                            Objects.requireNonNull(((ServerLevel) level).getServer().getLevel(serverPlayer.getRespawnDimension())),
                            spawnPoint.getX(), spawnPoint.getY(), spawnPoint.getZ(), serverPlayer.getYRot(), serverPlayer.getVoicePitch()
                    );
            }

            player.awardStat(Stats.ITEM_USED.get(this));

            if (!player.isCreative()) {
                itemStack.getDamageValue();
                itemStack.setDamageValue(itemStack.getDamageValue() + 1);
            }

            player.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.5F, 2F / (RandomUtils.nextFloat() *0.4F + 0.2F));
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemStack);
        }

        return new InteractionResultHolder<>(InteractionResult.PASS, itemStack);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced){
        tooltip.add(Component.translatable(LunaUtils.MOD_ID + ".tooltip.homemagicmirror", new Object[0]).withStyle(ChatFormatting.GRAY));
    }

}
