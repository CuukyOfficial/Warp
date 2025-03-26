package de.cuuky.warp.commands;

import de.cuuky.warp.Warp;
import de.cuuky.warp.WarpPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DelWarpCommand implements CommandExecutor {

    private final WarpPlugin warpPlugin;

    public DelWarpCommand(WarpPlugin warpPlugin) {
        this.warpPlugin = warpPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("§c/delwarp <WARP>");
            return false;
        }

        Warp warp = this.warpPlugin.getWarp(args[0]);
        if (warp == null) {
            sender.sendMessage("§7Warp §e" + args[0] + " §7not found!");
            return false;
        }

        if (!this.warpPlugin.deleteWarp(warp)) {
            sender.sendMessage("§7Warp §e" + args[0] + " §7could not be deleted!");
            return false;
        }

        sender.sendMessage("§7Warp §e" + args[0] + " §7has been deleted!");
        return true;
    }
}
