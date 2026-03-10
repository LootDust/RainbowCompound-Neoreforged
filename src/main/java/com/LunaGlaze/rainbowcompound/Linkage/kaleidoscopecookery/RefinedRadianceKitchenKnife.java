package com.LunaGlaze.rainbowcompound.Linkage.kaleidoscopecookery;

import com.LunaGlaze.rainbowcompound.Core.Tiers.ToolTiers;
import com.github.ysbbbbbb.kaleidoscopecookery.item.KitchenKnifeItem;
import net.minecraft.world.item.Rarity;

public class RefinedRadianceKitchenKnife extends KitchenKnifeItem {
    public RefinedRadianceKitchenKnife() {
        super(ToolTiers.RefinedRadiance, new Properties().rarity(Rarity.UNCOMMON).attributes(createAttributes(ToolTiers.RefinedRadiance, 0.5f, -2f)));
    }
}
