package com.kuzey.eclipserealms.client.screen;

import com.kuzey.eclipserealms.EclipseRealms;
import com.kuzey.eclipserealms.menu.EclipseStandMenu;
import com.kuzey.eclipserealms.menu.StandRecipe;
import com.kuzey.eclipserealms.menu.StandRecipes;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * The Eclipse Stand screen with a recipe book panel on its left. The "Stand" tab lists every forging recipe and fills
 * the slots when clicked; the "Crafting" tab shows the mod's crafting table recipes with a 3x3 preview.
 */
public class EclipseStandScreen extends AbstractContainerScreen<EclipseStandMenu> {
	private static final ResourceLocation TEXTURE = EclipseRealms.id("textures/gui/eclipse_stand.png");
	private static final Component BASE = Component.translatable("container.eclipserealms.eclipse_stand.base");
	private static final Component GEM = Component.translatable("container.eclipserealms.eclipse_stand.gem");
	private static final Component MATERIAL = Component.translatable("container.eclipserealms.eclipse_stand.material");
	private static final Component STAND_TAB = Component.translatable("gui.eclipserealms.recipes.stand");
	private static final Component CRAFTING_TAB = Component.translatable("gui.eclipserealms.recipes.crafting");

	private static final int PANEL_WIDTH = 124;
	private static final int ROW_HEIGHT = 20;
	private static final int LIST_TOP = 22;
	private static final int STAND_ROWS = 7;
	private static final int CRAFTING_ROWS = 4;

	private static final int PANEL_COLOR = 0xF0190F26;
	private static final int BORDER_COLOR = 0xFFB8862B;
	private static final int ROW_COLOR = 0xFF2A1F3A;
	private static final int ROW_READY_COLOR = 0xFF4A3480;
	private static final int ROW_HOVER_COLOR = 0xFF6A4AA8;
	private static final int TEXT_COLOR = 0xFFE7B0FF;

	private boolean craftingTab;
	private int scroll;
	private int selectedCrafting = -1;
	private List<CraftingRecipe> craftingRecipes = List.of();

	public EclipseStandScreen(EclipseStandMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
	}

	@Override
	protected void init() {
		super.init();
		// Shift the stand right so the stand and the recipe panel are centered together.
		leftPos = Math.max(PANEL_WIDTH, (width - imageWidth - PANEL_WIDTH) / 2 + PANEL_WIDTH);
		craftingRecipes = loadCraftingRecipes();
	}

