package me.aristhena.crest.module.modules.render;

import me.aristhena.crest.module.*;
import me.aristhena.crest.option.*;
import me.aristhena.event.events.*;
import me.aristhena.utils.*;
import me.aristhena.event.*;

@Module.Mod(displayName = "Anti-Effects", shown = false)
public class AntiEffects extends Module
{
    private static final int NAUSEA_ID = 9;
    @Option.Op
    private boolean nausea;
    @Option.Op
    private boolean blindness;
    
    public AntiEffects() {
        this.nausea = true;
        this.blindness = true;
    }
    
    @EventTarget
    private void onTick(final UpdateEvent event) {
        if (event.getState() == Event.State.POST && ClientUtils.player().isPotionActive(9) && this.nausea) {
            ClientUtils.player().removePotionEffect(9);
        }
    }
    
    public boolean isBlindness() {
        return this.blindness;
    }
}
