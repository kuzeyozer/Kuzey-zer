package com.kuzey.eclipserealms.entity;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.level.Level;

/** A flitting spirit that drifts through walls and saps strength. */
public class VoidWisp extends Vex {
	public VoidWisp(EntityType<? extends Vex> type, Level level) {
		super(type, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Vex.createAttributes()
				.add(Attributes.MAX_HEALTH, 22.0)
				.add(Attributes.ATTACK_DAMAGE, 6.0);
	}

	@Override
	public boolean doHurtTarget(Entity target) {
		boolean hit = super.doHurtTarget(target);
		if (hit && target instanceof LivingEntity living) {
			living.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 0), this);
		}
		return hit;
	}
}
