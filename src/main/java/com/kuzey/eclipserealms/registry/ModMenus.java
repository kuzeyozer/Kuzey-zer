package com.kuzey.eclipserealms.registry;

import com.kuzey.eclipserealms.EclipseRealms;
import com.kuzey.eclipserealms.menu.EclipseStandMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public final class ModMenus {
	public static final MenuType<EclipseStandMenu> ECLIPSE_STAND = Registry.register(BuiltInRegistries.MENU,
			EclipseRealms.id("eclipse_stand"), new MenuType<>(EclipseStandMenu::new, FeatureFlags.VANILLA_SET));

	public static void init() {
	}

	private ModMenus() {
	}
}
