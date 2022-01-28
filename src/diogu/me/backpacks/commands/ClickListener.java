package diogu.me.backpacks.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import diogu.me.backpacks.basics.InventoryManager;
import diogu.me.backpacks.basics.Main;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagString;

public class ClickListener implements Listener {
	private InventoryManager inventoryManager = Main.getInstance().getInventoryManager();

	@EventHandler
	public void onClickItem(PlayerInteractEvent e) {
		Player player1 = e.getPlayer();
		ItemStack hand = player1.getItemInHand();
		if (hand == null)
			return;
		if(hand.getType()==Material.AIR) return;
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(hand);
		NBTTagCompound tag = nmsItem.getTag();
		if (tag == null)
			return;
		String inv = tag.getString("inventory");
		if (inv == null)
			return;
		Inventory inventory = inventoryManager.stringToInventory(inv);
		if (inventory == null)
			return;
		player1.openInventory(inventory);
		Listener invClose=new Listener() {		
			@EventHandler
			public void onCloseInv(InventoryCloseEvent e) {
				Player player2=(Player) e.getPlayer();
				if(player1!=player2) return;
				ItemStack hand=player2.getItemInHand();
				Inventory inv=e.getInventory();
				String strInv=inventoryManager.inventoryToString(inv);
				net.minecraft.server.v1_8_R3.ItemStack nmsItem=CraftItemStack.asNMSCopy(hand);
				NBTTagCompound tags=nmsItem.getTag();
				tags.set("inventory", new NBTTagString(strInv));
				nmsItem.setTag(tags);	
				ItemStack item=CraftItemStack.asBukkitCopy(nmsItem);
				player2.getInventory().setItemInHand(item);
				HandlerList.unregisterAll(this);				
			}
		};
		Bukkit.getPluginManager().registerEvents(invClose, Main.getInstance());
	}
}
