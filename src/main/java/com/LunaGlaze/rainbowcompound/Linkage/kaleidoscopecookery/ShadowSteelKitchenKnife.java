package com.LunaGlaze.rainbowcompound.Linkage.kaleidoscopecookery;

import com.LunaGlaze.rainbowcompound.Core.Tiers.ToolTiers;
import com.github.ysbbbbbb.kaleidoscopecookery.item.KitchenKnifeItem;
import net.minecraft.world.item.Rarity;

public class ShadowSteelKitchenKnife extends KitchenKnifeItem {
    public ShadowSteelKitchenKnife() {
        super(ToolTiers.Shadowsteeltool, new Properties().rarity(Rarity.UNCOMMON).attributes(createAttributes(ToolTiers.Shadowsteeltool, 0.5f, -1.75f)));
    }
}
