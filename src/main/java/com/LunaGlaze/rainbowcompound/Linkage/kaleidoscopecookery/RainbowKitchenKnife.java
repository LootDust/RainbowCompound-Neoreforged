package com.LunaGlaze.rainbowcompound.Linkage.kaleidoscopecookery;

import com.LunaGlaze.rainbowcompound.Core.Tiers.ToolTiers;
import com.github.ysbbbbbb.kaleidoscopecookery.item.KitchenKnifeItem;
import net.minecraft.world.item.Rarity;

public class RainbowKitchenKnife extends KitchenKnifeItem {
    public RainbowKitchenKnife() {
        super(ToolTiers.RAINBOW, new Properties().fireResistant().rarity(Rarity.UNCOMMON).attributes(createAttributes(ToolTiers.RAINBOW, 0.5f, -2f)));
    }
}
