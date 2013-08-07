package me.Man_cub.Buddies.component.entity;

import java.util.Arrays;
import java.util.HashMap;

import me.Man_cub.Buddies.data.BuddiesData;
import me.Man_cub.Buddies.event.entity.network.EntityMetaChangeEvent;

import org.spout.api.component.entity.EntityComponent;
import org.spout.api.entity.Player;
import org.spout.api.util.Parameter;

public class BuddiesEntityComponent extends EntityComponent {
	
	@Override
	@SuppressWarnings("unchecked")
	public void onAttached() {
		HashMap<Class<? extends BuddiesEntityComponent>, Integer> map = getOwner().getData().get(BuddiesData.ATTACHED_COUNT);
		Integer count = map.get(getClass());
		if (count == null) {
			count = 0;
		}
		count++;
		map.put(getClass(), count);
		getOwner().getData().put(BuddiesData.ATTACHED_COUNT, map);
		getOwner().setSavable(true);
		
		//Players initialized in intializeSession
		if (!(getOwner() instanceof Player)) {
			getOwner().add(BuddiesEntityComponent.class);
		}
	}
	
	protected void setMetadata(Parameter<?>... p) {
		getOwner().getNetwork().callProtocolEvent(new EntityMetaChangeEvent(getOwner(), Arrays.asList(p)));
	}

	/**
	 * A counter of how many times this component has been attached to an entity
	 * <p/>
	 * Values > 1 indicate how many times this component has been saved to disk,
	 * and reloaded
	 * <p/>
	 * Values == 1 indicate a new component that has never been saved and loaded.
	 * @return attached count
	 */
	
	public final int getAttachedCount() {
		return (Integer) getOwner().getData().get(BuddiesData.ATTACHED_COUNT).get(getClass());
	}
	
}
