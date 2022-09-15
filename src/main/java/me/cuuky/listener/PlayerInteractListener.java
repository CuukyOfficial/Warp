package me.cuuky.listener;

import me.cuuky.Main;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    private final Main main;

    public PlayerInteractListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (!(block.getState() instanceof Sign))
            return;

        Sign sign = (Sign) block.getState();
        if (!event.getPlayer().hasPermission("Warp.warp")) {
            event.getPlayer().sendMessage(Main.getNoPerm());
            return;
        }

        if (sign.getLine(0).equalsIgnoreCase("§7[§eWarp§7]"))
            for (String warp : this.main.getConfiguration().getKeys(true))
                if (warp.equals(sign.getLine(1))) {
                    player.performCommand("warp " + warp);
                }
    }
}
