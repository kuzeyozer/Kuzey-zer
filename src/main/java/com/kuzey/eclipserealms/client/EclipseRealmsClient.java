package com.kuzey.eclipserealms.client;

import com.kuzey.eclipserealms.client.renderer.EmberFiendRenderer;
import com.kuzey.eclipserealms.client.renderer.UmbralStalkerRenderer;
import com.kuzey.eclipserealms.client.renderer.VoidWispRenderer;
import com.kuzey.eclipserealms.registry.ModBlocks;
import com.kuzey.eclipserealms.registry.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;

public class EclipseRealmsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STARLIGHT_LEAVES, RenderType.cutoutMipped());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LUMINITE_CLUSTER, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ABYSSAL_ANEMONE, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ECLIPSE_PORTAL, RenderType.translucent());

		EntityRendererRegistry.register(ModEntities.UMBRAL_STALKER, UmbralStalkerRenderer::new);
		EntityRendererRegistry.register(ModEntities.EMBER_FIEND, EmberFiendRenderer::new);
		EntityRendererRegistry.register(ModEntities.VOID_WISP, VoidWispRenderer::new);
	}
}
