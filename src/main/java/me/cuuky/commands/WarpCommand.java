package me.cuuky.commands;

import me.cuuky.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class WarpCommand implements CommandExecutor {

    private final Main main;

    public WarpCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        Player p = (Player) sender;
        if (args.length == 1) {
            if (!(this.main.getConfiguration().getString(args[0]) == null)) {
                Location loc = new Location(Bukkit.getWorld(Objects.requireNonNull(this.main.getConfiguration().getString(args[0] + ".world"))),
                        this.main.getConfiguration().getDouble(args[0] + ".x"),
                        this.main.getConfiguration().getDouble(args[0] + ".y"),
                        this.main.getConfiguration().getDouble(args[0] + ".z"));
                loc.setPitch((float) this.main.getConfiguration().getDouble(args[0] + ".yaw"));
                loc.setYaw((float) this.main.getConfiguration().getDouble(args[0] + ".pitch"));

                p.teleport(loc);
                p.sendMessage(Main.getPrefix() + "§7You have been teleportet to the warp §e" + args[0] + "§7!");
            } else p.sendMessage(Main.getPrefix() + "§7Warp §e" + args[0] + " §7bot found!");
        } else p.sendMessage(Main.getPrefix() + "§c/warp <NAME>");
        return false;
    }
}