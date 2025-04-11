package de.cuuky.warp.commands;

import de.cuuky.warp.Warp;
import de.cuuky.warp.WarpContext;
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
            sender.sendMessage(this.warpPlugin.getMessages().deleteUsage.value());
            return false;
        }

        Warp warp = this.warpPlugin.getWarp(args[0]);
        if (warp == null) {
            sender.sendMessage(this.warpPlugin.getMessages().notFound.value(new WarpContext(args[0])));
            return false;
        }

        if (!this.warpPlugin.deleteWarp(warp)) {
            sender.sendMessage(this.warpPlugin.getMessages().deleteError.value(new WarpContext(warp)));
            return false;
        }

        sender.sendMessage(this.warpPlugin.getMessages().deleteSuccess.value(new WarpContext(warp)));
        return true;
    }
}
