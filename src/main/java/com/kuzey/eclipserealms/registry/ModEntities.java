package com.kuzey.eclipserealms.registry;

import com.kuzey.eclipserealms.EclipseRealms;
import com.kuzey.eclipserealms.entity.EmberFiend;
import com.kuzey.eclipserealms.entity.UmbralStalker;
import com.kuzey.eclipserealms.entity.VoidWisp;
import com.kuzey.eclipserealms.mixin.SpawnPlacementsInvoker;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;

public final class ModEntities {
	public static final EntityType<UmbralStalker> UMBRAL_STALKER = register("umbral_stalker",
			EntityType.Builder.of(UmbralStalker::new, MobCategory.MONSTER).sized(0.6F, 1.95F).eyeHeight(1.74F)
					.clientTrackingRange(8));
	public static final EntityType<EmberFiend> EMBER_FIEND = register("ember_fiend",
			EntityType.Builder.of(EmberFiend::new, MobCategory.MONSTER).sized(0.6F, 1.8F).fireImmune()
					.clientTrackingRange(8));
	public static final EntityType<VoidWisp> VOID_WISP = register("void_wisp",
			EntityType.Builder.of(VoidWisp::new, MobCategory.MONSTER).sized(0.4F, 0.8F).fireImmune()
					.clientTrackingRange(8));

	private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, EclipseRealms.id(name), builder.build(name));
	}

	public static void init() {
		FabricDefaultAttributeRegistry.register(UMBRAL_STALKER, UmbralStalker.createAttributes());
		FabricDefaultAttributeRegistry.register(EMBER_FIEND, EmberFiend.createAttributes());
		FabricDefaultAttributeRegistry.register(VOID_WISP, VoidWisp.createAttributes());

		SpawnPlacementsInvoker.eclipserealms$register(UMBRAL_STALKER, SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacementsInvoker.eclipserealms$register(EMBER_FIEND, SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules);
		SpawnPlacementsInvoker.eclipserealms$register(VOID_WISP, SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
	}

	private ModEntities() {
	}
}
