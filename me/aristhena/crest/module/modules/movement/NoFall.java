package me.aristhena.crest.module.modules.movement;

import me.aristhena.crest.module.*;
import me.aristhena.event.events.*;
import me.aristhena.event.*;

@Module.Mod(displayName = "No Fall")
public class NoFall extends Module
{
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        if (!event.shouldAlwaysSend()) {
            event.setGround(true);
        }
    }
}
