package me.Man_cub.Buddies.util;

import java.awt.Color;

import org.spout.cereal.config.serialization.GenericType;
import org.spout.cereal.config.serialization.Serializer;

public class ColorSerializer extends Serializer {
	@Override
	protected Object handleSerialize(GenericType type, Object value) {
		final Color color = (Color) value;
		return "rgb=" + color.getRed() + "," + color.getGreen() + "," + color.getBlue();
	}

	@Override
	protected Object handleDeserialize(GenericType type, Object value) {
		final String[] string = ((String) value).split(",");
		return new Color(Integer.parseInt(string[0].substring(4)), Integer.parseInt(string[1]), Integer.parseInt(string[2]));
	}

	@Override
	public boolean isApplicable(GenericType type) {
		return Color.class.isAssignableFrom(type.getMainType());
	}

	@Override
	protected int getParametersRequired() {
		return 0;
	}
}
