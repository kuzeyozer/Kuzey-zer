package com.kuzey.eclipserealms.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/** Massive blade forged from eclipse metal. Right-click unleashes a shockwave. */
public class EclipseGreatswordItem extends SwordItem {
	private static final double RADIUS = 6.0;

	public EclipseGreatswordItem(Tier tier, Properties properties) {
		super(tier, properties);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		// Darkness strikes harder: bonus damage against anything below half health.
		if (target.getHealth() < target.getMaxHealth() / 2) {
			target.invulnerableTime = 0;
			target.hurt(attacker.damageSources().magic(), 4.0F);
		}
		return super.hurtEnemy(stack, target, attacker);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!level.isClientSide) {
			ServerLevel serverLevel = (ServerLevel) level;
			for (LivingEntity target : WeaponEffects.enemiesAround(level, player, RADIUS)) {
				target.hurt(player.damageSources().playerAttack(player), 10.0F);
				Vec3 away = target.position().subtract(player.position()).normalize();
				target.knockback(1.6, -away.x, -away.z);
				target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 1), player);
			}
			for (double r = 1.5; r <= RADIUS; r += 1.5) {
				WeaponEffects.ring(serverLevel, ParticleTypes.REVERSE_PORTAL, player.getX(), player.getY() + 0.2,
						player.getZ(), r, (int) (r * 10));
			}
			serverLevel.sendParticles(ParticleTypes.SONIC_BOOM, player.getX(), player.getY() + 1, player.getZ(),
					1, 0, 0, 0, 0);
			level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WARDEN_SONIC_BOOM,
					SoundSource.PLAYERS, 1.0F, 1.3F);
			stack.hurtAndBreak(5, player, LivingEntity.getSlotForHand(hand));
		}
		player.getCooldowns().addCooldown(this, 160);
		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		TooltipHelper.addWeaponLines(getDescriptionId(), tooltip);
	}
}
