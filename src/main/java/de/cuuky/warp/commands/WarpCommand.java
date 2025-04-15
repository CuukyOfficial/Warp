package de.cuuky.warp.commands;

import de.cuuky.warp.Warp;
import de.cuuky.warp.WarpContext;
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
            sender.sendMessage(this.warpPlugin.getMessages().noConsole.value());
            return false;
        }

        Player player = (Player) sender;
        if (args.length != 1) {
            player.sendMessage(this.warpPlugin.getMessages().warpUsage.value());
            return false;
        }

        Warp warp = this.warpPlugin.getWarp(args[0]);
        if (warp == null) {
            player.sendMessage(this.warpPlugin.getMessages().notFound.value(new WarpContext(args[0])));
            return false;
        }

        player.teleport(warp.getLocation());
        player.sendMessage(this.warpPlugin.getMessages().warpSuccess.value(new WarpContext(warp)));
        return true;
    }
}