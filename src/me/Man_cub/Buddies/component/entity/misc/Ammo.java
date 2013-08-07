package me.Man_cub.Buddies.component.entity.misc;

import java.awt.Color;

import me.Man_cub.Buddies.BuddiesPlugin;
import me.Man_cub.Buddies.component.entity.BuddiesEntityComponent;
import me.Man_cub.Buddies.component.entity.inventory.BuddyInventory;
import me.Man_cub.Buddies.component.entity.player.HUD;
import me.Man_cub.Buddies.data.BuddiesResources;
import me.Man_cub.Buddies.material.block.crate.Crate;
import me.Man_cub.Buddies.material.block.crate.MegaCrate;
import me.Man_cub.Buddies.material.item.RangedWeapon;

import org.spout.api.Client;
import org.spout.api.component.widget.LabelComponent;
import org.spout.api.component.widget.RenderPartPacksComponent;
import org.spout.api.entity.Player;
import org.spout.api.gui.Widget;
import org.spout.api.gui.render.RenderPart;
import org.spout.api.gui.render.RenderPartPack;
import org.spout.api.inventory.ItemStack;
import org.spout.api.math.Rectangle;
import org.spout.api.Platform;

public class Ammo extends BuddiesEntityComponent {
	BuddiesPlugin plugin = BuddiesPlugin.getInstance();
	private Widget ammo;
	private Widget ammoAmount;
	
	public Ammo() {
		if (plugin.getEngine().getPlatform() == Platform.CLIENT) {
			ammo = ((Client) plugin.getEngine()).getScreenStack().createWidget();
			ammoAmount = ((Client) plugin.getEngine()).getScreenStack().createWidget();
		}
	}
	
	@Override
	public boolean canTick() {
		return true;
	}
	
	@Override
	public void onAttached() {
		if (plugin.getEngine() instanceof Client && getOwner() instanceof Player) {
			final RenderPartPacksComponent ammoRect = ammo.add(RenderPartPacksComponent.class);
			final RenderPartPack ammo_pack = new RenderPartPack(BuddiesResources.ICONS_MAT);
			final RenderPart ammoPart = new RenderPart();
			ammoPart.setColor(Color.WHITE);
			ammoPart.setSprite(new Rectangle(0.7f, -0.95f, 0.25f, 0.25f));
			ammoPart.setSource(new Rectangle(16f / 256f, 0f / 256f, 16f / 256f, 16f / 256f));
			ammo_pack.add(ammoPart);
			ammoRect.add(ammo_pack);
			
			ammoAmount.getTransform().setPosition(0.79375f, -0.8875f);
			ammoAmount.getTransform().setScale(2.5f);
			LabelComponent ammoMessage = ammoAmount.add(LabelComponent.class);
			ammoMessage.setFont(BuddiesResources.FONT);
			
			getOwner().get(HUD.class).attachWidget(ammo);
			getOwner().get(HUD.class).attachWidget(ammoAmount);
		}
	}
	
	/*
	@Override
	public void onTick(float dt) {
		ammoAmount.get(LabelComponent.class).setText(ChatStyle.WHITE + getAmmoValue());
	}*/
	
	public String getAmmoValue() {
		BuddyInventory inv = getOwner().get(BuddyInventory.class);
		ItemStack itemInHand = inv.getHeldItem();
		if (itemInHand != null && (itemInHand.getMaterial() instanceof Crate) || (itemInHand.getMaterial() instanceof MegaCrate)) {
			ItemStack secondary = inv.getInv().get(1);
			if (secondary != null && secondary.getMaterial() instanceof RangedWeapon) {
				return new Integer(((RangedWeapon) secondary.getMaterial()).getAmmo(getOwner())).toString() + "/" + (new Integer(((RangedWeapon) secondary.getMaterial()).getMaxAmmo()).toString());
			}
		} else if (itemInHand != null && itemInHand.getMaterial() instanceof RangedWeapon) {
			return new Integer(((RangedWeapon) itemInHand.getMaterial()).getAmmo(getOwner())).toString() + "/" + (new Integer(((RangedWeapon) itemInHand.getMaterial()).getMaxAmmo()).toString());
		}
		return "-";
	}

}
