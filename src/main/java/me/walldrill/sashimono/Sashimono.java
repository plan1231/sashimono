package me.walldrill.sashimono;

import me.walldrill.sashimono.commands.EquipBannerCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Sashimono extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("starting up sashimono");
        getCommand("equipbanner").setExecutor(new EquipBannerCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
