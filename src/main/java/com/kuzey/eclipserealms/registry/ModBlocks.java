package com.kuzey.eclipserealms.registry;

import com.kuzey.eclipserealms.EclipseRealms;
import com.kuzey.eclipserealms.block.EclipsePortalBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public final class ModBlocks {
	public static final Block UMBRAL_STONE = register("umbral_stone",
			new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).strength(1.5F, 6.0F)
					.requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
	public static final Block UMBRAL_BRICKS = register("umbral_bricks",
			new Block(BlockBehaviour.Properties.ofFullCopy(UMBRAL_STONE).strength(2.0F, 6.0F)
					.sound(SoundType.DEEPSLATE_BRICKS)));
	public static final Block DUSKMOSS = register("duskmoss",
			new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).strength(0.6F)
					.sound(SoundType.MOSS)));
	public static final Block ASH_BLOCK = register("ash_block",
			new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(0.5F)
					.sound(SoundType.SAND)));
	public static final Block STARLIGHT_LOG = register("starlight_log",
			new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_LOG)
					.mapColor(MapColor.COLOR_BLUE)));
	public static final Block STARLIGHT_PLANKS = register("starlight_planks",
			new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_PLANKS)
					.mapColor(MapColor.COLOR_BLUE)));
	public static final Block STARLIGHT_LEAVES = register("starlight_leaves",
			new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
					.mapColor(MapColor.COLOR_LIGHT_BLUE).lightLevel(state -> 6)));
	public static final Block VOID_CRYSTAL_ORE = register("void_crystal_ore",
			new DropExperienceBlock(UniformInt.of(3, 7), BlockBehaviour.Properties.ofFullCopy(UMBRAL_STONE)
					.strength(3.5F, 6.0F).lightLevel(state -> 3)));
	public static final Block VOID_CRYSTAL_BLOCK = register("void_crystal_block",
			new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_MAGENTA).strength(5.0F, 6.0F)
					.requiresCorrectToolForDrops().sound(SoundType.AMETHYST).lightLevel(state -> 7)));
	public static final Block LUMINITE_CLUSTER = register("luminite_cluster",
			new AmethystClusterBlock(7.0F, 3.0F, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN)
					.forceSolidOn().noOcclusion().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F)
					.lightLevel(state -> 10).pushReaction(PushReaction.DESTROY)));
	public static final Block ABYSSAL_SAND = register("abyssal_sand",
			new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(0.5F)
					.sound(SoundType.SAND)));
	public static final Block ABYSSAL_BRICKS = register("abyssal_bricks",
			new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(2.0F, 6.0F)
					.requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE_BRICKS)));
	public static final Block ABYSSAL_CORAL_BLOCK = register("abyssal_coral_block",
			new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(1.5F, 6.0F)
					.requiresCorrectToolForDrops().sound(SoundType.CORAL_BLOCK).lightLevel(state -> 8)));
	public static final Block ABYSSAL_ANEMONE = register("abyssal_anemone",
			new AmethystClusterBlock(6.0F, 4.0F, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE)
					.forceSolidOn().noOcclusion().noCollission().sound(SoundType.WET_GRASS).instabreak()
					.lightLevel(state -> 12).pushReaction(PushReaction.DESTROY)));
	public static final Block ECLIPSE_PORTAL = register("eclipse_portal",
			new EclipsePortalBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(5.0F, 1200.0F)
					.noCollission().noOcclusion().lightLevel(state -> 12).sound(SoundType.GLASS)
					.pushReaction(PushReaction.BLOCK)), Rarity.EPIC);

	private static Block register(String name, Block block) {
		return register(name, block, Rarity.COMMON);
	}

	private static Block register(String name, Block block, Rarity rarity) {
		Registry.register(BuiltInRegistries.BLOCK, EclipseRealms.id(name), block);
		BlockItem item = new BlockItem(block, new Item.Properties().rarity(rarity));
		item.registerBlocks(Item.BY_BLOCK, item);
		Registry.register(BuiltInRegistries.ITEM, EclipseRealms.id(name), item);
		return block;
	}

	public static void init() {
	}

	private ModBlocks() {
	}
}
