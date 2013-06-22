package me.Man_cub.Buddies.component.entity;

import me.Man_cub.Buddies.data.BuddiesData;

import org.spout.api.component.entity.EntityComponent;
import org.spout.api.util.Parameter;

public class BuddiesEntityComponent extends EntityComponent {
	
	@Override
	public void onAttached() {
		//Tracks the number of times this component has been attached (i.e how many times it's been saved, then loaded. 1 = fresh entity)
		getOwner().getDatatable().put(BuddiesData.ATTACHED_COUNT, getAttachedCount() + 1);
		getOwner().setSavable(true);
	}
	
	protected void setMetadata(Parameter<?>... p) {
		//getOwner().getNetwork.callProtocolEvent(new EntityMetaChangeEvent(getOwner(), Arrays.asList(p)));
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
		return getOwner().getDatatable().get(BuddiesData.ATTACHED_COUNT);
	}
	
}
