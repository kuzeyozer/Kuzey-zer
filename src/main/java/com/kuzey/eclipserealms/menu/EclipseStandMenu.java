package com.kuzey.eclipserealms.menu;

import com.kuzey.eclipserealms.registry.ModBlocks;
import com.kuzey.eclipserealms.registry.ModMenus;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.Optional;

public class EclipseStandMenu extends AbstractContainerMenu {
	public static final int BASE_SLOT = 0;
	public static final int GEM_SLOT = 1;
	public static final int MATERIAL_SLOT = 2;
	public static final int RESULT_SLOT = 3;
	private static final int INVENTORY_START = 4;
	private static final int INVENTORY_END = INVENTORY_START + 36;

	private final ContainerLevelAccess access;
	private final Container inputs = new SimpleContainer(3) {
		@Override
		public void setChanged() {
			super.setChanged();
			EclipseStandMenu.this.slotsChanged(this);
		}
	};
	private final ResultContainer result = new ResultContainer();

	public EclipseStandMenu(int containerId, Inventory playerInventory) {
		this(containerId, playerInventory, ContainerLevelAccess.NULL);
	}

	public EclipseStandMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
		super(ModMenus.ECLIPSE_STAND, containerId);
		this.access = access;

		addSlot(new Slot(inputs, BASE_SLOT, 27, 47));
		addSlot(new Slot(inputs, GEM_SLOT, 51, 47));
		addSlot(new Slot(inputs, MATERIAL_SLOT, 75, 47));
		addSlot(new Slot(result, 0, 133, 47) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;
			}

			@Override
			public void onTake(Player player, ItemStack stack) {
				craft(player);
				super.onTake(player, stack);
			}
		});

		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 9; col++) {
				addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
			}
		}
		for (int col = 0; col < 9; col++) {
			addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
		}
	}

	private Optional<StandRecipe> currentRecipe() {
		return StandRecipes.find(inputs.getItem(BASE_SLOT), inputs.getItem(GEM_SLOT), inputs.getItem(MATERIAL_SLOT));
	}

	@Override
	public void slotsChanged(Container container) {
		super.slotsChanged(container);
		if (container == inputs) {
			result.setItem(0, currentRecipe().map(this::assemble).orElse(ItemStack.EMPTY));
		}
	}

	/** Builds the result, carrying over any enchantments from the base item. */
	private ItemStack assemble(StandRecipe recipe) {
		ItemStack out = recipe.result().get();
		ItemEnchantments enchantments = inputs.getItem(BASE_SLOT).get(DataComponents.ENCHANTMENTS);
		if (enchantments != null && !enchantments.isEmpty()) {
			out.set(DataComponents.ENCHANTMENTS, enchantments);
		}
		return out;
	}

	private void craft(Player player) {
		currentRecipe().ifPresent(recipe -> {
			inputs.removeItem(BASE_SLOT, 1);
			inputs.removeItem(GEM_SLOT, recipe.gemCount());
			inputs.removeItem(MATERIAL_SLOT, recipe.materialCount());
		});
		access.execute((level, pos) -> level.playSound(null, pos, SoundEvents.SMITHING_TABLE_USE, SoundSource.BLOCKS, 1.0F,
				0.7F + level.random.nextFloat() * 0.2F));
	}

	/**
	 * Called (server side) when the player clicks a recipe in the stand's recipe book: returns whatever is in the
	 * input slots and pulls the recipe's ingredients out of the player's inventory instead.
	 */
	@Override
	public boolean clickMenuButton(Player player, int id) {
		if (id < 0 || id >= StandRecipes.all().size()) {
			return false;
		}
		StandRecipe recipe = StandRecipes.all().get(id);
		for (int slot = BASE_SLOT; slot <= MATERIAL_SLOT; slot++) {
			ItemStack previous = inputs.removeItemNoUpdate(slot);
			if (!previous.isEmpty()) {
				player.getInventory().placeItemBackInInventory(previous);
			}
		}
		fillFromInventory(player, BASE_SLOT, recipe.base(), 1);
		fillFromInventory(player, GEM_SLOT, recipe.gem(), recipe.gemCount());
		fillFromInventory(player, MATERIAL_SLOT, recipe.material(), recipe.materialCount());
		slotsChanged(inputs);
		return true;
	}

	private void fillFromInventory(Player player, int slot, Ingredient ingredient, int count) {
		Inventory inventory = player.getInventory();
		for (int i = 0; i < inventory.items.size() && count > 0; i++) {
			ItemStack stack = inventory.items.get(i);
			if (stack.isEmpty() || !ingredient.test(stack)) {
				continue;
			}
			ItemStack current = inputs.getItem(slot);
			if (!current.isEmpty() && !ItemStack.isSameItemSameComponents(current, stack)) {
				continue;
			}
			ItemStack taken = stack.split(Math.min(count, stack.getCount()));
			count -= taken.getCount();
			if (current.isEmpty()) {
				inputs.setItem(slot, taken);
			} else {
				current.grow(taken.getCount());
			}
		}
	}

	/** The item currently in one of the three input slots (used by the recipe book to count what the player has). */
	public ItemStack getInput(int slot) {
		return inputs.getItem(slot);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		Slot slot = slots.get(index);
		if (!slot.hasItem()) {
			return ItemStack.EMPTY;
		}
		ItemStack stack = slot.getItem();
		ItemStack original = stack.copy();
		if (index == RESULT_SLOT) {
			if (!moveItemStackTo(stack, INVENTORY_START, INVENTORY_END, true)) {
				return ItemStack.EMPTY;
			}
			slot.onQuickCraft(stack, original);
		} else if (index < RESULT_SLOT) {
			if (!moveItemStackTo(stack, INVENTORY_START, INVENTORY_END, false)) {
				return ItemStack.EMPTY;
			}
		} else if (!moveItemStackTo(stack, BASE_SLOT, RESULT_SLOT, false)) {
			return ItemStack.EMPTY;
		}

		if (stack.isEmpty()) {
			slot.setByPlayer(ItemStack.EMPTY);
		} else {
			slot.setChanged();
		}
		if (stack.getCount() == original.getCount()) {
			return ItemStack.EMPTY;
		}
		slot.onTake(player, stack);
		return original;
	}

	@Override
	public boolean stillValid(Player player) {
		return stillValid(access, player, ModBlocks.ECLIPSE_STAND);
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		access.execute((level, pos) -> clearContainer(player, inputs));
	}
}
