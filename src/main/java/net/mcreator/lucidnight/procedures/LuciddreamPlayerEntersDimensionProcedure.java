package net.mcreator.lucidnight.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

public class LuciddreamPlayerEntersDimensionProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		boolean found = false;
		double sx = 0;
		double sy = 0;
		double sz = 0;
		if ((entity.level().dimension()) == ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("lucidnight:luciddream"))) {
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
						"attribute @s neoforge:creative_flight base set 1");
		}
		BlockPos entityPos = new BlockPos((int) x, (int) y, (int) z);
		Level world = (Level) dependencies.get("world");
		Entity entity = (Entity) dependencies.get("entity");
		if (world != null && entity != null) {
			BlockPos closestGrass = null;
			double closestDistanceSq = Double.MAX_VALUE;
			int radius = 250;
			// Scan horizontal progressif
			for (int dx = -radius; dx <= radius; dx++) {
				for (int dz = -radius; dz <= radius; dz++) {
					int targetX = entityPos.getX() + dx;
					int targetZ = entityPos.getZ() + dz;
					// Récupère instantanément la hauteur maximale de la surface (Zéro lag)
					int highestY = world.getHeight(Heightmap.Types.WORLD_SURFACE, targetX, targetZ);
					BlockPos checkPos = new BlockPos(targetX, highestY - 1, targetZ);
					if (world.getBlockState(checkPos).is(Blocks.GRASS_BLOCK)) {
						// Vérifie si l'espace au-dessus est bien de l'air pour se TP en sécurité
						if (world.isEmptyBlock(checkPos.above()) && world.isEmptyBlock(checkPos.above(2))) {
							double distSq = entityPos.distSqr(checkPos);
							// On garde uniquement le plus proche de la position de départ
							if (distSq < closestDistanceSq) {
								closestDistanceSq = distSq;
								closestGrass = checkPos;
							}
						}
					}
				}
			}
			// Si on a trouvé un bloc d'herbe valide, on y téléporte le joueur
			if (closestGrass != null) {
				entity.teleportTo(closestGrass.getX() + 0.5, closestGrass.getY() + 1, closestGrass.getZ() + 0.5);
			}
		}
	}
}