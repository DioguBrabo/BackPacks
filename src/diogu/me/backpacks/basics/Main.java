package diogu.me.backpacks.basics;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import diogu.me.backpacks.commands.ClickListener;
import diogu.me.backpacks.commands.CreateBackPack;

public class Main extends JavaPlugin{
	private ConfigManager configManager;
	private InventoryManager inventoryManager;
	private static Main instance;
	
	@Override
	public void onEnable() {
		instance=this;
		configManager=new ConfigManager();
		configManager.createConfig();
		inventoryManager=new InventoryManager();
		Bukkit.getPluginManager().registerEvents(new ClickListener(), this);
		getCommand("backpack").setExecutor(new CreateBackPack());
		
	}
	
	public static Main getInstance() {return instance;}
	
	public ConfigManager getConfigManager() {return configManager;}
	
	public InventoryManager getInventoryManager() {return inventoryManager;}

}
