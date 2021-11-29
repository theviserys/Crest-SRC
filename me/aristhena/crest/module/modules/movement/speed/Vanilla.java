package me.aristhena.crest.module.modules.movement.speed;

import me.aristhena.crest.module.*;
import me.aristhena.event.events.*;
import me.aristhena.utils.*;
import me.aristhena.crest.module.modules.movement.*;

public class Vanilla extends SpeedMode
{
    public Vanilla(final String name, final boolean value, final Module module) {
        super(name, value, module);
    }
    
    @Override
    public boolean onMove(final MoveEvent event) {
        if (super.onMove(event) && ClientUtils.player().onGround) {
            ClientUtils.setMoveSpeed(event, ((Speed)this.getModule()).speed);
        }
        return true;
    }
}
