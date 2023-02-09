package me.walldrill.sashimono;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class PdcReader {
    public static Plugin plugin;
    private static final String KEY = "sashimono";
    static Material playerBannerType(Player player){
        String val = player.getPersistentDataContainer().get(getNamespacedKey(KEY), PersistentDataType.STRING);
        return Material.getMaterial(val);
    }

    static boolean playerEquippedSashimono(Player player){
        return player.getPersistentDataContainer().has(getNamespacedKey(KEY), PersistentDataType.INTEGER);
    }

    private static NamespacedKey getNamespacedKey(String s){
        return new NamespacedKey(plugin, s);
    }
}
