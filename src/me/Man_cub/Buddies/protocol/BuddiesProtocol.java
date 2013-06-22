package me.Man_cub.Buddies.protocol;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.Man_cub.Buddies.BuddiesPlugin;
import me.Man_cub.Buddies.protocol.message.ServerPluginMessage;
import me.Man_cub.Buddies.protocol.message.player.PlayerChatMessage;
import me.Man_cub.Buddies.protocol.message.player.connection.PlayerHandshakeMessage;
import me.Man_cub.Buddies.protocol.message.player.connection.PlayerKickMessage;
import me.Man_cub.Buddies.protocol.netcache.ChunkNetCache;
import me.Man_cub.Buddies.protocol.netcache.protocol.ChunkCacheCodec;
import me.Man_cub.Buddies.protocol.netcache.protocol.ChunkCacheHandler;
import me.Man_cub.Buddies.protocol.plugin.RegisterPluginChannelCodec;
import me.Man_cub.Buddies.protocol.plugin.RegisterPluginChannelMessage;
import me.Man_cub.Buddies.protocol.plugin.RegisterPluginChannelMessageHandler;
import me.Man_cub.Buddies.protocol.plugin.UnregisterPluginChannelCodec;
import me.Man_cub.Buddies.protocol.plugin.UnregisterPluginChannelMessageHandler;

import org.apache.commons.lang3.tuple.Pair;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import org.spout.api.command.Command;
import org.spout.api.command.CommandArguments;
import org.spout.api.exception.UnknownPacketException;
import org.spout.api.map.DefaultedKey;
import org.spout.api.map.DefaultedKeyImpl;
import org.spout.api.protocol.Message;
import org.spout.api.protocol.MessageCodec;
import org.spout.api.protocol.Protocol;
import org.spout.api.protocol.Session;
import org.spout.api.util.Named;

public class BuddiesProtocol extends Protocol {
	public final static DefaultedKey<String> SESSION_ID = new DefaultedKeyImpl<String>("sessionid", "0000000000000000");
	public final static DefaultedKey<String> HANDSHAKE_USERNAME = new DefaultedKeyImpl<String>("handshake_username", "");
	public final static DefaultedKey<Long> LOGIN_TIME = new DefaultedKeyImpl<Long>("handshake_time", -1L);
	public final static DefaultedKey<ChunkNetCache> CHUNK_NET_CACHE = new DefaultedKeyImpl<ChunkNetCache>("chunk_net_cache", (ChunkNetCache) null);
	public static final DefaultedKey<ArrayList<String>> REGISTERED_CUSTOM_PACKETS = new DefaultedKey<ArrayList<String>>() {
		private final List<String> defaultRestricted = Arrays.asList("REGISTER", "UNREGISTER");
		
		public ArrayList<String> getDefaultValue() {
			return new ArrayList<String>(defaultRestricted);
		}
		
		public String getKeyString() {
			return "registeredPluginChannels";
		}
	};
	public static final int DEFAULT_PORT = 25565;
	
	public BuddiesProtocol() {
		super("Buddies", DEFAULT_PORT, new BuddiesCodecLookupService(), new BuddiesHandlerLookupService());
		/* PacketFA wrapped packets */
		registerPacket(RegisterPluginChannelCodec.class, new RegisterPluginChannelMessageHandler());
		registerPacket(UnregisterPluginChannelCodec.class, new UnregisterPluginChannelMessageHandler());
		registerPacket(ChunkCacheCodec.class, new ChunkCacheHandler());
	}
	
	@Override
	public Message getCommandMessage(Command command, CommandArguments args) {
		if (command.getName().equals("kick")) {
			return getKickMessage(args.getJoinedString(0));
		} else if (command.getName().equals("say")) {
			return new PlayerChatMessage(args.getJoinedString(0) + "\u00a7r");
		} else {
			return new PlayerChatMessage("/" + command.getName() + " " + args.getJoinedString(0));
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Message> Message getWrappedMessage(boolean upstream, T dynamicMessage) throws IOException {
		MessageCodec<T> codec = (MessageCodec<T>) getCodecLookupService().find(dynamicMessage.getClass());
		ChannelBuffer buffer = codec.encode(upstream, dynamicMessage);
		return new ServerPluginMessage(getName(codec), buffer.array());
	}

	@Override
	public Message getIntroductionMessage(String playerName, InetSocketAddress addr) {
		return new PlayerHandshakeMessage((byte) BuddiesPlugin.BUDDIES_PROTOCOL_ID, BuddiesPlugin.getInstance().getUsername(), addr.getHostName(), addr.getPort());
	}

	@Override
	public Message getKickMessage(String message) {
		return new PlayerKickMessage(message);
	}
	
	public static MessageCodec<?> getCodec(String name, Protocol activeProtocol) {
		for (Pair<Integer, String> item : activeProtocol.getDynamicallyRegisteredPackets()) {
			MessageCodec<?> codec = activeProtocol.getCodecLookupService().find(item.getLeft());
			if (getName(codec).equalsIgnoreCase(name)) {
				return codec;
			}
		}
		return null;
	}
	
	public static String getName(MessageCodec<?> codec) {
		if (codec instanceof Named) {
			return ((Named) codec).getName();
		} else {
			return "SPOUT:" + codec.getOpcode();
		}
	}

	@Override
	public void initializeSession(Session session) {
		session.setNetworkSynchronizer(new BuddiesNetworkSynchronizer(session));
		
		List<MessageCodec<?>> dynamicCodecList = new ArrayList<MessageCodec<?>>();
		for (Pair<Integer, String> item : getDynamicallyRegisteredPackets()) {
			MessageCodec<?> codec = getCodecLookupService().find(item.getLeft());
			if (codec != null) {
				dynamicCodecList.add(codec);
			} else {
				throw new IllegalStateException("Dynamic pakcet class" + item.getRight() + " claims to be registered but is not in our CodecLookupService!");
			}
		}
		session.send(false, new RegisterPluginChannelMessage(dynamicCodecList));
	}

	@Override
	public MessageCodec<?> readHeader(ChannelBuffer buf) throws UnknownPacketException {
		int opcode = buf.readUnsignedByte();
		MessageCodec<?> codec = getCodecLookupService().find(opcode);
		if (codec == null) {
			throw new UnknownPacketException(opcode);
		}
		return codec;
	}

	@Override
	public ChannelBuffer writeHeader(MessageCodec<?> codec, ChannelBuffer data) {
		ChannelBuffer buffer = ChannelBuffers.buffer(1);
		buffer.writeByte(codec.getOpcode());
		return buffer;
	}
	
	
}
