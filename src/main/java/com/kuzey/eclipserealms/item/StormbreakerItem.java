package com.kuzey.eclipserealms.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/** Thunder hammer. Hits crackle with extra shock damage; right-click calls lightning where you look. */
public class StormbreakerItem extends SwordItem {
	private static final double RANGE = 48.0;

	public StormbreakerItem(Tier tier, Properties properties) {
		super(tier, properties);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		target.setDeltaMovement(target.getDeltaMovement().add(0.0, 0.35, 0.0));
		if (attacker.getRandom().nextFloat() < 0.5F) {
			target.invulnerableTime = 0;
			target.hurt(attacker.damageSources().lightningBolt(), 6.0F);
		}
		if (attacker.level() instanceof ServerLevel level) {
			level.sendParticles(ParticleTypes.ELECTRIC_SPARK, target.getX(), target.getY(0.5), target.getZ(),
					15, 0.4, 0.5, 0.4, 0.2);
		}
		return super.hurtEnemy(stack, target, attacker);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		HitResult hit = player.pick(RANGE, 1.0F, false);
		if (hit.getType() != HitResult.Type.BLOCK) {
			return InteractionResultHolder.fail(stack);
		}
		if (!level.isClientSide) {
			BlockPos target = ((BlockHitResult) hit).getBlockPos().above();
			// A storm of three bolts: one on the target and two around it.
			BlockPos[] strikes = {target, target.offset(2, 0, 1), target.offset(-1, 0, -2)};
			for (BlockPos strike : strikes) {
				LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);
				if (bolt != null) {
					bolt.moveTo(Vec3.atBottomCenterOf(strike));
					if (player instanceof ServerPlayer serverPlayer) {
						bolt.setCause(serverPlayer);
					}
					level.addFreshEntity(bolt);
				}
			}
			stack.hurtAndBreak(3, player, LivingEntity.getSlotForHand(hand));
		}
		player.getCooldowns().addCooldown(this, 80);
		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		TooltipHelper.addWeaponLines(getDescriptionId(), tooltip);
	}
}
