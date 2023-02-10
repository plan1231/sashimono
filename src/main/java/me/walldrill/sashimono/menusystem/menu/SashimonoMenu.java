package me.walldrill.sashimono.menusystem.menu;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.Pair;
import me.walldrill.sashimono.PacketUtil;
import me.walldrill.sashimono.Sashimono;
import me.walldrill.sashimono.menusystem.Menu;
import me.walldrill.sashimono.menusystem.PlayerMenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class SashimonoMenu extends Menu {

    public SashimonoMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Equip a sashimono";
    }

    @Override
    public int getSlots() {
        return 9;
    }


    private boolean isInteractingBlockedSlot(InventoryClickEvent e){
        return e.getClickedInventory() == inventory && e.getSlot() != 0;
    }

    private boolean isMovingInWrong(InventoryClickEvent e){
        return e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY
                &&
                e.getClickedInventory() != inventory
                &&
                (!(e.getCurrentItem().getItemMeta() instanceof BannerMeta) ||
                 e.getCurrentItem().getAmount() != 1);
    }
    private boolean isActionPlace(InventoryClickEvent e){
        return e.getAction() == InventoryAction.PLACE_SOME ||
                e.getAction() == InventoryAction.PLACE_ONE ||
                e.getAction() == InventoryAction.PLACE_ALL;
    }
    private boolean isActionPickup(InventoryClickEvent e){
        return e.getAction() == InventoryAction.PICKUP_ALL ||
                e.getAction() == InventoryAction.PICKUP_HALF ||
                e.getAction() == InventoryAction.PICKUP_ONE ||
                e.getAction() == InventoryAction.PICKUP_SOME;
    }
    private boolean isPlacingDownWrong(InventoryClickEvent e){
        return (isActionPlace(e) || e.getAction() == InventoryAction.SWAP_WITH_CURSOR)
                &&
                e.getClickedInventory() == inventory
                &&
                (!(e.getWhoClicked().getItemOnCursor().getItemMeta() instanceof BannerMeta) || e.getWhoClicked().getItemOnCursor().getAmount() != 1);
    }
    @Override
    public void handleMenu(InventoryClickEvent e) {
        if(isInteractingBlockedSlot(e) || isMovingInWrong(e) || isPlacingDownWrong(e)
                ){
            e.setCancelled(true);
        }
    }

    /*
    WARNING: The way this menu is implemented means that players may lose a banner if they put one into the menu slot
    and then exiting the game without closing the inventory first.

    Consider moving the saving logic into handleMenu, or schedule it to run a few ticks later.
     */
    @Override
    public void handleMenuClose(InventoryCloseEvent e) {
        ItemStack itemStack = inventory.getItem(0);
        Player p = playerMenuUtility.getOwner();

        if(itemStack != null) {
            Sashimono.getPlayerBannerManager().setPlayerBanner(p, itemStack);

        }
        else {
            Sashimono.getPlayerBannerManager().removePlayerBanner(playerMenuUtility.getOwner());
            itemStack = new ItemStack(Material.AIR, 1);
        }


        PacketContainer packet = PacketUtil.createHelmetPacket(p, itemStack);
        for(Player otherP : Bukkit.getOnlinePlayers()){
            if(otherP == p) continue;
            try{
                ProtocolLibrary.getProtocolManager().sendServerPacket(otherP, packet);
            }
            catch (InvocationTargetException ite){
                System.out.println("Failed to send packet");
            }
        }
    }

    @Override
    public void setMenuItems() {

        ItemStack yes = new ItemStack(Material.EMERALD, 1);
        ItemMeta yes_meta = yes.getItemMeta();
        yes_meta.setDisplayName(ChatColor.YELLOW + "Drop a banner");
        ArrayList<String> yes_lore = new ArrayList<>();
        yes_lore.add(ChatColor.AQUA + "Cosmetic change only.");
        yes_meta.setLore(yes_lore);
        yes.setItemMeta(yes_meta);

        inventory.setItem(5, yes);

        setFillerGlass();

        // may want to reduce verbosity
        if(Sashimono.getPlayerBannerManager().playerHasCustomBanner(playerMenuUtility.getOwner()))
            inventory.setItem(0, Sashimono.getPlayerBannerManager().getPlayerBanner(playerMenuUtility.getOwner()));
        else
            inventory.clear(0);
    }
}
