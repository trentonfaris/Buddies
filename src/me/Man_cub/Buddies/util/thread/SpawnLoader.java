package me.Man_cub.Buddies.util.thread;

import me.Man_cub.Buddies.BuddiesPlugin;

import org.spout.api.geo.LoadOption;
import org.spout.api.geo.World;
import org.spout.api.math.IntVector3;
import org.spout.api.util.FlatIterator;
import org.spout.api.util.thread.MultiTasker;

public class SpawnLoader extends MultiTasker<IntVector3> {
	private final FlatIterator iter = new FlatIterator();
	private String loadingName;
	private int startAmount;
	private int step;
	private World world;

	public SpawnLoader(int threadCount) {
		super(threadCount);
	}

	@Override
	protected void handle(IntVector3 task, int remaining) {
		if (remaining % this.step == 0) {
			BuddiesPlugin.getInstance().getEngine().getLogger().info(this.loadingName + " [" + this.world.getName() + "], " + (((this.startAmount - remaining) * 100) / this.startAmount) + "% complete");
		}
		this.world.getChunk(task.getX(), task.getY(), task.getZ(), LoadOption.LOAD_GEN);
	}

	public synchronized void load(World world, int cx, int cz, int radius, boolean generate) {
		this.iter.reset(cx, 0, cz, 16, radius);
		this.loadingName = generate ? "Generating" : "Loading";
		this.world = world;
		while (this.iter.hasNext()) {
			addTask(this.iter.next());
		}
		this.startAmount = this.getRemaining();
		this.step = this.startAmount / 10;
		start();
		finish();
		try {
			join();
		} catch (InterruptedException e) {
			BuddiesPlugin.getInstance().getEngine().getLogger().info("Interrupted when waiting for spawn area to load");
		}
	}
}
