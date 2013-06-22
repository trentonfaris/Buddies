package me.Man_cub.Buddies.protocol.netcache;

public class PartitionChunk {
	static public void copyToChunkData(byte[] chunkData, int blockNum, byte[] partition, int dataLength) {

		int j = blockNum << 11;

		boolean clear = partition == null;

		if (clear) {
			for (int i = 0; i < 2048 && j < dataLength; i++) {
				chunkData[j++] = 0;
			}
		} else {
			for (int i = 0; i < 2048 && j < dataLength; i++) {
				chunkData[j++] = partition[i];
			}
		}
	}

	static public void copyFromChunkData(byte[] chunkData, int blockNum, byte[] partition, int dataLength) {

		int j = blockNum << 11;

		int i = 0;
		for (i = 0; i < 2048 && j < dataLength; i++) {
			partition[i] = chunkData[j++];
		}
		for (; i < 2048; i++) {
			partition[i] = 0;
		}
	}

	static public long getHash(byte[] chunkData, int blockNum, int base) {
		int p = blockNum * 8 + base;
		long hash = 0;
		hash = hash << 8 | (((long) chunkData[p++]) & 0xFFL);
		hash = hash << 8 | (((long) chunkData[p++]) & 0xFFL);
		hash = hash << 8 | (((long) chunkData[p++]) & 0xFFL);
		hash = hash << 8 | (((long) chunkData[p++]) & 0xFFL);
		hash = hash << 8 | (((long) chunkData[p++]) & 0xFFL);
		hash = hash << 8 | (((long) chunkData[p++]) & 0xFFL);
		hash = hash << 8 | (((long) chunkData[p++]) & 0xFFL);
		hash = hash << 8 | (((long) chunkData[p++]) & 0xFFL);
		return hash;
	}

	static public void setHash(byte[] chunkData, int blockNum, long hash, int base) {
		int p = blockNum * 8 + base;
		chunkData[p++] = (byte) (hash >> 56);
		chunkData[p++] = (byte) (hash >> 48);
		chunkData[p++] = (byte) (hash >> 40);
		chunkData[p++] = (byte) (hash >> 32);
		chunkData[p++] = (byte) (hash >> 24);
		chunkData[p++] = (byte) (hash >> 16);
		chunkData[p++] = (byte) (hash >> 8);
		chunkData[p++] = (byte) (hash >> 0);
	}

	static public int getInt(byte[] chunkData, int blockNum, int base) {
		int p = blockNum * 4 + base;
		int hash = 0;
		hash = hash << 8 | (((int) chunkData[p++]) & 0xFF);
		hash = hash << 8 | (((int) chunkData[p++]) & 0xFF);
		hash = hash << 8 | (((int) chunkData[p++]) & 0xFF);
		hash = hash << 8 | (((int) chunkData[p++]) & 0xFF);
		return hash;
	}

	static public void setInt(byte[] chunkData, int blockNum, int hash, int base) {
		int p = blockNum * 4 + base;
		chunkData[p++] = (byte) (hash >> 24);
		chunkData[p++] = (byte) (hash >> 16);
		chunkData[p++] = (byte) (hash >> 8);
		chunkData[p++] = (byte) (hash >> 0);
	}

	public static long hash(final byte[] a) {
		return hash(a, 0, a.length);
	}

	public static long hash(final byte[] a, final int off, final int len) {
		long h = 1;
		int end = off + len;
		for (int i = off; i < end; i++) {
			h += (h << 5) + (long) a[i];
		}
		return h;
	}
}
