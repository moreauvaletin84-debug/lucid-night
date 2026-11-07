package net.mcreator.lucidnight.procedures;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

public class WakeupOnKeyPressedProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double spawnX = 0;
		double spawnY = 0;
		double spawnZ = 0;
		if (!((entity.level().dimension()) == Level.OVERWORLD && (entity.level().dimension()) == Level.NETHER && (entity.level().dimension()) == Level.END
				&& (entity.level().dimension()) == ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("lucidnight:nightmare")))) {
			if (entity instanceof ServerPlayer _player && !_player.level().isClientSide()) {
				ResourceKey<Level> destinationType = Level.OVERWORLD;
				if (_player.level().dimension() == destinationType)
					return;
				ServerLevel nextLevel = _player.server.getLevel(destinationType);
				if (nextLevel != null) {
					_player.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.WIN_GAME, 0));
					_player.teleportTo(nextLevel, _player.getX(), _player.getY(), _player.getZ(), _player.getYRot(), _player.getXRot());
					_player.connection.send(new ClientboundPlayerAbilitiesPacket(_player.getAbilities()));
					for (MobEffectInstance _effectinstance : _player.getActiveEffects())
						_player.connection.send(new ClientboundUpdateMobEffectPacket(_player.getId(), _effectinstance, false));
					_player.connection.send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
				}
			}
			if (entity instanceof net.minecraft.server.level.ServerPlayer _player) {
				net.minecraft.core.BlockPos _spawnPos = _player.getRespawnPosition();
				if (_spawnPos != null) {
					spawnX = _spawnPos.getX();
					spawnY = _spawnPos.getY();
					spawnZ = _spawnPos.getZ();
				}
			}
			{
				Entity _ent = entity;
				double _tx = spawnX;
				double _ty = spawnY;
				double _tz = spawnZ;
				_ent.teleportTo(_tx, _ty, _tz);
				if (_ent instanceof ServerPlayer _serverPlayer)
					_serverPlayer.connection.teleport(_tx, _ty, _tz, _ent.getYRot(), _ent.getXRot());
			}
		}
	}
}