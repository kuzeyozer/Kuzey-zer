package com.kuzey.eclipserealms.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;

final class TooltipHelper {
	/** Adds the "passive" and "ability" lines defined in the lang file for an item. */
	static void addWeaponLines(String descriptionId, List<Component> tooltip) {
		tooltip.add(Component.translatable(descriptionId + ".passive").withStyle(ChatFormatting.DARK_PURPLE));
		tooltip.add(Component.translatable(descriptionId + ".ability").withStyle(ChatFormatting.GOLD));
	}

	private TooltipHelper() {
	}
}
