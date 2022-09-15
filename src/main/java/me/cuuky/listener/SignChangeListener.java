package me.cuuky.listener;

import me.cuuky.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.util.Objects;

public class SignChangeListener implements Listener {

    private final Main main;

    public SignChangeListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (!Objects.requireNonNull(event.getLine(0)).equalsIgnoreCase("[warp]")) return;

        if (!event.getPlayer().hasPermission("Warp.setwarp")) {
            event.getPlayer().sendMessage(Main.getNoPerm());
            return;
        }

        for (String string : this.main.getConfiguration().getKeys(true))
            if (string.equals(event.getLine(1))) {
                event.setLine(0, "§7[§eWarp§7]");
                event.getPlayer().sendMessage(Main.getPrefix() + "§7The sign of the warp §e" + string + " §7has been set!");
            }
    }
}
