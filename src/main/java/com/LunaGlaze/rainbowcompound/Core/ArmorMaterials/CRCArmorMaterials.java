package com.LunaGlaze.rainbowcompound.Core.ArmorMaterials;

import com.LunaGlaze.rainbowcompound.LunaUtils;
import com.LunaGlaze.rainbowcompound.Projects.Items.Basic.ItemsItemRegistry;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;

public enum CRCArmorMaterials {
    // 25.09.27 By ZNECrode 盔甲材料从enum变成record了，不再能够继承，只能这样写了，或者你把这个get方法丢进CRCArmorItem也可以
    //                      至于盔甲耐久基数，改到了注册时候在Properties里面添加了，你可以在ArmorsItemRegistry里看见
    /*Obsidianite("obsidianite", 38, new int[]{3, 6, 8, 3}, 15, SoundEvents.ARMOR_EQUIP_NETHERITE.value(), 2.0F, 0.15F, () -> {
        return Ingredient.of(Items.OBSIDIAN);
    }),
    RAINBOW("rainbow", 48, new int[]{4 , 7 , 9 , 4 },
            25, SoundEvents.ARMOR_EQUIP_NETHERITE.value(), 4.0F , 0.1F, () -> {
        return Ingredient.of(ItemsItemRegistry.rainbowcompound.get());
    });*/

    Obsidianite, Rainbow;

    public ArmorMaterial get() {
        return switch (this) {
            case Obsidianite -> new ArmorMaterial(
                    Util.make(new EnumMap<>(Type.class), map -> {
                        map.put(Type.BOOTS, 3);
                        map.put(Type.LEGGINGS, 6);
                        map.put(Type.CHESTPLATE, 8);
                        map.put(Type.HELMET, 3);
                    }),
                    15,
                    SoundEvents.ARMOR_EQUIP_NETHERITE,
                    () -> Ingredient.of(Items.OBSIDIAN),
                    List.of(
                            new ArmorMaterial.Layer(
                                    ResourceLocation.fromNamespaceAndPath(LunaUtils.MOD_ID, "obsidianite")
                            )
                    ),
                    2.0f,
                    0.15f
            );
            case Rainbow -> new ArmorMaterial(
                    Util.make(new EnumMap<>(Type.class), map -> {
                        map.put(Type.BOOTS, 4);
                        map.put(Type.LEGGINGS, 7);
                        map.put(Type.CHESTPLATE, 9);
                        map.put(Type.HELMET, 4);
                    }),
                25,
                    SoundEvents.ARMOR_EQUIP_NETHERITE,
                    () -> Ingredient.of(ItemsItemRegistry.rainbowcompound.get()),
                    List.of(
                            new ArmorMaterial.Layer(
                                    ResourceLocation.fromNamespaceAndPath(LunaUtils.MOD_ID, "rainbow")
                            )
                    ),
                    4.0f,
                    1.0f
            );
        };
    }

    /*private static final int[] MAX_DAMAGE_ARRAY = new int[] { 13, 15, 16, 11 };
    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairMaterial;

    private CRCArmorMaterials(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability,
                              SoundEvent soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterial) {
        this.name = name;
        this.maxDamageFactor = maxDamageFactor;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairMaterial = Suppliers.memoize(repairMaterial::get);
    }

    @Override
    public int getDurabilityForType(Type type) {
        return MAX_DAMAGE_ARRAY[type.getSlot().getIndex()] * this.maxDamageFactor;
        ArmorMaterial
    }

    @Override
    public int getDefenseForType(Type type) {
        return this.damageReductionAmountArray[type.getSlot().getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.soundEvent;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }*/
}
