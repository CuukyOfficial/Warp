package de.cuuky.warp.commands;

import de.cuuky.warp.Warp;
import de.cuuky.warp.WarpPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarpCommand implements CommandExecutor {

    private final WarpPlugin warpPlugin;

    public SetWarpCommand(WarpPlugin warpPlugin) {
        this.warpPlugin = warpPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Not for Console!");
            return false;
        }

        if (args.length != 1) {
            sender.sendMessage("§c/setwarp <NAME>");
            return false;
        }

        if (this.warpPlugin.getWarp(args[0]) != null) {
            sender.sendMessage("§7Warp §e" + args[0] + " §7already exists!");
            return false;
        }

        Player player = (Player) sender;
        Warp warp = new Warp(args[0], player.getLocation());
        if (!this.warpPlugin.addWarp(warp)) {
            player.sendMessage("§7Warp §e" + warp.getName() + " §7could not be set!");
            return false;
        }

        player.sendMessage("§7Warp §e" + warp.getName() + " §7has been set!");
        return true;
    }
}
