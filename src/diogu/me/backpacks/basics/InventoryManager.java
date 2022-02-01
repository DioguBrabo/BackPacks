package diogu.me.backpacks.basics;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

public class InventoryManager {
	private static final String EMPTY="§7Your backpack is §aEMPTY§7!";
	private static final String FULL="§7Your backpack is §cFULL§7!";

	/**
	* Serializes a inventory into a String.
	*/
	public static String inventoryToString(Inventory inv) {
		ItemStack[] items = inv.getContents();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			BukkitObjectOutputStream bos = new BukkitObjectOutputStream(os);
			bos.writeObject(items);
			bos.flush();
		} catch (IOException e) {}
		byte[] serializedItems = os.toByteArray();

		return Base64.getEncoder().encodeToString(serializedItems);
	}

	/**
	* Serializes a String into an Inventory.
	*/
	public static Inventory stringToInventory(String strInv) {
		Inventory inventory = Bukkit.createInventory(null, 27, "BackPack");
		byte[] serializeditems = Base64.getDecoder().decode(strInv);
		ByteArrayInputStream is = new ByteArrayInputStream(serializeditems);
		try {
			BukkitObjectInputStream bis = new BukkitObjectInputStream(is);
			ItemStack[] items = (ItemStack[]) bis.readObject();
			inventory.setContents(items);
		} catch (IOException |ClassNotFoundException e) {}

		return inventory;
	}
	
	/**
	 * This method is used to generate the lore of the backpack.
	 * If the backpack is empty or full, it will be shown as well.
	 * Otherwise, it will show the remaning slots on the backpack.
	 * 
	*/
	public static List<String> availableSlots(Inventory inv) {
		List<String> lore=new ArrayList<>(List.of("",""));
		lore.set(1, "§7slots available to use.");
		String firstLine="§7You still have ";
		int usedSlots=0;
		ItemStack[] items=inv.getContents();
		for(ItemStack item:items) {
			if(item==null) continue;
			usedSlots++;			
		}
		int availableSlots=items.length-usedSlots;
		String fullOrEmpty=availableSlots==0?FULL:availableSlots==items.length?EMPTY:null;
		if(fullOrEmpty!=null) return List.of(fullOrEmpty);
		
		if(availableSlots>18) firstLine+="§a"+availableSlots;
		if(availableSlots>9 && availableSlots<=18) firstLine+="§e"+availableSlots;
		if(availableSlots>0 && availableSlots<=9) firstLine+="§c"+availableSlots;
		if(availableSlots==1) lore.set(1, "§7slot available to use.");
		lore.set(0, firstLine);
		
		return lore;
	
	}
}
