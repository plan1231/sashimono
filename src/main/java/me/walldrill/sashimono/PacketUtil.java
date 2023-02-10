package me.walldrill.sashimono;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.Pair;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class PacketUtil {
    // I can probably rewrite this using generics but that's for another day
    public static PacketContainer createHelmetPacket(Player p, ItemStack itemStack){
        PacketContainer ee = new PacketContainer(PacketType.Play.Server.ENTITY_EQUIPMENT);
        ee.getIntegers().write(0, p.getEntityId());
        ArrayList<Pair<EnumWrappers.ItemSlot, ItemStack>> lst = new ArrayList<>();
        lst.add(new Pair<>(EnumWrappers.ItemSlot.HEAD, itemStack));
        ee.getSlotStackPairLists().write(0, lst);
        return ee;
    }

    public static CustomPacketContainer createHelmetPacketCustom(Player p, ItemStack itemStack){
        CustomPacketContainer ee = new CustomPacketContainer(PacketType.Play.Server.ENTITY_EQUIPMENT);
        ee.getIntegers().write(0, p.getEntityId());
        ArrayList<Pair<EnumWrappers.ItemSlot, ItemStack>> lst = new ArrayList<>();
        lst.add(new Pair<>(EnumWrappers.ItemSlot.HEAD, itemStack));
        ee.getSlotStackPairLists().write(0, lst);
        return ee;
    }
}
