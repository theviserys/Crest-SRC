package me.aristhena.crest.module.modules.misc;

import me.aristhena.crest.module.*;
import me.aristhena.crest.option.*;
import me.aristhena.event.events.*;
import net.minecraft.network.play.client.*;
import me.aristhena.utils.*;
import net.minecraft.network.*;
import me.aristhena.event.*;

@Module.Mod
public class Derp extends Module
{
    @Option.Op
    private boolean spinny;
    @Option.Op
    private boolean headless;
    @Option.Op(name = "Spin Increment", min = 1.0, max = 42.0, increment = 1.0)
    private double increment;
    private double serverYaw;
    
    public Derp() {
        this.increment = 25.0;
    }
    
    @EventTarget(0)
    private void onUpdate(final UpdateEvent event) {
        if (event.getState() == Event.State.PRE) {
            if (this.spinny) {
                this.serverYaw += this.increment;
                event.setYaw((float)this.serverYaw);
            }
            if (this.headless) {
                event.setPitch(180.0f);
            }
            else if (!this.headless && !this.spinny) {
                event.setYaw((float)(Math.random() * 360.0));
                event.setPitch((float)(Math.random() * 360.0));
                ClientUtils.packet(new C0APacketAnimation());
            }
        }
    }
}
