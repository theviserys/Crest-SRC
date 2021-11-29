package me.aristhena.crest.module.modules.combat;

import me.aristhena.crest.module.*;
import me.aristhena.crest.option.*;
import me.aristhena.event.events.*;
import me.aristhena.utils.*;
import net.minecraft.network.play.server.*;
import me.aristhena.event.*;

@Module.Mod
public class Velocity extends Module
{
    @Option.Op(min = 0.0, max = 200.0, increment = 5.0)
    private double percent;
    
    public Velocity() {
        this.percent = 0.0;
    }
    
    @EventTarget
    private void onPacketReceive(final PacketReceiveEvent event) {
        if (event.getPacket() instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity packet = (S12PacketEntityVelocity)event.getPacket();
            if (ClientUtils.world().getEntityByID(packet.func_149412_c()) == ClientUtils.player()) {
                if (this.percent > 0.0) {
                    final S12PacketEntityVelocity s12PacketEntityVelocity = packet;
                    s12PacketEntityVelocity.field_149415_b *= (int)(this.percent / 100.0);
                    final S12PacketEntityVelocity s12PacketEntityVelocity2 = packet;
                    s12PacketEntityVelocity2.field_149416_c *= (int)(this.percent / 100.0);
                    final S12PacketEntityVelocity s12PacketEntityVelocity3 = packet;
                    s12PacketEntityVelocity3.field_149414_d *= (int)(this.percent / 100.0);
                }
                else {
                    event.setCancelled(true);
                }
            }
        }
        else if (event.getPacket() instanceof S27PacketExplosion) {
            final S27PacketExplosion s27PacketExplosion;
            final S27PacketExplosion packet2 = s27PacketExplosion = (S27PacketExplosion)event.getPacket();
            s27PacketExplosion.x *= (float)(this.percent / 100.0);
            final S27PacketExplosion s27PacketExplosion2 = packet2;
            s27PacketExplosion2.y *= (float)(this.percent / 100.0);
            final S27PacketExplosion s27PacketExplosion3 = packet2;
            s27PacketExplosion3.z *= (float)(this.percent / 100.0);
        }
    }
}
