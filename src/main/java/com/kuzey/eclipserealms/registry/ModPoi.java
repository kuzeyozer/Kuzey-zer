package com.kuzey.eclipserealms.registry;

import com.kuzey.eclipserealms.EclipseRealms;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public final class ModPoi {
	public static final ResourceKey<PoiType> ECLIPSE_PORTAL =
			ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, EclipseRealms.id("eclipse_portal"));

	public static void init() {
		PointOfInterestHelper.register(ECLIPSE_PORTAL.location(), 0, 1, ModBlocks.ECLIPSE_PORTAL);
	}

	private ModPoi() {
	}
}
