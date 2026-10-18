package com.kuzey.eclipserealms.registry;

import com.kuzey.eclipserealms.EclipseRealms;
import com.kuzey.eclipserealms.item.EclipseGreatswordItem;
import com.kuzey.eclipserealms.item.EmberScytheItem;
import com.kuzey.eclipserealms.item.EmberStaffItem;
import com.kuzey.eclipserealms.item.FrostfangDaggerItem;
import com.kuzey.eclipserealms.item.StormbreakerItem;
import com.kuzey.eclipserealms.item.VoidStaffItem;
import com.kuzey.eclipserealms.item.VoidbladeItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;

public final class ModItems {
	// Materials
	public static final Item VOID_CRYSTAL_SHARD = register("void_crystal_shard",
			new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
	public static final Item ECLIPSE_INGOT = register("eclipse_ingot",
			new Item(new Item.Properties().rarity(Rarity.RARE).fireResistant()));

	// Weapons
	public static final Item VOIDBLADE = register("voidblade",
			new VoidbladeItem(ModTiers.VOID_CRYSTAL, new Item.Properties().rarity(Rarity.RARE)
					.attributes(SwordItem.createAttributes(ModTiers.VOID_CRYSTAL, 3, -2.4F))));
	public static final Item FROSTFANG_DAGGER = register("frostfang_dagger",
			new FrostfangDaggerItem(ModTiers.VOID_CRYSTAL, new Item.Properties().rarity(Rarity.RARE)
					.attributes(SwordItem.createAttributes(ModTiers.VOID_CRYSTAL, 1, -1.6F))));
	public static final Item ECLIPSE_GREATSWORD = register("eclipse_greatsword",
			new EclipseGreatswordItem(ModTiers.ECLIPSE, new Item.Properties().rarity(Rarity.EPIC).fireResistant()
					.attributes(SwordItem.createAttributes(ModTiers.ECLIPSE, 7, -3.0F))));
	public static final Item STORMBREAKER = register("stormbreaker",
			new StormbreakerItem(ModTiers.ECLIPSE, new Item.Properties().rarity(Rarity.EPIC).fireResistant()
					.attributes(SwordItem.createAttributes(ModTiers.ECLIPSE, 6, -3.2F))));
	public static final Item EMBER_SCYTHE = register("ember_scythe",
			new EmberScytheItem(ModTiers.ECLIPSE, new Item.Properties().rarity(Rarity.EPIC).fireResistant()
					.attributes(SwordItem.createAttributes(ModTiers.ECLIPSE, 5, -2.8F))));
	public static final Item VOID_STAFF = register("void_staff",
			new VoidStaffItem(new Item.Properties().rarity(Rarity.EPIC).durability(400)));
	public static final Item EMBER_STAFF = register("ember_staff",
			new EmberStaffItem(new Item.Properties().rarity(Rarity.RARE).durability(350).fireResistant()));

	private static Item register(String name, Item item) {
		return Registry.register(BuiltInRegistries.ITEM, EclipseRealms.id(name), item);
	}

	public static void init() {
	}

	private ModItems() {
	}
}
