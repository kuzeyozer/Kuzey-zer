package com.kuzey.eclipserealms.item;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/** Sprays a fan of three fireballs. */
public class EmberStaffItem extends Item {
	private static final float SPREAD = (float) Math.toRadians(12.0);

	public EmberStaffItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!level.isClientSide) {
			Vec3 look = player.getLookAngle();
			for (int i = -1; i <= 1; i++) {
				Vec3 dir = look.yRot(SPREAD * i);
				SmallFireball fireball = new SmallFireball(level, player, dir);
				fireball.setPos(player.getX() + dir.x * 1.2, player.getEyeY() - 0.1 + dir.y * 1.2, player.getZ() + dir.z * 1.2);
				level.addFreshEntity(fireball);
			}
			level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLAZE_SHOOT,
					SoundSource.PLAYERS, 1.0F, 1.2F);
			stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
		}
		player.getCooldowns().addCooldown(this, 25);
		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		TooltipHelper.addWeaponLines(getDescriptionId(), tooltip);
	}
}
