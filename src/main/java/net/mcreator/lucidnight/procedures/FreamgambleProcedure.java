package net.mcreator.lucidnight.procedures;

import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

@EventBusSubscriber
public class FreamgambleProcedure {
	@SubscribeEvent
	public static void onPlayerInBed(CanPlayerSleepEvent event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		double dreamgamble = 0;
		dreamgamble = Mth.nextInt(RandomSource.create(), 1, 100);
		if (dreamgamble < 11) {
			if (entity instanceof ServerPlayer _player)
				_player.sendSystemMessage(Component.literal("you are having a nightmare"), true);
		} else {
			if (entity instanceof ServerPlayer _player)
				_player.sendSystemMessage(Component.literal("you are having a lucid dream"), true);
		}
	}
}