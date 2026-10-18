package com.kuzey.eclipserealms.client.screen;

import com.kuzey.eclipserealms.EclipseRealms;
import com.kuzey.eclipserealms.menu.EclipseStandMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class EclipseStandScreen extends AbstractContainerScreen<EclipseStandMenu> {
	private static final ResourceLocation TEXTURE = EclipseRealms.id("textures/gui/eclipse_stand.png");
	private static final Component BASE = Component.translatable("container.eclipserealms.eclipse_stand.base");
	private static final Component GEM = Component.translatable("container.eclipserealms.eclipse_stand.gem");
	private static final Component MATERIAL = Component.translatable("container.eclipserealms.eclipse_stand.material");

	public EclipseStandScreen(EclipseStandMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
		graphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);
	}

	@Override
	protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
		super.renderLabels(graphics, mouseX, mouseY);
		// Small captions above the three input slots so players know what goes where.
		graphics.pose().pushPose();
		graphics.pose().scale(0.5F, 0.5F, 1.0F);
		drawCentered(graphics, BASE, 35, 40);
		drawCentered(graphics, GEM, 59, 40);
		drawCentered(graphics, MATERIAL, 83, 40);
		graphics.pose().popPose();
	}

	private void drawCentered(GuiGraphics graphics, Component text, int centerX, int y) {
		graphics.drawString(font, text, centerX * 2 - font.width(text) / 2, y * 2, 0xE7B0FF, false);
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
		super.render(graphics, mouseX, mouseY, partialTick);
		renderTooltip(graphics, mouseX, mouseY);
	}
}
