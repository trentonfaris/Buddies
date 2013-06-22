package me.Man_cub.Buddies.protocol.codec.player;

import java.io.IOException;

import me.Man_cub.Buddies.protocol.message.player.PlayerAbilityMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;
import org.spout.api.util.LogicUtil;

public class PlayerAbilityCodec extends MessageCodec<PlayerAbilityMessage> {
	
	public PlayerAbilityCodec() {
		super(PlayerAbilityMessage.class, 0xCA);
	}

	@Override
	public ChannelBuffer encode(PlayerAbilityMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		byte flag = 0;
		flag = LogicUtil.setBit(flag, 0x1, message.isGodMode());
		flag = LogicUtil.setBit(flag, 0x2, message.isFlying());
		flag = LogicUtil.setBit(flag, 0x4, message.canFly());
		flag = LogicUtil.setBit(flag, 0x8, message.isCreativeMode());
		buffer.writeByte(flag);
		buffer.writeByte(message.getFlyingSpeed());
		buffer.writeByte(message.getWalkingSpeed());
		return buffer;
	}

	@Override
	public PlayerAbilityMessage decode(ChannelBuffer buffer) throws IOException {
		byte flag = buffer.readByte();
		boolean godMode = LogicUtil.getBit(flag, 0x1);
		boolean isFlying = LogicUtil.getBit(flag, 0x2);
		boolean canFly = LogicUtil.getBit(flag, 0x4);
		boolean creativeMode = LogicUtil.getBit(flag, 0x8);
		byte flyingSpeed = buffer.readByte();
		byte walkingSpeed = buffer.readByte();
		return new PlayerAbilityMessage(godMode, isFlying, canFly, creativeMode, flyingSpeed, walkingSpeed);
	}

}
