package com.kuzey.eclipserealms.registry;

import com.kuzey.eclipserealms.EclipseRealms;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public final class ModDimensions {
	/** The Eclipse: an eternally-dark realm defined in data/eclipserealms/dimension/eclipse.json. */
	public static final ResourceKey<Level> ECLIPSE = ResourceKey.create(Registries.DIMENSION, EclipseRealms.id("eclipse"));

	private ModDimensions() {
	}
}
