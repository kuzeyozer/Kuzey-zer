package com.kuzey.eclipserealms.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

/** Burning scythe. Sets targets ablaze; right-click erupts an inferno ring around you. */
public class EmberScytheItem extends SwordItem {
	private static final double RADIUS = 5.0;

	public EmberScytheItem(Tier tier, Properties properties) {
		super(tier, properties);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		target.igniteForSeconds(5.0F);
		if (attacker.level() instanceof ServerLevel level) {
			level.sendParticles(ParticleTypes.FLAME, target.getX(), target.getY(0.5), target.getZ(),
					12, 0.3, 0.4, 0.3, 0.03);
		}
		return super.hurtEnemy(stack, target, attacker);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!level.isClientSide) {
			ServerLevel serverLevel = (ServerLevel) level;
			for (LivingEntity target : WeaponEffects.enemiesAround(level, player, RADIUS)) {
				target.hurt(player.damageSources().playerAttack(player), 6.0F);
				target.igniteForSeconds(8.0F);
			}
			for (double r = 1.0; r <= RADIUS; r += 1.0) {
				WeaponEffects.ring(serverLevel, ParticleTypes.FLAME, player.getX(), player.getY() + 0.1,
						player.getZ(), r, (int) (r * 12));
			}
			WeaponEffects.ring(serverLevel, ParticleTypes.LAVA, player.getX(), player.getY() + 0.1,
					player.getZ(), RADIUS, 16);
			level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLAZE_SHOOT,
					SoundSource.PLAYERS, 1.2F, 0.7F);
			stack.hurtAndBreak(4, player, LivingEntity.getSlotForHand(hand));
		}
		player.getCooldowns().addCooldown(this, 140);
		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		TooltipHelper.addWeaponLines(getDescriptionId(), tooltip);
	}
}
