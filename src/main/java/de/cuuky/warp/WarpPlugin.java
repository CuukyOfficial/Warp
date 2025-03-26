package de.cuuky.warp;

import de.cuuky.warp.commands.DelWarpCommand;
import de.cuuky.warp.commands.SetWarpCommand;
import de.cuuky.warp.commands.WarpCommand;
import de.cuuky.warp.commands.WarpListCommand;
import de.cuuky.warp.listener.WarpSignListener;
import de.varoplugin.cfw.configuration.BetterYamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class WarpPlugin extends JavaPlugin {

    private BetterYamlConfiguration configuration;
    private List<Warp> warps;
    private BukkitTask saveTask;

    private void saveWarps() {
        // Clear configuration
        for (String key : this.configuration.getKeys(false)) {
            this.configuration.set(key, null);
        }

        for (Warp warp : this.warps) {
            this.configuration.set(warp.getName(), warp);
        }
        this.configuration.save();
    }

    private void loadWarps() {
        this.configuration = new BetterYamlConfiguration("plugins/Warp/warps.yml");
        for (String key : this.configuration.getKeys(true)) {
            if (this.configuration.isConfigurationSection(key)) {
                this.warps.add((Warp) this.configuration.get(key));
            }
        }
    }

    private void registerBukkit() {
        this.getCommand("setwarp").setExecutor(new SetWarpCommand(this));
        this.getCommand("warp").setExecutor(new WarpCommand(this));
        this.getCommand("delwarp").setExecutor(new DelWarpCommand(this));
        this.getCommand("warplist").setExecutor(new WarpListCommand(this));

        this.getServer().getPluginManager().registerEvents(new WarpSignListener(this), this);
    }

    private void startPeriodicSave() {
        this.saveTask = this.getServer().getScheduler()
                .runTaskTimerAsynchronously(this, this::saveWarps, 20 * 60 * 5, 20 * 60 * 5);
    }

    @Override
    public void onEnable() {
        this.getLogger().info("Warp is activating...");
        this.warps = new ArrayList<>();
        this.loadWarps();
        this.registerBukkit();
        this.startPeriodicSave();
        this.getLogger().info("Warp has been activated!");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Warp is deactivating...");
        this.saveTask.cancel();
        this.saveWarps();
        this.getLogger().info("Warp has been deactivated!");
    }

    public Warp getWarp(String name) {
        return this.warps.stream().filter(warp -> warp.getName().equals(name)).findFirst().orElse(null);
    }

    public boolean addWarp(Warp warp) {
        return this.warps.add(warp);
    }

    public boolean deleteWarp(Warp warp) {
        return this.warps.remove(warp);
    }

    public Stream<Warp> getWarps() {
        return this.warps.stream();
    }
}