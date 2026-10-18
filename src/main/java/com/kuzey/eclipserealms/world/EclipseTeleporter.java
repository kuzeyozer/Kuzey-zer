package com.kuzey.eclipserealms.world;

import com.kuzey.eclipserealms.registry.ModBlocks;
import com.kuzey.eclipserealms.registry.ModDimensions;
import com.kuzey.eclipserealms.registry.ModPoi;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public final class EclipseTeleporter {
	private static final int SEARCH_RADIUS = 64;

	public static void teleport(Entity entity, ServerLevel from, BlockPos portalPos) {
		ResourceKey<Level> destKey = from.dimension() == ModDimensions.ECLIPSE ? Level.OVERWORLD : ModDimensions.ECLIPSE;
		ServerLevel dest = from.getServer().getLevel(destKey);
		if (dest == null) {
			return;
		}

		BlockPos near = dest.getWorldBorder().clampToBounds(portalPos.getX(), portalPos.getY(), portalPos.getZ());
		BlockPos portal = findPortal(dest, near).orElseGet(() -> buildPortal(dest, near));
		Vec3 arrival = findArrivalSpot(dest, portal);

		entity.changeDimension(new DimensionTransition(dest, arrival, Vec3.ZERO, entity.getYRot(), entity.getXRot(),
				arrived -> {
					arrived.setPortalCooldown();
					dest.playSound(null, arrived.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.BLOCKS, 1.0F, 0.6F);
				}));
	}

	private static Optional<BlockPos> findPortal(ServerLevel level, BlockPos near) {
		PoiManager poi = level.getPoiManager();
		poi.ensureLoadedAndValid(level, near, SEARCH_RADIUS);
		return poi.findClosest(type -> type.is(ModPoi.ECLIPSE_PORTAL), near, SEARCH_RADIUS, PoiManager.Occupancy.ANY);
	}

	/** Builds a small brick shrine with a return portal in the middle. */
	private static BlockPos buildPortal(ServerLevel level, BlockPos near) {
		int x = near.getX();
		int z = near.getZ();
		int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
		if (y <= level.getMinBuildHeight() + 1) {
			y = level.getSeaLevel() + 8;
		}
		y = Mth.clamp(y, level.getMinBuildHeight() + 5, level.getMaxBuildHeight() - 6);
		BlockPos base = new BlockPos(x, y, z);

		BlockState floor = ModBlocks.UMBRAL_BRICKS.defaultBlockState();
		BlockState air = Blocks.AIR.defaultBlockState();
		for (int dx = -2; dx <= 2; dx++) {
			for (int dz = -2; dz <= 2; dz++) {
				level.setBlockAndUpdate(base.offset(dx, -1, dz), floor);
				for (int dy = 0; dy <= 3; dy++) {
					level.setBlockAndUpdate(base.offset(dx, dy, dz), air);
				}
			}
		}
		for (int dx = -2; dx <= 2; dx += 4) {
			for (int dz = -2; dz <= 2; dz += 4) {
				level.setBlockAndUpdate(base.offset(dx, 0, dz), Blocks.SOUL_LANTERN.defaultBlockState());
			}
		}
		level.setBlockAndUpdate(base, ModBlocks.ECLIPSE_PORTAL.defaultBlockState());
		return base;
	}

	private static Vec3 findArrivalSpot(ServerLevel level, BlockPos portal) {
		for (int dist = 2; dist >= 1; dist--) {
			for (Direction dir : Direction.Plane.HORIZONTAL) {
				BlockPos feet = portal.relative(dir, dist);
				if (level.isEmptyBlock(feet) && level.isEmptyBlock(feet.above())
						&& level.getBlockState(feet.below()).isFaceSturdy(level, feet.below(), Direction.UP)) {
					return Vec3.atBottomCenterOf(feet);
				}
			}
		}
		return Vec3.atBottomCenterOf(portal.above());
	}

	private EclipseTeleporter() {
	}
}
