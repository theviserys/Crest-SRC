package me.aristhena.crest.module.modules.misc;

import me.aristhena.crest.module.*;
import me.aristhena.crest.option.*;
import net.minecraft.network.play.server.*;
import me.aristhena.event.events.*;
import me.aristhena.event.*;
import me.aristhena.utils.*;

@Module.Mod(displayName = "World Time")
public class WorldTime extends Module
{
    @Option.Op(min = 0.0, max = 24000.0, increment = 250.0)
    private double time;
    
    public WorldTime() {
        this.time = 9000.0;
    }
    
    @EventTarget
    private void onPacketRecieve(final PacketReceiveEvent event) {
        if (event.getPacket() instanceof S03PacketTimeUpdate) {
            event.setCancelled(true);
        }
    }
    
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        if (event.getState() == Event.State.POST) {
            ClientUtils.world().setWorldTime((long)this.time);
        }
    }
}
