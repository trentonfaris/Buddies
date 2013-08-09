package me.man_cub.buddies.data;

import me.man_cub.buddies.BuddiesPlugin;

import org.spout.api.Platform;
import org.spout.api.render.Font;
import org.spout.api.render.RenderMaterial;

public final class BuddiesResources {
	public static final Font FONT;
	public static final RenderMaterial LOBBY_MAT;
	public static final RenderMaterial ITEM_MAT;
	public static final RenderMaterial ICONS_MAT;
	public static final RenderMaterial PROJ_MAT;
	static {
		if (BuddiesPlugin.getInstance().getEngine().getPlatform() == Platform.CLIENT) {
			FONT = BuddiesPlugin.getInstance().getEngine().getFileSystem().getResource("font://Buddies/resources/font/BFont.ttf");
			LOBBY_MAT = BuddiesPlugin.getInstance().getEngine().getFileSystem().getResource("material://Buddies/resources/gui/smt/LobbyGUIMaterial.smt");
			ITEM_MAT = null/*BuddiesPlugin.getInstance().getEngine().getFilesystem().getResource("material://Buddies/resources/gui/smt/ItemMaterial.smt")*/;
			ICONS_MAT = BuddiesPlugin.getInstance().getEngine().getFileSystem().getResource("material://Buddies/resources/gui/smt/IconsGUIMaterial.smt");
			PROJ_MAT = null;
		} else {
			FONT = null;
			LOBBY_MAT = null;
			ITEM_MAT = null;
			ICONS_MAT = null;
			PROJ_MAT = null;
		}
	}
}
