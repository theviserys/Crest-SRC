package me.aristhena.crest.module.modules.movement.speed;

import me.aristhena.crest.module.*;
import me.aristhena.utils.*;
import me.aristhena.crest.module.modules.movement.*;
import me.aristhena.event.events.*;
import me.aristhena.event.*;

public class Minez extends SpeedMode
{
    private double moveSpeed;
    private double lastDist;
    private int stage;
    
    public Minez(final String name, final boolean value, final Module module) {
        super(name, value, module);
    }
    
    @Override
    public boolean enable() {
        if (super.enable()) {
            if (ClientUtils.player() != null) {
                this.moveSpeed = Speed.getBaseMoveSpeed();
            }
            this.lastDist = 0.0;
            this.stage = 4;
        }
        return true;
    }
    
    @Override
    public boolean onMove(final MoveEvent event) {
        if (super.onMove(event)) {
            switch (this.stage) {
                case 1: {
                    this.moveSpeed = 0.579;
                    break;
                }
                case 2: {
                    this.moveSpeed = 0.66781;
                    break;
                }
                default: {
                    this.moveSpeed = Speed.getBaseMoveSpeed();
                    break;
                }
            }
            ClientUtils.setMoveSpeed(event, this.moveSpeed = Math.max(this.moveSpeed, Speed.getBaseMoveSpeed()));
            ++this.stage;
        }
        return true;
    }
    
    @Override
    public boolean onUpdate(final UpdateEvent event) {
        if (super.onUpdate(event) && event.getState() == Event.State.PRE) {
            switch (this.stage) {
                case 1: {
                    event.setY(event.getY() + 1.0E-4);
                    ++this.stage;
                    break;
                }
                case 2: {
                    event.setY(event.getY() + 2.0E-4);
                    ++this.stage;
                    break;
                }
                default: {
                    this.stage = 1;
                    if (!ClientUtils.player().isSneaking() && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) && !ClientUtils.gamesettings().keyBindJump.isPressed()) {
                        this.stage = 1;
                        break;
                    }
                    this.moveSpeed = Speed.getBaseMoveSpeed();
                    break;
                }
            }
        }
        return true;
    }
}
