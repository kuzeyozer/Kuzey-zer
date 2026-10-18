package com.kuzey.eclipserealms.block;

import com.kuzey.eclipserealms.world.EclipseTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A crafted rift into the Eclipse. Walking into it sends you to the other side, where a linked
 * return portal is found (or built) for you.
 */
public class EclipsePortalBlock extends Block {
	public EclipsePortalBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (!(level instanceof ServerLevel serverLevel) || entity.isPassenger() || entity.isVehicle()) {
			return;
		}
		if (entity.isOnPortalCooldown()) {
			entity.setPortalCooldown();
			return;
		}
		EclipseTeleporter.teleport(entity, serverLevel, pos);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		for (int i = 0; i < 3; i++) {
			double x = pos.getX() + random.nextDouble();
			double y = pos.getY() + random.nextDouble();
			double z = pos.getZ() + random.nextDouble();
			level.addParticle(ParticleTypes.REVERSE_PORTAL, x, y, z,
					(random.nextDouble() - 0.5) * 0.2, random.nextDouble() * 0.15, (random.nextDouble() - 0.5) * 0.2);
		}
		if (random.nextInt(4) == 0) {
			level.addParticle(ParticleTypes.END_ROD, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5,
					0.0, 0.05, 0.0);
		}
	}
}
