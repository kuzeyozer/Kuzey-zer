package com.kuzey.eclipserealms;

import com.kuzey.eclipserealms.registry.ModArmorMaterials;
import com.kuzey.eclipserealms.registry.ModBlocks;
import com.kuzey.eclipserealms.registry.ModCreativeTab;
import com.kuzey.eclipserealms.registry.ModEntities;
import com.kuzey.eclipserealms.registry.ModItems;
import com.kuzey.eclipserealms.registry.ModMenus;
import com.kuzey.eclipserealms.registry.ModPoi;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EclipseRealms implements ModInitializer {
	public static final String MOD_ID = "eclipserealms";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		ModBlocks.init();
		ModArmorMaterials.init();
		ModEntities.init();
		ModItems.init();
		ModMenus.init();
		ModPoi.init();
		ModCreativeTab.init();
		LOGGER.info("The eclipse has begun.");
	}
}
