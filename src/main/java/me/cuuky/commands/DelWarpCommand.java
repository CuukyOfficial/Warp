package me.cuuky.commands;

import me.cuuky.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class DelWarpCommand implements CommandExecutor {

    private final Main main;

    public DelWarpCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String delwarp, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 1) {
                YamlConfiguration cfg = this.main.getConfiguration();
                if (!(cfg.getString(args[0]) == null)) {
                    cfg.set(args[0], null);
                    p.sendMessage(Main.getPrefix() + "§7Warp §e" + args[0] + " §7has been deleted!");
                } else p.sendMessage(Main.getPrefix() + "§7Warp §e" + args[0] + " §7not found!");
            } else p.sendMessage(Main.getPrefix() + "§c/delwarp <WARP>");
        } else System.out.println("Not for Console!");
        return false;
    }
}
