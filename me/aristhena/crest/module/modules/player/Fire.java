package me.aristhena.crest.module.modules.player;

import me.aristhena.crest.module.*;
import me.aristhena.event.events.*;
import me.aristhena.event.*;

public class Fire extends Module
{
    @EventTarget
    private void onPostUpdate(final UpdateEvent event) {
        event.getState();
        final Event.State pre = Event.State.PRE;
    }
}
