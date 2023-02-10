package me.walldrill.sashimono;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;

public class CustomPacketContainer extends PacketContainer {
    CustomPacketContainer(PacketType packetType) {
        super(packetType);
    }
}
