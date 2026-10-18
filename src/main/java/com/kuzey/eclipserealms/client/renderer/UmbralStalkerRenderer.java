package com.kuzey.eclipserealms.client.renderer;

import com.kuzey.eclipserealms.EclipseRealms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;

public class UmbralStalkerRenderer extends ZombieRenderer {
	private static final ResourceLocation TEXTURE = EclipseRealms.id("textures/entity/umbral_stalker.png");

	public UmbralStalkerRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(Zombie entity) {
		return TEXTURE;
	}
}
