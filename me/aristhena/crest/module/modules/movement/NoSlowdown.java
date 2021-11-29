package me.aristhena.crest.module.modules.movement;

import me.aristhena.crest.module.*;
import me.aristhena.crest.option.*;
import me.aristhena.event.events.*;
import me.aristhena.utils.*;
import me.aristhena.event.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;

@Module.Mod(displayName = "No Slowdown")
public class NoSlowdown extends Module
{
    @Option.Op
    public boolean vanilla;
    public static boolean wasOnground;
    
    static {
        NoSlowdown.wasOnground = true;
    }
    
    @EventTarget
    private void onItemUse(final ItemSlowEvent event) {
        event.setCancelled(true);
    }
    
    @EventTarget(4)
    private void onUpdate(final UpdateEvent event) {
        if (!this.vanilla && ClientUtils.player().isBlocking() && (ClientUtils.player().motionX != 0.0 || ClientUtils.player().motionZ != 0.0) && NoSlowdown.wasOnground) {
            if (event.getState() == Event.State.PRE) {
                ClientUtils.packet(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
            else if (event.getState() == Event.State.POST) {
                ClientUtils.packet(new C08PacketPlayerBlockPlacement(ClientUtils.player().inventory.getCurrentItem()));
            }
        }
        if (!new Speed().getInstance().isEnabled() || !((Speed)new Speed().getInstance()).latest.getValue()) {
            NoSlowdown.wasOnground = ClientUtils.player().onGround;
        }
    }
}
