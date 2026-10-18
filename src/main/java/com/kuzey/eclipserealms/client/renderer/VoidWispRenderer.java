package com.kuzey.eclipserealms.client.renderer;

import com.kuzey.eclipserealms.EclipseRealms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.VexRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Vex;

public class VoidWispRenderer extends VexRenderer {
	private static final ResourceLocation TEXTURE = EclipseRealms.id("textures/entity/void_wisp.png");

	public VoidWispRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(Vex entity) {
		return TEXTURE;
	}
}
