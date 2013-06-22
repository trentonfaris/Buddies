package me.Man_cub.Buddies.protocol.message.entity;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.spout.api.util.Parameter;
import org.spout.api.util.SpoutToStringStyle;

// TODO: REDO this whole class. There are elements transferred over from Vanilla that I don't use.
public final class EntityMetadataMessage extends EntityMessage {
	public static enum Parameters {
		//Entity flags
		META_INFLAMED(0, (byte) 0x01),
		META_CROUCHED(0, (byte) 0x02),
		META_MOBRIDER(0, (byte) 0x04),
		META_SPRINTING(0, (byte) 0x08),
		META_RIGHTCLICKACTION(0, (byte) 0x10),
		META_INVISIBLE(0, (byte) 0x20),
		/**
		 * Drowning counter for resources.entities. This should be sent when an entity spawns and be decremented
		 * per tick. If the value hits -19, send a 0x26 and reset this to 0
		 */
		META_FULLDROWNINGCOUNTER(1, (short) 300),
		META_DROWNINGCOUNTEDDEPLETED(1, (short) -19),
		META_STARTDROWNING(1, (short) 0),
		/**
		 * Set whether to show or hide the name of the entity on the nameplate.
		 */
		META_HIDENAME(6, (byte) 0),
		META_SHOWNAME(6, (byte) 1),
		/**
		 * Potion color for effect. The value is composed of RRGGBB (in that order).
		 */
		META_NOPOTIONEFFECT(8, 000000),
		/**
		 * Animal growth and control. -23999 is the value for babies, 6000 is the value for adults. When set
		 * to 6000, decrease over time (to determine when to breed again). Value of 0 means the adult can breed.
		 */
		META_BABYANIMALSTAGE(12, -23999),
		META_PARENTANIMALSTAGE(12, 6000),
		META_BREEDANIMALSTAGE(12, 0);
		private final Parameter<?> parameter;

		private Parameters(int index, int value) {
			this.parameter = new Parameter<Integer>(Parameter.TYPE_INT, index, value);
		}

		private Parameters(int index, short value) {
			this.parameter = new Parameter<Short>(Parameter.TYPE_SHORT, index, value);
		}

		private Parameters(int index, byte value) {
			this.parameter = new Parameter<Byte>(Parameter.TYPE_BYTE, index, value);
		}

		public Parameter<?> get() {
			return this.parameter;
		}
	}

	private final List<Parameter<?>> parameters;

	public EntityMetadataMessage(int id, List<Parameter<?>> parameters) {
		super(id);
		this.parameters = parameters;
	}

	public List<Parameter<?>> getParameters() {
		return parameters;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, SpoutToStringStyle.INSTANCE)
				.append("id", this.getEntityId())
				.append("parameters", parameters, true)
				.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final EntityMetadataMessage other = (EntityMetadataMessage) obj;
		return new EqualsBuilder()
				.append(this.getEntityId(), other.getEntityId())
				.append(this.parameters, other.parameters)
				.isEquals();
	}

}
