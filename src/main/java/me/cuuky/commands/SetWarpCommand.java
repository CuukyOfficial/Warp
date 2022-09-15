package me.cuuky.commands;

import me.cuuky.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class SetWarpCommand implements CommandExecutor {

    private final Main main;

    public SetWarpCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String setwarp, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 1) {
                YamlConfiguration cfg = this.main.getConfiguration();

                cfg.set(args[0] + ".world", p.getWorld().getName());
                cfg.set(args[0] + ".x", p.getLocation().getX());
                cfg.set(args[0] + ".y", p.getLocation().getY());
                cfg.set(args[0] + ".z", p.getLocation().getZ());
                cfg.set(args[0] + ".yaw", p.getLocation().getYaw());
                cfg.set(args[0] + ".pitch", p.getLocation().getPitch());

                p.sendMessage(Main.getPrefix() + "§7Warp §e" + args[0] + " §7has been set!");
            } else p.sendMessage(Main.getPrefix() + "§c/setwarp <NAME>");
        } else System.out.println("Not for Console!");
        return false;
    }
}
