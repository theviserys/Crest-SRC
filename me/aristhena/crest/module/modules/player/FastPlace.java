package me.aristhena.crest.module.modules.player;

import me.aristhena.crest.module.*;
import me.aristhena.event.events.*;
import me.aristhena.utils.*;
import me.aristhena.event.*;

@Module.Mod(displayName = "Fast Place")
public class FastPlace extends Module
{
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        if (event.getState() == Event.State.PRE) {
            ClientUtils.mc().rightClickDelayTimer = 0;
        }
    }
}
