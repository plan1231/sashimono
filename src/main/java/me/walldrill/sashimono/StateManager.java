package me.walldrill.sashimono;

import com.comphenix.protocol.wrappers.Pair;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class StateManager {
    // Mapping for players and the banner they've equipped
    private final HashMap<UUID, ItemStack> playerBanners = new HashMap<>();
    // Mapping for players and whether they've received a buff because they're in range of another player
    private final HashMap<Player, HashSet<PotionEffectType>> playerPotions = new HashMap<>();
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

    public void addPlayerPotionEffect(Player p, PotionEffectType potionEffectType){
        if(!playerPotions.containsKey(p)) playerPotions.put(p, new HashSet<>());
        playerPotions.get(p).add(potionEffectType);
    }
    public void removePlayerPotionEffect(Player p, PotionEffectType potionEffectType){
        playerPotions.get(p).remove(potionEffectType);
    }
    public boolean hasPotionEffect(Player p, PotionEffectType potionEffectType){
        if(!playerPotions.containsKey(p)) return false;
        return playerPotions.get(p).contains(potionEffectType);
    }
}
