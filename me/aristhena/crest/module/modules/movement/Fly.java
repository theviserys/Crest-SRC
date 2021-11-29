package me.aristhena.crest.module.modules.movement;

import me.aristhena.crest.module.*;
import me.aristhena.crest.option.*;
import me.aristhena.utils.*;
import me.aristhena.event.*;
import me.aristhena.event.events.*;

@Module.Mod
public class Fly extends Module
{
    @Option.Op
    private boolean damage;
    @Option.Op(min = 0.0, max = 9.0, increment = 0.01)
    private double speed;
    
    public Fly() {
        this.speed = 0.8;
    }
    
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        if (event.getState() == Event.State.PRE) {
            if (ClientUtils.movementInput().jump) {
                ClientUtils.player().motionY = this.speed;
            }
            else if (ClientUtils.movementInput().sneak) {
                ClientUtils.player().motionY = -this.speed;
            }
            else {
                ClientUtils.player().motionY = 0.0;
            }
        }
    }
    
    @EventTarget
    private void onMove(final MoveEvent event) {
        ClientUtils.setMoveSpeed(event, this.speed);
    }
}
