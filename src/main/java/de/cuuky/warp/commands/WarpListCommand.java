package de.cuuky.warp.commands;

import de.cuuky.warp.Warp;
import de.cuuky.warp.WarpPlugin;
import io.github.almightysatan.slams.Placeholder;
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
            sender.sendMessage(this.warpPlugin.getMessages().listUsage.value());
            return false;
        }

        List<Warp> warps = this.warpPlugin.getWarps().collect(Collectors.toList());
        if (warps.isEmpty()) {
            sender.sendMessage(this.warpPlugin.getMessages().listEmpty.value());
            return false;
        }

        String warpNames = "§6" + this.warpPlugin.getWarps().map(Warp::getName).collect(Collectors.joining("§7, §6"));
        sender.sendMessage(this.warpPlugin.getMessages().listSuccess.value(Placeholder.constant("warps", warpNames)));
        return true;
    }
}