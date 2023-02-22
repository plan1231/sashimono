package me.walldrill.sashimono;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class BuffApplier extends BukkitRunnable {
    private static final int BUFF_RADIUS_SQUARED = 36;
    private final JavaPlugin plugin;

    public BuffApplier(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        // What you want to schedule goes here
        for(Player p1 : plugin.getServer().getOnlinePlayers()){
            for(Player p2 : plugin.getServer().getOnlinePlayers()){
                if(p1 == p2 || !Sashimono.getStateManager().playerHasCustomBanner(p1)) continue;
                if(p1.getLocation().distanceSquared(p2.getLocation()) <= BUFF_RADIUS_SQUARED && !Sashimono.getStateManager().hasPotionEffect(p2, PotionEffectType.SPEED)){
                    p2.addPotionEffect(PotionEffectType.SPEED.createEffect(1200, 20));
                    Sashimono.getStateManager().addPlayerPotionEffect(p2, PotionEffectType.SPEED);
                    p2.sendMessage("In sashimono range. Buff applied.");
                }
                else if(p1.getLocation().distanceSquared(p2.getLocation()) > BUFF_RADIUS_SQUARED && Sashimono.getStateManager().hasPotionEffect(p2, PotionEffectType.SPEED)){
                    p2.removePotionEffect(PotionEffectType.SPEED);
                    Sashimono.getStateManager().removePlayerPotionEffect(p2, PotionEffectType.SPEED);
                    p2.sendMessage("Out of sashimono range. Buff removed.");

                }
            }
        }

    }

}