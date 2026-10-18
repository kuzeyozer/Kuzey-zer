package com.kuzey.eclipserealms.registry;

import com.kuzey.eclipserealms.EclipseRealms;
import com.kuzey.eclipserealms.item.EclipseArmorItem;
import com.kuzey.eclipserealms.item.EclipseGreatswordItem;
import com.kuzey.eclipserealms.item.EmberScytheItem;
import com.kuzey.eclipserealms.item.EmberStaffItem;
import com.kuzey.eclipserealms.item.FrostfangDaggerItem;
import com.kuzey.eclipserealms.item.StormbreakerItem;
import com.kuzey.eclipserealms.item.VoidStaffItem;
import com.kuzey.eclipserealms.item.VoidbladeItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.SwordItem;

import java.util.List;

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
					.attributes(SwordItem.createAttributes(ModTiers.VOID_CRYSTAL, 2, -1.4F))));
	public static final Item ECLIPSE_GREATSWORD = register("eclipse_greatsword",
			new EclipseGreatswordItem(ModTiers.ECLIPSE, new Item.Properties().rarity(Rarity.EPIC).fireResistant()
					.attributes(SwordItem.createAttributes(ModTiers.ECLIPSE, 9, -2.8F))));
	public static final Item STORMBREAKER = register("stormbreaker",
			new StormbreakerItem(ModTiers.ECLIPSE, new Item.Properties().rarity(Rarity.EPIC).fireResistant()
					.attributes(SwordItem.createAttributes(ModTiers.ECLIPSE, 8, -3.0F))));
	public static final Item EMBER_SCYTHE = register("ember_scythe",
			new EmberScytheItem(ModTiers.ECLIPSE, new Item.Properties().rarity(Rarity.EPIC).fireResistant()
					.attributes(SwordItem.createAttributes(ModTiers.ECLIPSE, 7, -2.6F))));
	public static final Item VOID_STAFF = register("void_staff",
			new VoidStaffItem(new Item.Properties().rarity(Rarity.EPIC).durability(800)));
	public static final Item EMBER_STAFF = register("ember_staff",
			new EmberStaffItem(new Item.Properties().rarity(Rarity.RARE).durability(700).fireResistant()));

	// Armor
	private static final List<Holder<MobEffect>> VOID_SET = List.of(MobEffects.NIGHT_VISION, MobEffects.MOVEMENT_SPEED);
	private static final List<Holder<MobEffect>> ECLIPSE_SET = List.of(MobEffects.NIGHT_VISION, MobEffects.DAMAGE_BOOST,
			MobEffects.DAMAGE_RESISTANCE, MobEffects.FIRE_RESISTANCE);
	public static final Item VOID_CRYSTAL_HELMET = armor("void_crystal_helmet", ModArmorMaterials.VOID_CRYSTAL, ArmorItem.Type.HELMET, 35, VOID_SET);
	public static final Item VOID_CRYSTAL_CHESTPLATE = armor("void_crystal_chestplate", ModArmorMaterials.VOID_CRYSTAL, ArmorItem.Type.CHESTPLATE, 35, VOID_SET);
	public static final Item VOID_CRYSTAL_LEGGINGS = armor("void_crystal_leggings", ModArmorMaterials.VOID_CRYSTAL, ArmorItem.Type.LEGGINGS, 35, VOID_SET);
	public static final Item VOID_CRYSTAL_BOOTS = armor("void_crystal_boots", ModArmorMaterials.VOID_CRYSTAL, ArmorItem.Type.BOOTS, 35, VOID_SET);
	public static final Item ECLIPSE_HELMET = armor("eclipse_helmet", ModArmorMaterials.ECLIPSE, ArmorItem.Type.HELMET, 45, ECLIPSE_SET);
	public static final Item ECLIPSE_CHESTPLATE = armor("eclipse_chestplate", ModArmorMaterials.ECLIPSE, ArmorItem.Type.CHESTPLATE, 45, ECLIPSE_SET);
	public static final Item ECLIPSE_LEGGINGS = armor("eclipse_leggings", ModArmorMaterials.ECLIPSE, ArmorItem.Type.LEGGINGS, 45, ECLIPSE_SET);
	public static final Item ECLIPSE_BOOTS = armor("eclipse_boots", ModArmorMaterials.ECLIPSE, ArmorItem.Type.BOOTS, 45, ECLIPSE_SET);

	// Spawn eggs
	public static final Item UMBRAL_STALKER_SPAWN_EGG = register("umbral_stalker_spawn_egg",
			new SpawnEggItem(ModEntities.UMBRAL_STALKER, 0x1D1529, 0xD04CFF, new Item.Properties()));
	public static final Item EMBER_FIEND_SPAWN_EGG = register("ember_fiend_spawn_egg",
			new SpawnEggItem(ModEntities.EMBER_FIEND, 0x3A1A14, 0xFF7A1A, new Item.Properties()));
	public static final Item VOID_WISP_SPAWN_EGG = register("void_wisp_spawn_egg",
			new SpawnEggItem(ModEntities.VOID_WISP, 0x2A2238, 0x8FF0FF, new Item.Properties()));

	private static Item armor(String name, Holder<ArmorMaterial> material, ArmorItem.Type type, int durabilityFactor,
			List<Holder<MobEffect>> setEffects) {
		Item.Properties properties = new Item.Properties().durability(type.getDurability(durabilityFactor))
				.rarity(material == ModArmorMaterials.ECLIPSE ? Rarity.EPIC : Rarity.RARE);
		if (material == ModArmorMaterials.ECLIPSE) {
			properties.fireResistant();
		}
		String setKey = material == ModArmorMaterials.ECLIPSE ? "tooltip.eclipserealms.eclipse_set" : "tooltip.eclipserealms.void_crystal_set";
		return register(name, new EclipseArmorItem(material, type, properties, setKey, setEffects));
	}

	private static Item register(String name, Item item) {
		return Registry.register(BuiltInRegistries.ITEM, EclipseRealms.id(name), item);
	}

	public static void init() {
	}

	private ModItems() {
	}
}
