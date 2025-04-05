package de.cuuky.warp.commands;

import de.cuuky.warp.Warp;
import de.cuuky.warp.WarpPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

public class WarpListCommand implements CommandExecutor {

    private final WarpPlugin warpPlugin;

    public WarpListCommand(WarpPlugin warpPlugin) {
        this.warpPlugin = warpPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 0) {
            sender.sendMessage("§c/warplist");
            return false;
        }

        List<Warp> warps = this.warpPlugin.getWarps().collect(Collectors.toList());
        if (warps.isEmpty()) {
            sender.sendMessage("§7There are no §6Warps§7!");
            return false;
        }

        String warpNames = "§6" + this.warpPlugin.getWarps().map(Warp::getName).collect(Collectors.joining("§7, §6"));
        sender.sendMessage("§7List of all §6Warps§7: " + warpNames);
        return true;
    }
}