package com.kuzey.eclipserealms.entity;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

/** A fast shadow that hunts in the dark and blinds whoever it strikes. */
public class UmbralStalker extends Zombie {
	public UmbralStalker(EntityType<? extends Zombie> type, Level level) {
		super(type, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Zombie.createAttributes()
				.add(Attributes.MAX_HEALTH, 40.0)
				.add(Attributes.ATTACK_DAMAGE, 7.0)
				.add(Attributes.MOVEMENT_SPEED, 0.3)
				.add(Attributes.ARMOR, 4.0)
				.add(Attributes.FOLLOW_RANGE, 40.0);
	}

	@Override
	public boolean doHurtTarget(Entity target) {
		boolean hit = super.doHurtTarget(target);
		if (hit && target instanceof LivingEntity living) {
			living.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 100, 0), this);
			living.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 0), this);
		}
		return hit;
	}

	@Override
	protected boolean isSunSensitive() {
		return false;
	}

	@Override
	protected boolean convertsInWater() {
		return false;
	}
}
