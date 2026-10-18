package com.kuzey.eclipserealms.menu;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

/**
 * One Eclipse Stand recipe: a base item, a gem and a material (each with a required count) forge into a result.
 */
public record StandRecipe(Ingredient base, Ingredient gem, int gemCount, Ingredient material, int materialCount,
		Supplier<ItemStack> result) {

	public boolean matches(ItemStack baseStack, ItemStack gemStack, ItemStack materialStack) {
		return base.test(baseStack)
				&& gem.test(gemStack) && gemStack.getCount() >= gemCount
				&& material.test(materialStack) && materialStack.getCount() >= materialCount;
	}
}
