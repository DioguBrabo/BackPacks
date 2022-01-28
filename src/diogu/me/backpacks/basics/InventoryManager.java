package diogu.me.backpacks.basics;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryManager {

	public String inventoryToString(Inventory inv) {
		String strItem = inv.getSize() + ";";
		ItemStack[] items = inv.getContents();
		int x = 0;
		for (ItemStack item : items) {
			x++;
			if (item == null) continue;
			Material material = item.getType();
			short damage=item.getDurability();
			int quantity = item.getAmount();
			strItem = strItem.concat(--x + "#" + material.name() + "," + quantity + ","+damage+";");
		}
		return strItem;
	}

	public Inventory stringToInventory(String str) {
		String[] args = str.split(";");
		int invSize = Integer.parseInt(args[0]);
		Inventory inv = Bukkit.createInventory(null, invSize);
		for (int x = 1; x < args.length; x++) {
			String[] itemInfo = args[x].split("#");
			int slot = Integer.parseInt(itemInfo[0]);
			Material material = Material.valueOf(itemInfo[1].split(",")[0]);
			int quantity = Integer.parseInt(itemInfo[1].split(",")[1]);
			short damage=Short.parseShort(itemInfo[1].split(",")[2]);
			ItemStack item = new ItemStack(material, quantity,damage);
			inv.setItem(slot, item);
		}
		return inv;
	}

}
