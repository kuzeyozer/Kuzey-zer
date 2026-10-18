package com.kuzey.eclipserealms.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.level.Level;

/** A hulking fire spirit of the Ashen Wastes. */
public class EmberFiend extends Blaze {
	public EmberFiend(EntityType<? extends Blaze> type, Level level) {
		super(type, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Blaze.createAttributes()
				.add(Attributes.MAX_HEALTH, 45.0)
				.add(Attributes.ATTACK_DAMAGE, 8.0)
				.add(Attributes.ARMOR, 6.0);
	}
}
