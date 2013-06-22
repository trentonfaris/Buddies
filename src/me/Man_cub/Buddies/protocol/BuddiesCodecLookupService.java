package me.Man_cub.Buddies.protocol;

import me.Man_cub.Buddies.protocol.codec.entity.EntityActionCodec;
import me.Man_cub.Buddies.protocol.codec.entity.EntityAnimationCodec;
import me.Man_cub.Buddies.protocol.codec.entity.EntityAttachEntityCodec;
import me.Man_cub.Buddies.protocol.codec.entity.EntityDestroyCodec;
import me.Man_cub.Buddies.protocol.codec.entity.EntityEquipmentCodec;
import me.Man_cub.Buddies.protocol.codec.entity.EntityInitializeCodec;
import me.Man_cub.Buddies.protocol.codec.entity.EntityItemDataCodec;
import me.Man_cub.Buddies.protocol.codec.entity.EntityMetadataCodec;
import me.Man_cub.Buddies.protocol.codec.entity.EntityRelativePositionCodec;
import me.Man_cub.Buddies.protocol.codec.entity.EntityStatusCodec;
import me.Man_cub.Buddies.protocol.codec.entity.EntityTileDataCodec;
import me.Man_cub.Buddies.protocol.codec.entity.position.EntityBodyYawCodec;
import me.Man_cub.Buddies.protocol.codec.entity.position.EntityRelativePositionYawCodec;
import me.Man_cub.Buddies.protocol.codec.entity.position.EntityTeleportCodec;
import me.Man_cub.Buddies.protocol.codec.entity.position.EntityVelocityCodec;
import me.Man_cub.Buddies.protocol.codec.entity.position.EntityYawCodec;
import me.Man_cub.Buddies.protocol.codec.entity.spawn.EntityMobCodec;
import me.Man_cub.Buddies.protocol.codec.entity.spawn.EntitySpawnObjectCodec;
import me.Man_cub.Buddies.protocol.codec.entity.spawn.EntityThunderboltCodec;
import me.Man_cub.Buddies.protocol.codec.player.PlayerAbilityCodec;
import me.Man_cub.Buddies.protocol.codec.player.PlayerBlockPlacementCodec;
import me.Man_cub.Buddies.protocol.codec.player.PlayerChatCodec;
import me.Man_cub.Buddies.protocol.codec.player.PlayerCollectItemCodec;
import me.Man_cub.Buddies.protocol.codec.player.PlayerGroundCodec;
import me.Man_cub.Buddies.protocol.codec.player.PlayerHealthCodec;
import me.Man_cub.Buddies.protocol.codec.player.PlayerHeldItemChangeCodec;
import me.Man_cub.Buddies.protocol.codec.player.PlayerStatisticCodec;
import me.Man_cub.Buddies.protocol.codec.player.PlayerStatusCodec;
import me.Man_cub.Buddies.protocol.codec.player.PlayerTabCompleteCodec;
import me.Man_cub.Buddies.protocol.codec.player.PlayerTimeCodec;
import me.Man_cub.Buddies.protocol.codec.player.PlayerUseEntityCodec;
import me.Man_cub.Buddies.protocol.codec.player.connection.PlayerHandshakeCodec;
import me.Man_cub.Buddies.protocol.codec.player.connection.PlayerKickCodec;
import me.Man_cub.Buddies.protocol.codec.player.connection.PlayerListCodec;
import me.Man_cub.Buddies.protocol.codec.player.connection.PlayerPingCodec;
import me.Man_cub.Buddies.protocol.codec.player.position.PlayerLookCodec;
import me.Man_cub.Buddies.protocol.codec.player.position.PlayerPositionCodec;
import me.Man_cub.Buddies.protocol.codec.player.position.PlayerPositionLookCodec;
import me.Man_cub.Buddies.protocol.codec.player.position.PlayerRespawnCodec;
import me.Man_cub.Buddies.protocol.codec.player.position.PlayerSpawnCodec;
import me.Man_cub.Buddies.protocol.codec.player.position.PlayerSpawnPositionCodec;
import me.Man_cub.Buddies.protocol.codec.scoreboard.ScoreboardDisplayCodec;
import me.Man_cub.Buddies.protocol.codec.scoreboard.ScoreboardObjectiveCodec;
import me.Man_cub.Buddies.protocol.codec.scoreboard.ScoreboardScoreCodec;
import me.Man_cub.Buddies.protocol.codec.scoreboard.ScoreboardTeamCodec;
import me.Man_cub.Buddies.protocol.codec.server.ServerListPingCodec;
import me.Man_cub.Buddies.protocol.codec.server.ServerPluginCodec;
import me.Man_cub.Buddies.protocol.codec.world.ExplosionCodec;
import me.Man_cub.Buddies.protocol.codec.world.ParticleEffectCodec;
import me.Man_cub.Buddies.protocol.codec.world.SoundEffectCodec;
import me.Man_cub.Buddies.protocol.codec.world.block.BlockActionCodec;
import me.Man_cub.Buddies.protocol.codec.world.block.BlockBreakAnimationCodec;
import me.Man_cub.Buddies.protocol.codec.world.block.BlockBulkCodec;
import me.Man_cub.Buddies.protocol.codec.world.block.BlockChangeCodec;
import me.Man_cub.Buddies.protocol.codec.world.chunk.ChunkBulkCodec;
import me.Man_cub.Buddies.protocol.codec.world.chunk.ChunkDataCodec;

