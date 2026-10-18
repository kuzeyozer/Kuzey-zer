package com.kuzey.eclipserealms.client.renderer;

import com.kuzey.eclipserealms.EclipseRealms;
import net.minecraft.client.renderer.entity.BlazeRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Blaze;

public class EmberFiendRenderer extends BlazeRenderer {
	private static final ResourceLocation TEXTURE = EclipseRealms.id("textures/entity/ember_fiend.png");

	public EmberFiendRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(Blaze entity) {
		return TEXTURE;
	}
}
