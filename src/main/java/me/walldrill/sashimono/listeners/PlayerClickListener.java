package me.walldrill.sashimono.listeners;

import me.walldrill.sashimono.Sashimono;
import me.walldrill.sashimono.menusystem.menu.SashimonoMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.BannerMeta;

public class PlayerClickListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // Check if the player right-clicked with a banner in hand
        if (event.getAction() == Action.RIGHT_CLICK_AIR && player.getInventory().getItemInMainHand().getItemMeta() instanceof BannerMeta) {
            new SashimonoMenu(Sashimono.getPlayerMenuUtility(player)).open();

        }
    }
}