import org.spout.api.protocol.CodecLookupService;

public class BuddiesCodecLookupService extends CodecLookupService {
	public BuddiesCodecLookupService() {
		super(512);
		try {
			/* 0x00 */
			bind(PlayerPingCodec.class);
			/* 0x01 */
			//bind(PlayerLoginRequestCodec.class); TODO : Authentication
			/* 0x02 */
			bind(PlayerHandshakeCodec.class);
			/* 0x03 */
			bind(PlayerChatCodec.class);
			/* 0x04 */
			bind(PlayerTimeCodec.class);
			/* 0x05 */
			bind(EntityEquipmentCodec.class);
			/* 0x06 */
			bind(PlayerSpawnPositionCodec.class);
			/* 0x07 */
			bind(PlayerUseEntityCodec.class);
			/* 0x08 */
			bind(PlayerHealthCodec.class);
			/* 0x09 */
			bind(PlayerRespawnCodec.class);
			/* 0x0A */
			bind(PlayerGroundCodec.class);
			/* 0x0B */
			bind(PlayerPositionCodec.class);
			/* 0x0C */
			bind(PlayerLookCodec.class);
			/* 0x0D */
			bind(PlayerPositionLookCodec.class);
			/* 0x0E */
			//bind(PlayerDiggingCodec.class); TODO : Might implement this
			/* 0x0F */
			bind(PlayerBlockPlacementCodec.class);
			/* 0x10 */
			bind(PlayerHeldItemChangeCodec.class);
			/* 0x12 */
			bind(EntityAnimationCodec.class);
			/* 0x13 */
			bind(EntityActionCodec.class);
			/* 0x14 */
			bind(PlayerSpawnCodec.class);
			/* 0x15 */
			//bind(EntityItemCodec.class); // Removed as of 1.4.6
			/* 0x16 */
			bind(PlayerCollectItemCodec.class);
			/* 0x17 */
			bind(EntitySpawnObjectCodec.class);
			/* 0x18 */
			bind(EntityMobCodec.class);
			/* 0x1C */
			bind(EntityVelocityCodec.class);
			/* 0x1D */
			bind(EntityDestroyCodec.class);
			/* 0x1E */
			bind(EntityInitializeCodec.class); //TODO the meaning of this packet is basically that the entity did not move/look since the last such packet. We need to implement this!
			/* 0x1F */
			bind(EntityRelativePositionCodec.class);
			/* 0x20 */
			bind(EntityYawCodec.class); //TODO rename Entity Look on the minecraft protocol page
			/* 0x21 */
			bind(EntityRelativePositionYawCodec.class);  //TODO same as above
			/* 0x22 */
			bind(EntityTeleportCodec.class);
			/* 0x23 */
			bind(EntityBodyYawCodec.class); //TODO same as above
			/* 0x26 */
			bind(EntityStatusCodec.class);
			/* 0x27 */
			bind(EntityAttachEntityCodec.class);
			/* 0x28 */
			bind(EntityMetadataCodec.class);
			/* 0x29 */
			//bind(EntityEffectCodec.class); TODO : Effects haven't been done
			/* 0x2A */
			//bind(EntityRemoveEffectCodec.class); TODO: As above
			/* 0x33 */
			bind(ChunkDataCodec.class); //TODO rename on the minecraft protocol page
			/* 0x34 */
			bind(BlockBulkCodec.class);
			/* 0x35 */
			bind(BlockChangeCodec.class);
			/* 0x36 */
			bind(BlockActionCodec.class);
			/* 0x37 */
			bind(BlockBreakAnimationCodec.class);
			/* 0x38 */
			bind(ChunkBulkCodec.class);
			/* 0x3C */
			bind(ExplosionCodec.class);
			/* 0x3D */
			//bind(EffectCodec.class); TODO: Effects haven't been done
			/* 0x3E */
			bind(SoundEffectCodec.class);
			/* 0x3F */
			bind(ParticleEffectCodec.class);
			/* 0x47 */
			bind(EntityThunderboltCodec.class); //Minecraft protocol page -> Thunderbolt :/
			/* 0x83 */
			bind(EntityItemDataCodec.class);
			/* 0x84 */
			bind(EntityTileDataCodec.class); //Update Tile Entity...
			/* 0xC8 */
			bind(PlayerStatisticCodec.class);
			/* 0xC9 */
			bind(PlayerListCodec.class);
			/* 0xCA */
			bind(PlayerAbilityCodec.class);
			/* 0xCB */
			bind(PlayerTabCompleteCodec.class);
			/* 0xCD */
			bind(PlayerStatusCodec.class);
			/* 0xCE */
			bind(ScoreboardObjectiveCodec.class);
			/* 0xCF */
			bind(ScoreboardScoreCodec.class);
			/* 0xD0 */
			bind(ScoreboardDisplayCodec.class);
			/* 0xD1 */
			bind(ScoreboardTeamCodec.class);
			/* 0xFA */
			bind(ServerPluginCodec.class);
			/* 0xFC */
			//bind(EncryptionKeyResponseCodec.class); TODO: Authentication
			/* 0xFD */
			//bind(EncryptionKeyRequestCodec.class); TODO : As above
			/* 0xFE */
			bind(ServerListPingCodec.class);
			/* 0xFF */
			bind(PlayerKickCodec.class);
		} catch (Exception ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

}
