package com.kuzey.eclipserealms.item;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;

final class WeaponEffects {
	/** Living things around the player that an area ability should hit (never the player or their pets). */
	static List<LivingEntity> enemiesAround(Level level, Player player, double radius) {
		return level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(radius, 2.0, radius),
				entity -> entity != player && entity.isAlive() && !entity.isAlliedTo(player)
						&& !(entity instanceof OwnableEntity pet && pet.getOwner() == player)
						&& entity.distanceToSqr(player) <= radius * radius);
	}

	static void ring(ServerLevel level, ParticleOptions particle, double x, double y, double z, double radius, int points) {
		for (int i = 0; i < points; i++) {
			double angle = Math.PI * 2 * i / points;
			level.sendParticles(particle, x + Math.cos(angle) * radius, y, z + Math.sin(angle) * radius,
					1, 0.0, 0.05, 0.0, 0.0);
		}
	}

	private WeaponEffects() {
	}
}
