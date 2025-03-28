package de.cuuky.warp.listener;

import de.cuuky.warp.Warp;
import de.cuuky.warp.WarpPlugin;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.Objects;

@SuppressWarnings("unused")
public class WarpSignListener implements Listener {

    private final WarpPlugin warpPlugin;

    public WarpSignListener(WarpPlugin warpPlugin) {
        this.warpPlugin = warpPlugin;
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
        MetadataValue metadata = sign.getMetadata("warp").stream().findFirst().orElse(null);
        if (metadata == null)
            return;

        Warp warp = this.warpPlugin.getWarp(metadata.asString());
        if (warp != null) {
            player.performCommand("warp " + warp.getName());
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (!Objects.requireNonNull(event.getLine(0)).equalsIgnoreCase("[warp]")) return;

        if (!event.getPlayer().hasPermission("Warp.setwarp")) {
            event.getPlayer().sendMessage("§7You do not have permission to set a warp sign!");
            return;
        }

        String name = event.getLine(1);
        Warp warp = this.warpPlugin.getWarp(name);

        if (warp != null) {
            event.getBlock().setMetadata("warp", new FixedMetadataValue(this.warpPlugin, name));
            event.setLine(0, "§7[§eWarp§7]");
            event.getPlayer().sendMessage("§7Sign for §e" + name + " §7has been set!");
        }
    }
}
