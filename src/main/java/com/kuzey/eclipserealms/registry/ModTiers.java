package com.kuzey.eclipserealms.registry;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public enum ModTiers implements Tier {
	VOID_CRYSTAL(2400, 9.0F, 6.0F, 18, () -> ModItems.VOID_CRYSTAL_SHARD),
	ECLIPSE(3600, 10.0F, 8.0F, 22, () -> ModItems.ECLIPSE_INGOT);

	private final int uses;
	private final float speed;
	private final float damage;
	private final int enchantmentValue;
	private final Supplier<Item> repairItem;

	ModTiers(int uses, float speed, float damage, int enchantmentValue, Supplier<Item> repairItem) {
		this.uses = uses;
		this.speed = speed;
		this.damage = damage;
		this.enchantmentValue = enchantmentValue;
		this.repairItem = repairItem;
	}

	@Override
	public int getUses() {
		return uses;
	}

	@Override
	public float getSpeed() {
		return speed;
	}

	@Override
	public float getAttackDamageBonus() {
		return damage;
	}

	@Override
	public TagKey<Block> getIncorrectBlocksForDrops() {
		return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
	}

	@Override
	public int getEnchantmentValue() {
		return enchantmentValue;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.of(repairItem.get());
	}
}
