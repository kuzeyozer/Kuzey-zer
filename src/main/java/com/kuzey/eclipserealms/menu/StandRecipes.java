package com.kuzey.eclipserealms.menu;

import com.kuzey.eclipserealms.registry.ModBlocks;
import com.kuzey.eclipserealms.registry.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.Optional;

/** Everything the Eclipse Stand can forge. Slot order is always: base, gem, material. */
public final class StandRecipes {
	private static List<StandRecipe> recipes;

	public static Optional<StandRecipe> find(ItemStack base, ItemStack gem, ItemStack material) {
		for (StandRecipe recipe : all()) {
			if (recipe.matches(base, gem, material)) {
				return Optional.of(recipe);
			}
		}
		return Optional.empty();
	}

	public static List<StandRecipe> all() {
		if (recipes == null) {
			ItemLike shard = ModItems.VOID_CRYSTAL_SHARD;
			ItemLike luminite = ModBlocks.LUMINITE_CLUSTER;
			ItemLike ingot = ModItems.ECLIPSE_INGOT;
			recipes = List.of(
					// Weapons
					recipe(Items.IRON_SWORD, shard, 4, luminite, 1, ModItems.VOIDBLADE),
					recipe(Items.IRON_SWORD, shard, 3, Items.BLUE_ICE, 2, ModItems.FROSTFANG_DAGGER),
					recipe(Items.DIAMOND_SWORD, shard, 4, ingot, 3, ModItems.ECLIPSE_GREATSWORD),
					recipe(Items.DIAMOND_AXE, luminite, 4, ingot, 3, ModItems.STORMBREAKER),
					recipe(Items.DIAMOND_HOE, luminite, 2, ingot, 3, ModItems.EMBER_SCYTHE),
					recipe(Items.BLAZE_ROD, shard, 6, ingot, 1, ModItems.VOID_STAFF),
					recipe(Items.BLAZE_ROD, luminite, 2, Items.FIRE_CHARGE, 4, ModItems.EMBER_STAFF),
					// Void Crystal armor: iron armor + shards + luminite
					recipe(Items.IRON_HELMET, shard, 5, luminite, 1, ModItems.VOID_CRYSTAL_HELMET),
					recipe(Items.IRON_CHESTPLATE, shard, 8, luminite, 1, ModItems.VOID_CRYSTAL_CHESTPLATE),
					recipe(Items.IRON_LEGGINGS, shard, 7, luminite, 1, ModItems.VOID_CRYSTAL_LEGGINGS),
					recipe(Items.IRON_BOOTS, shard, 4, luminite, 1, ModItems.VOID_CRYSTAL_BOOTS),
					// Eclipse armor: upgrade Void Crystal armor with luminite + eclipse ingots
					recipe(ModItems.VOID_CRYSTAL_HELMET, luminite, 2, ingot, 3, ModItems.ECLIPSE_HELMET),
					recipe(ModItems.VOID_CRYSTAL_CHESTPLATE, luminite, 2, ingot, 5, ModItems.ECLIPSE_CHESTPLATE),
					recipe(ModItems.VOID_CRYSTAL_LEGGINGS, luminite, 2, ingot, 4, ModItems.ECLIPSE_LEGGINGS),
					recipe(ModItems.VOID_CRYSTAL_BOOTS, luminite, 2, ingot, 2, ModItems.ECLIPSE_BOOTS));
		}
		return recipes;
	}

	private static StandRecipe recipe(ItemLike base, ItemLike gem, int gemCount, ItemLike material, int materialCount,
			Item result) {
		return new StandRecipe(Ingredient.of(base), Ingredient.of(gem), gemCount, Ingredient.of(material), materialCount,
				() -> new ItemStack(result));
	}

	private StandRecipes() {
	}
}
