package com.kuzey.eclipserealms.item;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/** Fires withering void skulls. */
public class VoidStaffItem extends Item {
	public VoidStaffItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!level.isClientSide) {
			Vec3 look = player.getLookAngle();
			WitherSkull skull = new WitherSkull(level, player, look);
			skull.setPos(player.getX() + look.x * 1.5, player.getEyeY() - 0.1 + look.y * 1.5, player.getZ() + look.z * 1.5);
			level.addFreshEntity(skull);
			level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WITHER_SHOOT,
					SoundSource.PLAYERS, 0.6F, 1.4F);
			stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
		}
		player.getCooldowns().addCooldown(this, 12);
		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		TooltipHelper.addWeaponLines(getDescriptionId(), tooltip);
	}
}
