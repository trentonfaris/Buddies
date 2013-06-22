package me.Man_cub.Buddies.protocol.reposition;

import org.spout.api.protocol.reposition.RepositionManager;
import org.spout.api.protocol.reposition.RepositionManagerImpl;

public class BuddiesRepositionManager extends RepositionManagerImpl {
	private final BuddiesRepositionManager inverse;
	private volatile int offset = 0;

	public BuddiesRepositionManager() {
		this(0);
	}

	public BuddiesRepositionManager(int offset) {
		this.inverse = new BuddiesRepositionManager(-offset, this);
		this.offset = offset;
	}

	private BuddiesRepositionManager(int offset, BuddiesRepositionManager inverse) {
		this.inverse = inverse;
		this.offset = offset;
	}

	@Override
	public RepositionManager getInverse() {
		return inverse;
	}

	@Override
	public double convertX(double x) {
		return x;
	}

	@Override
	public double convertY(double y) {
		return y + offset;
	}

	@Override
	public double convertZ(double z) {
		return z;
	}

	public void setOffset(int offset) {
		this.offset = offset;
		inverse.offset = -offset;
	}

}
