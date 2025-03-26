package de.cuuky.warp.commands;

import de.cuuky.warp.Warp;
import de.cuuky.warp.WarpPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {

    private final WarpPlugin warpPlugin;

    public WarpCommand(WarpPlugin warpPlugin) {
        this.warpPlugin = warpPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Not for Console!");
            return false;
        }

        Player player = (Player) sender;
        if (args.length != 1) {
            player.sendMessage("§c/warp <NAME>");
            return false;
        }

        Warp warp = this.warpPlugin.getWarp(args[0]);
        if (warp == null) {
            player.sendMessage("§7Warp §e" + args[0] + " §7not found!");
            return false;
        }

        player.teleport(warp.getLocation());
        player.sendMessage("§7You have been teleportet to the warp §e" + args[0] + "§7!");
        return true;
    }
}