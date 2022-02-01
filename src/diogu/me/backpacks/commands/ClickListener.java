package diogu.me.backpacks.commands;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import diogu.me.backpacks.basics.InventoryManager;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagString;

public class ClickListener implements Listener {

	/**
	 * 
	 * Open the backpack if the item in the player hand contains the
	 * inventory tag.
	 * 
	 */
	@EventHandler
	public void onClickBackPack(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack hand = player.getItemInHand();
		if (hand == null || hand.getType() == Material.AIR)
			return;
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(hand);
		if (!nmsItem.hasTag() || !(nmsItem.getTag().hasKey("inventory")))
			return;
		String inv = nmsItem.getTag().getString("inventory");
		Inventory inventory = InventoryManager.stringToInventory(inv);
		player.openInventory(inventory);
	}

	/**
	 * Updates the backpack inventory when the player close it.
	 */
	@EventHandler
	public void onUpdateBackPack(InventoryCloseEvent e) {
		Player player = (Player) e.getPlayer();
		Inventory inv = e.getInventory();
		if (!(inv.getName().equalsIgnoreCase("backpack")))
			return;
		String strInv = InventoryManager.inventoryToString(inv);
		List<String> availableSlots = InventoryManager.availableSlots(inv);
		ItemStack hand = player.getItemInHand();
		ItemMeta meta = hand.getItemMeta();
		meta.setLore(availableSlots);
		hand.setItemMeta(meta);
		net.minecraft.server.v1_8_R3.ItemStack nmsHand = CraftItemStack.asNMSCopy(hand);
		NBTTagCompound tags = nmsHand.getTag();
		tags.set("inventory", new NBTTagString(strInv));
		player.setItemInHand(CraftItemStack.asBukkitCopy(nmsHand));
	}
}
