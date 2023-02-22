package me.walldrill.sashimono.listeners;
import com.comphenix.protocol.ProtocolLibrary;
import me.walldrill.sashimono.PacketUtil;
import me.walldrill.sashimono.Sashimono;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;

public class PlayerEventsListener implements Listener {
    Plugin plugin;

    public PlayerEventsListener(Plugin p){
        plugin = p;
    }

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent e){
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                for(Player other : Bukkit.getOnlinePlayers()) {
                    System.out.println(other.getName());
                    if (other == e.getPlayer()) continue;
                    try {
                        if(Sashimono.getStateManager().playerHasCustomBanner(e.getPlayer())){
                            ProtocolLibrary.getProtocolManager().sendServerPacket(other, PacketUtil.createHelmetPacket(e.getPlayer(), Sashimono.getStateManager().getPlayerBanner(e.getPlayer())));
                        }
                        if(Sashimono.getStateManager().playerHasCustomBanner(other)){
                            ProtocolLibrary.getProtocolManager().sendServerPacket(e.getPlayer(), PacketUtil.createHelmetPacket(other, Sashimono.getStateManager().getPlayerBanner(other)));
                        }

                    } catch (InvocationTargetException ite) {
                        System.out.println("Failed to send packet");
                    }
                }

            }
        }, 40L); // 40 Tick (2 Seconds) delay before run. This is so that other clients have time to rec
    }
}
