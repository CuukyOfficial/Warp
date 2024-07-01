package me.cuuky;

import me.cuuky.commands.DelWarpCommand;
import me.cuuky.commands.SetWarpCommand;
import me.cuuky.commands.WarpCommand;
import me.cuuky.commands.WarpListCommand;
import me.cuuky.listener.PlayerInteractListener;
import me.cuuky.listener.SignChangeListener;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {

	private static final String PREFIX = "§6Warp §8» §7";
	private static final String NO_PERM = PREFIX + "§cYou don't have permission to do that!";

	private File file;
	private YamlConfiguration configuration;

	@Override
	public void onEnable() {
		this.file = new File("plugins/Warp", "warps.yml");
		this.configuration = YamlConfiguration.loadConfiguration(this.file);

		this.registerBukkit();
		getLogger().info("Warp has been activated!");
	}

	private void registerBukkit() {
		this.getCommand("setwarp").setExecutor(new SetWarpCommand(this));
		this.getCommand("warp").setExecutor(new WarpCommand(this));
		this.getCommand("delwarp").setExecutor(new DelWarpCommand(this));
		this.getCommand("warplist").setExecutor(new WarpListCommand(this));

		this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
		this.getServer().getPluginManager().registerEvents(new SignChangeListener(this), this);
	}

	@Override
	public void onDisable() {
		try {
			this.configuration.save(this.file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		getLogger().info("Warp has been deactivated!");
	}

	public YamlConfiguration getConfiguration() {
		return this.configuration;
	}

	public static String getPrefix() {
		return PREFIX;
	}

	public static String getNoPerm() {
		return NO_PERM;
	}
}