	private List<CraftingRecipe> loadCraftingRecipes() {
		if (minecraft == null || minecraft.level == null) {
			return List.of();
		}
		List<CraftingRecipe> found = new ArrayList<>();
		for (RecipeHolder<CraftingRecipe> holder : minecraft.level.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING)) {
			if (holder.id().getNamespace().equals(EclipseRealms.MOD_ID)) {
				found.add(holder.value());
			}
		}
		found.sort(Comparator.comparing(recipe -> result(recipe).getHoverName().getString()));
		return found;
	}

	private ItemStack result(CraftingRecipe recipe) {
		return recipe.getResultItem(minecraft.level.registryAccess());
	}

	private int panelX() {
		return leftPos - PANEL_WIDTH;
	}

	private int rowCount() {
		return craftingTab ? craftingRecipes.size() : StandRecipes.all().size();
	}

	private int visibleRows() {
		return craftingTab ? CRAFTING_ROWS : STAND_ROWS;
	}

	private int maxScroll() {
		return Math.max(0, rowCount() - visibleRows());
	}

	// ---------------------------------------------------------------- rendering

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
		super.render(graphics, mouseX, mouseY, partialTick);
		renderRecipePanel(graphics, mouseX, mouseY);
		renderTooltip(graphics, mouseX, mouseY);
		renderRecipeTooltip(graphics, mouseX, mouseY);
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

	private void renderRecipePanel(GuiGraphics graphics, int mouseX, int mouseY) {
		int x = panelX();
		int y = topPos;
		int w = PANEL_WIDTH - 4;
		graphics.fill(x, y, x + w, y + imageHeight, PANEL_COLOR);
		graphics.renderOutline(x, y, w, imageHeight, BORDER_COLOR);

		renderTab(graphics, x + 4, y + 4, STAND_TAB, !craftingTab, mouseX, mouseY);
		renderTab(graphics, x + 61, y + 4, CRAFTING_TAB, craftingTab, mouseX, mouseY);

		for (int i = 0; i < visibleRows(); i++) {
			int index = scroll + i;
			if (index >= rowCount()) {
				break;
			}
			int rowY = y + LIST_TOP + i * ROW_HEIGHT;
			boolean hovered = isOverRow(mouseX, mouseY, i);
			if (craftingTab) {
				CraftingRecipe recipe = craftingRecipes.get(index);
				int color = hovered ? ROW_HOVER_COLOR : ROW_READY_COLOR;
				renderRow(graphics, x + 4, rowY, color, result(recipe), index == selectedCrafting);
			} else {
				StandRecipe recipe = StandRecipes.all().get(index);
				int color = hovered ? ROW_HOVER_COLOR : canForge(recipe) ? ROW_READY_COLOR : ROW_COLOR;
				renderRow(graphics, x + 4, rowY, color, recipe.result().get(), false);
			}
		}

		// Scrollbar
		if (maxScroll() > 0) {
			int trackTop = y + LIST_TOP;
			int trackHeight = visibleRows() * ROW_HEIGHT - 2;
			int thumbHeight = Math.max(8, trackHeight * visibleRows() / rowCount());
			int thumbY = trackTop + (trackHeight - thumbHeight) * scroll / maxScroll();
			graphics.fill(x + w - 5, trackTop, x + w - 2, trackTop + trackHeight, 0xFF120A1C);
			graphics.fill(x + w - 5, thumbY, x + w - 2, thumbY + thumbHeight, BORDER_COLOR);
		}

		if (craftingTab) {
			renderCraftingPreview(graphics, x, y + LIST_TOP + CRAFTING_ROWS * ROW_HEIGHT + 4);
		}
	}

	private void renderTab(GuiGraphics graphics, int x, int y, Component label, boolean active, int mouseX, int mouseY) {
		boolean hovered = mouseX >= x && mouseX < x + 55 && mouseY >= y && mouseY < y + 14;
		graphics.fill(x, y, x + 55, y + 14, active ? 0xFF6A4AA8 : hovered ? 0xFF4A3480 : 0xFF2A1F3A);
		if (active) {
			graphics.renderOutline(x, y, 55, 14, BORDER_COLOR);
		}
		graphics.drawCenteredString(font, label, x + 28, y + 3, active ? 0xFFFFD36B : TEXT_COLOR);
	}

	private void renderRow(GuiGraphics graphics, int x, int y, int color, ItemStack icon, boolean selected) {
		int w = PANEL_WIDTH - 16;
		graphics.fill(x, y, x + w, y + ROW_HEIGHT - 2, color);
		if (selected) {
			graphics.renderOutline(x, y, w, ROW_HEIGHT - 2, BORDER_COLOR);
		}
		graphics.renderItem(icon, x + 1, y + 1);
		String name = icon.getHoverName().getString();
		int maxWidth = w - 22;
		if (font.width(name) > maxWidth) {
			while (name.length() > 1 && font.width(name + "...") > maxWidth) {
				name = name.substring(0, name.length() - 1);
			}
			name += "...";
		}
		graphics.drawString(font, name, x + 20, y + 5, TEXT_COLOR, false);
	}

	private void renderCraftingPreview(GuiGraphics graphics, int panelX, int y) {
		if (selectedCrafting < 0 || selectedCrafting >= craftingRecipes.size()) {
			graphics.drawString(font, Component.translatable("gui.eclipserealms.recipes.select"), panelX + 8, y + 20,
					0xFF8A7AA8, false);
			return;
		}
		CraftingRecipe recipe = craftingRecipes.get(selectedCrafting);
		ItemStack[] grid = layoutGrid(recipe);
		int gridX = panelX + 8;
		for (int i = 0; i < 9; i++) {
			int cellX = gridX + (i % 3) * 18;
			int cellY = y + (i / 3) * 18;
			graphics.fill(cellX, cellY, cellX + 17, cellY + 17, 0xFF8B8B8B);
			graphics.fill(cellX, cellY, cellX + 16, cellY + 16, 0xFF373737);
			graphics.fill(cellX + 1, cellY + 1, cellX + 16, cellY + 16, 0xFF8B8B8B);
			if (!grid[i].isEmpty()) {
				graphics.renderItem(grid[i], cellX, cellY);
			}
		}
		graphics.drawString(font, "→", gridX + 58, y + 22, BORDER_COLOR, false);
		ItemStack result = result(recipe);
		graphics.fill(gridX + 72, y + 14, gridX + 98, y + 40, 0xFF8B8B8B);
		graphics.renderItem(result, gridX + 77, y + 19);
		graphics.renderItemDecorations(font, result, gridX + 77, y + 19);
	}

	/** Lays a crafting recipe out on a 3x3 grid, cycling through ingredient options once per second. */
	private ItemStack[] layoutGrid(CraftingRecipe recipe) {
		ItemStack[] grid = new ItemStack[9];
		Arrays.fill(grid, ItemStack.EMPTY);
		NonNullList<Ingredient> ingredients = recipe.getIngredients();
		int width = recipe instanceof ShapedRecipe shaped ? shaped.getWidth() : 3;
		for (int i = 0; i < ingredients.size() && i < 9; i++) {
			int row = i / width;
			int col = i % width;
			grid[row * 3 + col] = displayStack(ingredients.get(i));
		}
		return grid;
	}

	private static ItemStack displayStack(Ingredient ingredient) {
		ItemStack[] options = ingredient.getItems();
		if (options.length == 0) {
			return ItemStack.EMPTY;
		}
		return options[(int) (System.currentTimeMillis() / 1000 % options.length)];
	}

	private void renderRecipeTooltip(GuiGraphics graphics, int mouseX, int mouseY) {
		int row = hoveredRow(mouseX, mouseY);
		if (row < 0) {
			return;
		}
		int index = scroll + row;
		List<Component> lines = new ArrayList<>();
		if (craftingTab) {
			lines.add(result(craftingRecipes.get(index)).getHoverName());
			lines.add(Component.translatable("gui.eclipserealms.recipes.crafting_hint").withStyle(ChatFormatting.GRAY));
		} else {
			StandRecipe recipe = StandRecipes.all().get(index);
			lines.add(recipe.result().get().getHoverName());
			lines.add(ingredientLine(BASE, recipe.base(), 1));
			lines.add(ingredientLine(GEM, recipe.gem(), recipe.gemCount()));
			lines.add(ingredientLine(MATERIAL, recipe.material(), recipe.materialCount()));
			lines.add(canForge(recipe)
					? Component.translatable("gui.eclipserealms.recipes.click_to_fill").withStyle(ChatFormatting.GREEN)
					: Component.translatable("gui.eclipserealms.recipes.missing").withStyle(ChatFormatting.RED));
		}
		graphics.renderComponentTooltip(font, lines, mouseX, mouseY);
	}

	private Component ingredientLine(Component role, Ingredient ingredient, int count) {
		ItemStack shown = displayStack(ingredient);
		return Component.literal("")
				.append(role.copy().withStyle(ChatFormatting.DARK_PURPLE))
				.append(Component.literal(": " + count + " x ").withStyle(ChatFormatting.GRAY))
				.append(shown.getHoverName().copy().withStyle(ChatFormatting.WHITE));
	}

	// ---------------------------------------------------------------- availability

	private boolean canForge(StandRecipe recipe) {
		return countAvailable(recipe.base()) >= 1
				&& countAvailable(recipe.gem()) >= recipe.gemCount()
				&& countAvailable(recipe.material()) >= recipe.materialCount();
	}

	/** Counts matching items in the player's inventory plus what is already sitting in the stand's input slots. */
	private int countAvailable(Ingredient ingredient) {
		int total = 0;
		if (minecraft != null && minecraft.player != null) {
			for (ItemStack stack : minecraft.player.getInventory().items) {
				if (!stack.isEmpty() && ingredient.test(stack)) {
					total += stack.getCount();
				}
			}
		}
		for (int slot = EclipseStandMenu.BASE_SLOT; slot <= EclipseStandMenu.MATERIAL_SLOT; slot++) {
			ItemStack stack = menu.getInput(slot);
			if (!stack.isEmpty() && ingredient.test(stack)) {
				total += stack.getCount();
			}
		}
		return total;
	}

	// ---------------------------------------------------------------- input

	private boolean isOverRow(double mouseX, double mouseY, int row) {
		int x = panelX() + 4;
		int y = topPos + LIST_TOP + row * ROW_HEIGHT;
		return mouseX >= x && mouseX < x + PANEL_WIDTH - 16 && mouseY >= y && mouseY < y + ROW_HEIGHT - 2;
	}

	private int hoveredRow(double mouseX, double mouseY) {
		for (int i = 0; i < visibleRows() && scroll + i < rowCount(); i++) {
			if (isOverRow(mouseX, mouseY, i)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		int tabY = topPos + 4;
		if (mouseY >= tabY && mouseY < tabY + 14) {
			if (mouseX >= panelX() + 4 && mouseX < panelX() + 59) {
				switchTab(false);
				return true;
			}
			if (mouseX >= panelX() + 61 && mouseX < panelX() + 116) {
				switchTab(true);
				return true;
			}
		}
		int row = hoveredRow(mouseX, mouseY);
		if (row >= 0 && minecraft != null && minecraft.gameMode != null) {
			int index = scroll + row;
			if (craftingTab) {
				selectedCrafting = index;
			} else {
				minecraft.gameMode.handleInventoryButtonClick(menu.containerId, index);
			}
			playClick();
			return true;
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}

	private void switchTab(boolean crafting) {
		if (craftingTab != crafting) {
			craftingTab = crafting;
			scroll = 0;
			playClick();
		}
	}

	private void playClick() {
		if (minecraft != null) {
			minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		}
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
		if (mouseX >= panelX() && mouseX < leftPos) {
			scroll = Math.max(0, Math.min(maxScroll(), scroll - (int) Math.signum(scrollY)));
			return true;
		}
		return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
	}

	@Override
	protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop, int button) {
		boolean inPanel = mouseX >= panelX() && mouseX < leftPos && mouseY >= topPos && mouseY < topPos + imageHeight;
		return !inPanel && super.hasClickedOutside(mouseX, mouseY, guiLeft, guiTop, button);
	}
}
