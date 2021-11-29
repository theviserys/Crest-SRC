package me.aristhena.crest.module.modules.movement;

import me.aristhena.crest.module.*;
import me.aristhena.event.events.*;
import me.aristhena.utils.*;
import me.aristhena.event.*;

@Module.Mod(displayName = "Fast Ladder")
public class FastLadder extends Module
{
    private static final double MAX_LADDER_SPEED = 0.287299999999994;
    
    @EventTarget
    private void onMove(final MoveEvent event) {
        if (event.getY() > 0.0 && ClientUtils.player().isOnLadder()) {
            event.setY(0.287299999999994);
        }
    }
}
