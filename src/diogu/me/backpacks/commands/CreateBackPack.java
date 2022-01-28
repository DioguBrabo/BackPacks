package diogu.me.backpacks.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagString;

public class CreateBackPack implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player player = (Player) sender;
		if (command.getName().equalsIgnoreCase("backpack")) {
			ItemStack backPack;
			backPack = new ItemStack(Material.SKULL_ITEM,1,(short)3);
			SkullMeta meta=(SkullMeta) backPack.getItemMeta();
			meta.setDisplayName("§aMochila");
			meta.setOwner("MHF_Chest");
			backPack.setItemMeta(meta);
			String strInv="27";
			net.minecraft.server.v1_8_R3.ItemStack nmsItem=CraftItemStack.asNMSCopy(backPack);
			NBTTagCompound tags=nmsItem.hasTag()?nmsItem.getTag():new NBTTagCompound();
			tags.set("inventory", new NBTTagString(strInv));
			nmsItem.setTag(tags);
			player.sendMessage("§aMochila criada com sucesso!");		
			backPack=CraftItemStack.asBukkitCopy(nmsItem);
			player.getInventory().addItem(backPack);
		}
		return false;
	}

}
