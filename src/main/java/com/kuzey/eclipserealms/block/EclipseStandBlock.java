package com.kuzey.eclipserealms.block;

import com.kuzey.eclipserealms.menu.EclipseStandMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/** Workstation that forges Eclipse weapons and armor from a base item, a gem and a material. */
public class EclipseStandBlock extends Block {
	private static final Component TITLE = Component.translatable("container.eclipserealms.eclipse_stand");

	public EclipseStandBlock(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
		if (!level.isClientSide) {
			player.openMenu(state.getMenuProvider(level, pos));
		}
		return InteractionResult.sidedSuccess(level.isClientSide);
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
		return new SimpleMenuProvider((containerId, inventory, player) ->
				new EclipseStandMenu(containerId, inventory, ContainerLevelAccess.create(level, pos)), TITLE);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (random.nextInt(3) == 0) {
			level.addParticle(ParticleTypes.REVERSE_PORTAL, pos.getX() + 0.2 + random.nextDouble() * 0.6,
					pos.getY() + 1.05, pos.getZ() + 0.2 + random.nextDouble() * 0.6, 0.0, 0.04, 0.0);
		}
	}
}
