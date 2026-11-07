package net.mcreator.lucidnight.world.dimension;

import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.client.renderer.DimensionSpecialEffects;

import net.mcreator.lucidnight.procedures.LuciddreamPlayerLeavesDimensionProcedure;
import net.mcreator.lucidnight.procedures.LuciddreamPlayerEntersDimensionProcedure;

@EventBusSubscriber
public class LuciddreamDimension {
	@EventBusSubscriber(Dist.CLIENT)
	public static class LuciddreamSpecialEffectsHandler {
		@SubscribeEvent
		public static void registerDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
			DimensionSpecialEffects customEffect = new DimensionSpecialEffects(192f, true, DimensionSpecialEffects.SkyType.NORMAL, false, false) {
				@Override
				public Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight) {
					return color;
				}

				@Override
				public boolean isFoggyAt(int x, int y) {
					return false;
				}
			};
			event.register(ResourceLocation.parse("lucidnight:luciddream"), customEffect);
		}
	}

	@SubscribeEvent
	public static void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
		Entity entity = event.getEntity();
		Level world = entity.level();
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		if (event.getFrom() == ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("lucidnight:luciddream"))) {
			LuciddreamPlayerLeavesDimensionProcedure.execute(world, x, y, z, entity);
		}
		if (event.getTo() == ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("lucidnight:luciddream"))) {
			LuciddreamPlayerEntersDimensionProcedure.execute(world, x, y, z, entity);
		}
	}
}