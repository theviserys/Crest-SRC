package me.aristhena.event.events;

import me.aristhena.event.*;
import net.minecraft.network.*;

public class PacketSendEvent extends Event
{
    private Packet packet;
    private State state;
    
    public PacketSendEvent(final State state, final Packet packet) {
        this.state = state;
        this.packet = packet;
    }
    
    public Packet getPacket() {
        return this.packet;
    }
    
    public State getState() {
        return this.state;
    }
    
    public void setPacket(final Packet packet) {
        this.packet = packet;
    }
    
    public void setState(final State state) {
        this.state = state;
    }
}
