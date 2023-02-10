package me.walldrill.sashimono;

import com.comphenix.protocol.wrappers.Pair;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Array;
import java.util.*;

public class PlayerBannerManager {
    private final HashMap<UUID, ItemStack> playerBanners = new HashMap<>();
    public void ImportData(Collection<Pair<UUID, ItemStack>> pairs){
        for(Pair<UUID, ItemStack> pair : pairs){
            playerBanners.put(pair.getFirst(), pair.getSecond());
        }
    }
    public ArrayList<Pair<UUID, ItemStack>> getAllBanners(){
        ArrayList<Pair<UUID, ItemStack>> result = new ArrayList<>();
        for(UUID key : playerBanners.keySet()){
            result.add(new Pair<>(key, playerBanners.get(key)));
        }
        return result;
    }

    public void setPlayerBanner(Player p, ItemStack banner) {
        playerBanners.put(p.getUniqueId(), banner);
    }
    public void removePlayerBanner(Player p){
        playerBanners.remove(p.getUniqueId());
    }
    public boolean playerHasCustomBanner(Player p){
        return playerBanners.containsKey(p.getUniqueId());
    }

    public ItemStack getPlayerBanner(Player p){
        return playerBanners.get(p.getUniqueId());
    }
}
