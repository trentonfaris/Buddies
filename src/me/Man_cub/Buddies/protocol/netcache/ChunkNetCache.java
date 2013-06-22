package me.Man_cub.Buddies.protocol.netcache;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import me.Man_cub.Buddies.data.configuration.BuddiesConfig;

import com.google.common.collect.Sets;

public class ChunkNetCache implements Serializable {
	private static final long serialVersionUID = 1L;
	private final AtomicReference<byte[]> partitionCache = new AtomicReference<byte[]>();
	private final Set<Long> hashSet;
	private volatile boolean cacheEnabled = false;

	public ChunkNetCache() {
		this(Sets.newSetFromMap(new ConcurrentHashMap<Long, Boolean>()));
	}

	public ChunkNetCache(Set<Long> hashSet) {
		this.hashSet = hashSet;
	}

	public boolean isCacheEnabled() {
		return cacheEnabled;
	}

	public void handleCustomPacket(String channel, byte[] array) {
		if (BuddiesConfig.USE_CHUNK_CACHE.getBoolean() && channel.equals("ChkCache:setHash")) {
			cacheEnabled = true;
			if (array != null) {
				DataInputStream din = new DataInputStream(new ByteArrayInputStream(array));
				try {
					while (true) {
						long hash = din.readLong();
						this.hashSet.add(hash);
					}
				} catch (IOException ee) {
				}
			}
		}
	}

	public byte[] handle(byte[] inflatedBuffer) {

		byte[] partition = partitionCache.getAndSet(null);

		if (partition == null) {
			partition = new byte[2048];
		}

		if (!cacheEnabled) {
			return inflatedBuffer;
		}

		int dataLength = inflatedBuffer.length;
		int segments = dataLength >> 11;
		if ((dataLength & 0x7FF) != 0) {
			segments++;
		}

		int newLength = dataLength + (segments << 3) + 8 + 4 + 1;

		byte[] newBuffer = new byte[newLength];

		for (int i = 0; i < segments; i++) {
			PartitionChunk.copyFromChunkData(inflatedBuffer, i, partition, inflatedBuffer.length);
			long hash = PartitionChunk.hash(partition);
			if (hashSet.add(hash)) {
				PartitionChunk.copyToChunkData(newBuffer, i, partition, dataLength);
			} else {
				PartitionChunk.setHash(newBuffer, i, hash, dataLength);
			}
		}
		long crc = PartitionChunk.hash(inflatedBuffer);
		PartitionChunk.setHash(newBuffer, 0, crc, newLength - 13);
		PartitionChunk.setInt(newBuffer, 0, dataLength, newLength - 5);

		partitionCache.set(partition);

		return newBuffer;
	}
}
