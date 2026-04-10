package com.LunaGlaze.rainbowcompound.Core.Date;

import net.neoforged.neoforge.common.ModConfigSpec;

public class LunaConfig {
    public static ModConfigSpec COMMON_CONFIG;

    public static ModConfigSpec.IntValue OBSIDIANITEBOW_DURABILITY;

    public static ModConfigSpec.DoubleValue ELYTRA_SPEED;
    public static ModConfigSpec.IntValue OBSIDIANITEELYTRA_DURABILITY;
    public static ModConfigSpec.DoubleValue OBSIDIANITEELYTRA_ARMOR;
    public static ModConfigSpec.DoubleValue OBSIDIANITEELYTRA_ARMOR_TOUGHNESS;


    static {
        ModConfigSpec.Builder confingbuilder = new ModConfigSpec.Builder();
        confingbuilder.push("RainbowCompound");

        confingbuilder.comment("Tools and weapons settings").push("tools_and_weapons");
        OBSIDIANITEBOW_DURABILITY = confingbuilder.comment("Durability of Obsidianite Bow")
                        .defineInRange("obsidianite_bow_durability", 2048, 1, Integer.MAX_VALUE);
        confingbuilder.pop();

        confingbuilder.comment("\n");

        confingbuilder.comment("Armors and elytra settings").push("armors_and_elytras");
        ELYTRA_SPEED = confingbuilder.comment("Speed multiplier for Dynamic Elytra")
                .defineInRange("dynamic_elytra_speed", 1.0d, 0.0d, Double.MAX_VALUE);
        OBSIDIANITEELYTRA_DURABILITY = confingbuilder.comment("Durability of Obsidianite Elytra")
                .defineInRange("obsidianite_elytra_durability", 864, 1, Integer.MAX_VALUE);
        OBSIDIANITEELYTRA_ARMOR = confingbuilder.comment("Armor of Obsidianite Elytra")
                .defineInRange("obsidianite_elytra_armor", 3.0d, 0.0d, Double.MAX_VALUE);
        OBSIDIANITEELYTRA_ARMOR_TOUGHNESS = confingbuilder.comment("Armor toughness of Obsidianite Elytra")
                .defineInRange("obsidianite_elytra_armor_toughness", 1.0d, 0.0d, Double.MAX_VALUE);
        confingbuilder.pop();

        confingbuilder.pop();
        COMMON_CONFIG = confingbuilder.build();
    }
}
