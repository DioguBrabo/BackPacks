package diogu.me.backpacks.basics;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
	private FileConfiguration config;
	private Main m = Main.getInstance();

	public void createConfig() {
		if (!(m.getDataFolder().exists())) {
			m.getDataFolder().mkdirs();
			m.saveConfig();
		}
		config = m.getConfig();
	}

	public FileConfiguration getConfig() {
		return config;
	}

}
