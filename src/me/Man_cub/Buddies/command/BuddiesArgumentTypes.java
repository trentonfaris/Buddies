package me.man_cub.buddies.command;

import me.man_cub.buddies.material.BuddiesMaterials;

import org.spout.api.command.CommandArguments;
import org.spout.api.exception.ArgumentParseException;
import org.spout.api.material.Material;
import org.spout.api.material.MaterialRegistry;

public class BuddiesArgumentTypes {
	
	private BuddiesArgumentTypes() {
	}
	
	public static Material popMaterial(String argName, CommandArguments args) throws ArgumentParseException {
		String arg = args.currentArgument(argName);
		Material mat;
		try {
			mat = BuddiesMaterials.getMaterial((short) Integer.parseInt(arg));
		} catch (NumberFormatException ex) {
			mat = MaterialRegistry.get(arg);
		}
		if (mat == null) {
			throw args.failure(argName, "Unknown material: ", false, arg);
		}
		return args.success(argName, mat);
	}

}
