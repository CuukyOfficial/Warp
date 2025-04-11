package de.cuuky.warp.commands;

import de.cuuky.warp.Warp;
import de.cuuky.warp.WarpContext;
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
            sender.sendMessage(this.warpPlugin.getMessages().noConsole.value());
            return false;
        }

        if (args.length != 1) {
            sender.sendMessage(this.warpPlugin.getMessages().setUsage.value());
            return false;
        }

        if (this.warpPlugin.getWarp(args[0]) != null) {
            sender.sendMessage(this.warpPlugin.getMessages().setExists.value(new WarpContext(args[0])));
            return false;
        }

        Player player = (Player) sender;
        Warp warp = new Warp(args[0], player.getLocation());
        if (!this.warpPlugin.addWarp(warp)) {
            player.sendMessage(this.warpPlugin.getMessages().setError.value(new WarpContext(warp)));
            return false;
        }

        player.sendMessage(this.warpPlugin.getMessages().setSuccess.value(new WarpContext(warp)));
        return true;
    }
}
