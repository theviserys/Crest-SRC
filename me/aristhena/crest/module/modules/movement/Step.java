package me.aristhena.crest.module.modules.movement;

import me.aristhena.crest.module.*;
import me.aristhena.crest.option.*;
import me.aristhena.event.Event;
import me.aristhena.event.EventTarget;
import me.aristhena.event.events.*;
import me.aristhena.utils.*;
import net.minecraft.network.play.client.*;

@Module.Mod
public class Step extends Module
{
    @Option.Op(min = 1.0, max = 10.0, increment = 1.0)
    private double height;
    @Option.Op
    public boolean packet;
    private int stepStage;
    public static boolean stepping;
    
    public Step() {
        this.height = 1.0;
    }

    @EventTarget
    private void onStep(final StepEvent event) {
        if (event.getState() == Event.State.PRE) {
            if (this.height > 1.0) {
                event.setStepHeight(this.height);
            }
            else if (!ClientUtils.movementInput().jump && ClientUtils.player().isCollidedVertically) {
                event.setStepHeight(1.0);
                event.setActive(true);
            }
        }
        else if (event.getState() == Event.State.POST && this.packet) {
            ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + 0.42, ClientUtils.z(), ClientUtils.player().onGround));
            ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + 0.75, ClientUtils.z(), ClientUtils.player().onGround));
        }
    }
}
