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

/** Life-stealing crystal sword. Right-click to blink forward through the void. */
public class VoidbladeItem extends SwordItem {
	private static final double BLINK_DISTANCE = 8.0;

	public VoidbladeItem(Tier tier, Properties properties) {
		super(tier, properties);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		attacker.heal(1.5F);
		if (attacker.getRandom().nextFloat() < 0.25F) {
			target.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 1), attacker);
		}
		if (attacker.level() instanceof ServerLevel level) {
			level.sendParticles(ParticleTypes.REVERSE_PORTAL, target.getX(), target.getY(0.5), target.getZ(),
					12, 0.3, 0.4, 0.3, 0.05);
		}
		return super.hurtEnemy(stack, target, attacker);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!level.isClientSide) {
			Vec3 start = player.position();
			Vec3 look = player.getLookAngle();
			Vec3 target = start;
			// Step forward until something solid is in the way.
			for (double d = 0.5; d <= BLINK_DISTANCE; d += 0.5) {
				Vec3 candidate = start.add(look.x * d, Math.max(0.0, look.y) * d, look.z * d);
				if (!level.noCollision(player, player.getBoundingBox().move(candidate.subtract(start)))) {
					break;
				}
				target = candidate;
			}
			ServerLevel serverLevel = (ServerLevel) level;
			serverLevel.sendParticles(ParticleTypes.PORTAL, start.x, start.y + 1, start.z, 40, 0.3, 0.6, 0.3, 0.5);
			player.teleportTo(target.x, target.y, target.z);
			player.resetFallDistance();
			serverLevel.sendParticles(ParticleTypes.REVERSE_PORTAL, target.x, target.y + 1, target.z, 40, 0.3, 0.6, 0.3, 0.1);
			level.playSound(null, target.x, target.y, target.z, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.2F);
			stack.hurtAndBreak(2, player, LivingEntity.getSlotForHand(hand));
		}
		player.getCooldowns().addCooldown(this, 60);
		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		TooltipHelper.addWeaponLines(getDescriptionId(), tooltip);
	}
}
