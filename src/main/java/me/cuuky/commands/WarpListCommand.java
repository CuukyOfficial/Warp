package me.cuuky.commands;

import me.cuuky.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpListCommand implements CommandExecutor {

    private final Main main;

    public WarpListCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String warplist, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                int i = 0;
                p.sendMessage(Main.getPrefix() + "§7List of all §6Warps§7:");
                for (String key : this.main.getConfiguration().getKeys(true)) {
                    if (!(key.contains("."))) {
                        i += 1;
                        p.sendMessage(Main.getPrefix() + "§7" + i + ". §6'" + key + "'");
                    }
                }
            } else p.sendMessage(Main.getPrefix() + "§c/warplist");
        } else System.out.println("Not for Console!");
        return false;
    }
}