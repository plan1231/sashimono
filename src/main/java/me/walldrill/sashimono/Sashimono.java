package me.walldrill.sashimono;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.Pair;
import me.walldrill.sashimono.commands.SashimonoCommand;
import me.walldrill.sashimono.listeners.MenuListener;
import me.walldrill.sashimono.listeners.PlayerEventsListener;
import me.walldrill.sashimono.menusystem.PlayerMenuUtility;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.nio.Buffer;
import java.util.*;

import static com.comphenix.protocol.PacketType.Play.Server.ENTITY_EQUIPMENT;

public final class Sashimono extends JavaPlugin {

    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();
    private static PlayerBannerManager playerBannerManager;

    public static PlayerBannerManager getPlayerBannerManager(){
        return playerBannerManager;
    }

    @Override
    public void onEnable() {
        playerBannerManager = new PlayerBannerManager();
        List<List<String>> records = new ArrayList<>();
        if(!getDataFolder().exists())
            getDataFolder().mkdir();
        try (BufferedReader br = new BufferedReader(new FileReader(getDataFolder().getAbsolutePath() + "/banners.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
            ArrayList<Pair<UUID, ItemStack>> data = new ArrayList<>();




            for(List<String> row : records){
                //decode string into raw bytes
                byte[] serializedObject = Base64.getDecoder().decode(row.get(1));

                // Typical Java stupidity: one inputstream into another inputstream
                // so fucking overengineered
                ByteArrayInputStream in = new ByteArrayInputStream(serializedObject);
                //object input stream to serialize bytes into objects
                BukkitObjectInputStream is = new BukkitObjectInputStream(in);

                //Use the object input stream to deserialize an object from the raw bytes
                ItemStack newItem = (ItemStack) is.readObject();
                data.add(new Pair<>(UUID.fromString(row.get(0)),newItem ));
            }
            playerBannerManager.ImportData(data);
        } catch (IOException ignored) {
            System.out.println("IO Error. Data ignored");

        }
        catch (ClassNotFoundException ignored){
            System.out.println("Error deserializing. Data ignored.");
        }


        getCommand("sashimono").setExecutor(new SashimonoCommand());
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerEventsListener(this), this);
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ENTITY_EQUIPMENT) {
                    @Override
                    public void onPacketSending(PacketEvent event) {

                        PacketContainer packet = event.getPacket();
                        PacketType type = event.getPacketType();
                        if(type != ENTITY_EQUIPMENT) return;

                        Player player = null;
                        int entityId = packet.getIntegers().read(0);
                        for (Player p : getServer().getOnlinePlayers()) {
                            if (p.getEntityId() == entityId)  player = p;
                        }
                        if(player == null || !playerBannerManager.playerHasCustomBanner(player)) return;

                        List<Pair<EnumWrappers.ItemSlot, ItemStack>> lst = packet.getSlotStackPairLists().read(0);
                        for(Pair<EnumWrappers.ItemSlot, ItemStack> pair : lst){
                            if(pair.getFirst() != EnumWrappers.ItemSlot.HEAD) continue;
                            pair.setSecond(playerBannerManager.getPlayerBanner(player));
                        }
                        packet.getSlotStackPairLists().write(0, lst);
                    }
                });
    }

    @Override
    public void onDisable() {
        if(!getDataFolder().exists())
            getDataFolder().mkdir();

        // by default, will overwrite if exists
        try (PrintWriter pr = new PrintWriter(new FileWriter(getDataFolder().getAbsolutePath() + "/banners.csv"))) {
            ArrayList<Pair<UUID, ItemStack>> data = playerBannerManager.getAllBanners();

            for(Pair<UUID, ItemStack> row : data){
                //decode string into raw bytes
                ByteArrayOutputStream io = new ByteArrayOutputStream();
                BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
                os.writeObject(row.getSecond());
                os.flush();

                byte[] serializedObject = io.toByteArray();

                //Encode the serialized object into to the base64 format
                String encodedObject = new String(Base64.getEncoder().encode(serializedObject));
                pr.println(row.getFirst().toString() + "," + encodedObject);
            }
            pr.flush();
        } catch (IOException ignored) {
            System.out.println("IO Error. No data saved");
        }
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
