package com.kuzey.eclipserealms.registry;

import com.kuzey.eclipserealms.EclipseRealms;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public final class ModCreativeTab {
	public static final ResourceKey<CreativeModeTab> MAIN =
			ResourceKey.create(Registries.CREATIVE_MODE_TAB, EclipseRealms.id("main"));

	public static void init() {
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MAIN, FabricItemGroup.builder()
				.title(Component.translatable("itemGroup.eclipserealms.main"))
				.icon(() -> new ItemStack(ModItems.ECLIPSE_GREATSWORD))
				.displayItems((parameters, output) -> {
					output.accept(ModBlocks.ECLIPSE_PORTAL);
					output.accept(ModItems.VOIDBLADE);
					output.accept(ModItems.FROSTFANG_DAGGER);
					output.accept(ModItems.ECLIPSE_GREATSWORD);
					output.accept(ModItems.STORMBREAKER);
					output.accept(ModItems.EMBER_SCYTHE);
					output.accept(ModItems.VOID_STAFF);
					output.accept(ModItems.EMBER_STAFF);
					output.accept(ModItems.VOID_CRYSTAL_SHARD);
					output.accept(ModItems.ECLIPSE_INGOT);
					output.accept(ModBlocks.UMBRAL_STONE);
					output.accept(ModBlocks.UMBRAL_BRICKS);
					output.accept(ModBlocks.DUSKMOSS);
					output.accept(ModBlocks.ASH_BLOCK);
					output.accept(ModBlocks.STARLIGHT_LOG);
					output.accept(ModBlocks.STARLIGHT_PLANKS);
					output.accept(ModBlocks.STARLIGHT_LEAVES);
					output.accept(ModBlocks.VOID_CRYSTAL_ORE);
					output.accept(ModBlocks.VOID_CRYSTAL_BLOCK);
					output.accept(ModBlocks.LUMINITE_CLUSTER);
					output.accept(ModBlocks.ABYSSAL_SAND);
					output.accept(ModBlocks.ABYSSAL_BRICKS);
					output.accept(ModBlocks.ABYSSAL_CORAL_BLOCK);
					output.accept(ModBlocks.ABYSSAL_ANEMONE);
					output.accept(ModItems.UMBRAL_STALKER_SPAWN_EGG);
					output.accept(ModItems.EMBER_FIEND_SPAWN_EGG);
					output.accept(ModItems.VOID_WISP_SPAWN_EGG);
				})
				.build());
	}

	private ModCreativeTab() {
	}
}
