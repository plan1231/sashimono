package me.walldrill.sashimono;

import me.walldrill.sashimono.commands.EquipBannerCommand;
import me.walldrill.sashimono.commands.SashimonoCommand;
import me.walldrill.sashimono.listeners.MenuListener;
import me.walldrill.sashimono.menusystem.PlayerMenuUtility;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Sashimono extends JavaPlugin {

    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();
    private static PlayerBannerManager playerBannerManager;

    public static PlayerBannerManager getPlayerBannerManager(){
        return playerBannerManager;
    }

    @Override
    public void onEnable() {
        PdcReader.plugin = this;
        playerBannerManager = new PlayerBannerManager();
        getCommand("sashimono").setExecutor(new SashimonoCommand());
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    //Provide a player and return a menu system for that player
    //create one if they don't already have one
    public static PlayerMenuUtility getPlayerMenuUtility(Player p) {
        PlayerMenuUtility playerMenuUtility;
        if (!(playerMenuUtilityMap.containsKey(p))) { //See if the player has a playermenuutility "saved" for them

            //This player doesn't. Make one for them and add it to the hashmap
            playerMenuUtility = new PlayerMenuUtility(p);
            playerMenuUtilityMap.put(p, playerMenuUtility);

            return playerMenuUtility;
        } else {
            return playerMenuUtilityMap.get(p); //Return the object by using the provided player
        }
    }


}
