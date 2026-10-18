package com.kuzey.eclipserealms.registry;

import com.kuzey.eclipserealms.EclipseRealms;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public final class ModArmorMaterials {
	public static final Holder<ArmorMaterial> VOID_CRYSTAL = register("void_crystal", 3, 7, 6, 3, 18, 2.0F, 0.0F,
			() -> ModItems.VOID_CRYSTAL_SHARD);
	public static final Holder<ArmorMaterial> ECLIPSE = register("eclipse", 4, 9, 7, 4, 22, 4.0F, 0.15F,
			() -> ModItems.ECLIPSE_INGOT);

	private static Holder<ArmorMaterial> register(String name, int helmet, int chestplate, int leggings, int boots,
			int enchantability, float toughness, float knockbackResistance, Supplier<Item> repairItem) {
		EnumMap<ArmorItem.Type, Integer> defense = new EnumMap<>(ArmorItem.Type.class);
		defense.put(ArmorItem.Type.HELMET, helmet);
		defense.put(ArmorItem.Type.CHESTPLATE, chestplate);
		defense.put(ArmorItem.Type.LEGGINGS, leggings);
		defense.put(ArmorItem.Type.BOOTS, boots);
		defense.put(ArmorItem.Type.BODY, chestplate);
		ArmorMaterial material = new ArmorMaterial(defense, enchantability, SoundEvents.ARMOR_EQUIP_NETHERITE,
				() -> Ingredient.of(repairItem.get()), List.of(new ArmorMaterial.Layer(EclipseRealms.id(name))),
				toughness, knockbackResistance);
		return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, EclipseRealms.id(name), material);
	}

	public static void init() {
	}

	private ModArmorMaterials() {
	}
}
