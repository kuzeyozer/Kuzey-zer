package com.kuzey.eclipserealms.client;

import com.kuzey.eclipserealms.registry.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public class EclipseRealmsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STARLIGHT_LEAVES, RenderType.cutoutMipped());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LUMINITE_CLUSTER, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ECLIPSE_PORTAL, RenderType.translucent());
	}
}
