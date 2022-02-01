package diogu.me.backpacks.basics;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import diogu.me.backpacks.commands.ClickListener;
import diogu.me.backpacks.commands.CreateBackPack;

public class Plugin extends JavaPlugin{
	private static Plugin instance;
	
	@Override
	public void onEnable() {
		instance=this;
		Bukkit.getPluginManager().registerEvents(new ClickListener(), this);
		getCommand("backpack").setExecutor(new CreateBackPack());	
	}
	
	public static Plugin getInstance() {return instance;}
	

}
