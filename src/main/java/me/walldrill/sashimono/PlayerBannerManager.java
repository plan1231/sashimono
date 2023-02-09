package me.walldrill.sashimono;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class PlayerBannerManager {
    private final HashMap<UUID, ItemStack> playerBanners = new HashMap<>();

    public void setPlayerBanner(Player p, ItemStack banner) {
        playerBanners.put(p.getUniqueId(), banner);
    }

    public boolean playerHasCustomBanner(Player p){
        return playerBanners.containsKey(p.getUniqueId());
    }

    public ItemStack getPlayerBanner(Player p){
        return playerBanners.get(p.getUniqueId());
    }
}
