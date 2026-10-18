package com.kuzey.eclipserealms.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

/** Armor that grants its set's effects while all four pieces of the same material are worn. */
public class EclipseArmorItem extends ArmorItem {
	private static final EquipmentSlot[] ARMOR_SLOTS =
			{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

	private final List<Holder<MobEffect>> setEffects;
	private final String setBonusKey;

	public EclipseArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties,
			String setBonusKey, List<Holder<MobEffect>> setEffects) {
		super(material, type, properties);
		this.setBonusKey = setBonusKey;
		this.setEffects = setEffects;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
		// Only the helmet drives the bonus, so effects are applied once per player rather than four times.
		if (level.isClientSide || getType() != Type.HELMET || !(entity instanceof Player player)
				|| player.getItemBySlot(EquipmentSlot.HEAD) != stack || level.getGameTime() % 40 != 0
				|| !wearingFullSet(player)) {
			return;
		}
		for (Holder<MobEffect> effect : setEffects) {
			player.addEffect(new MobEffectInstance(effect, 300, 0, true, false, true));
		}
	}

	private boolean wearingFullSet(Player player) {
		for (EquipmentSlot slot : ARMOR_SLOTS) {
			if (!(player.getItemBySlot(slot).getItem() instanceof ArmorItem armor) || armor.getMaterial() != getMaterial()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable(setBonusKey).withStyle(ChatFormatting.DARK_PURPLE));
		for (Holder<MobEffect> effect : setEffects) {
			tooltip.add(Component.literal(" - ").append(effect.value().getDisplayName()).withStyle(ChatFormatting.BLUE));
		}
	}
}
