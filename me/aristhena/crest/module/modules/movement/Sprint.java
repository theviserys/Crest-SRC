package me.aristhena.crest.module.modules.movement;

import me.aristhena.crest.module.*;
import me.aristhena.crest.option.*;
import me.aristhena.utils.*;
import me.aristhena.event.events.*;
import me.aristhena.event.*;
import net.minecraft.network.play.client.*;

@Module.Mod(shown = false)
public class Sprint extends Module
{
    @Option.Op(name = "Multi-Directional")
    private boolean multiDirection;
    @Option.Op(name = "Auto Sprint")
    private boolean auto;
    @Option.Op(name = "Client Sided")
    private boolean clientSide;
    @Option.Op
    private boolean legit;
    
    public Sprint() {
        this.multiDirection = true;
        this.auto = true;
        this.clientSide = true;
    }
    
    @EventTarget
    private void onUpdate(final TickEvent event) {
        if (ClientUtils.player() != null && this.canSprint()) {
            ClientUtils.player().setSprinting(true);
        }
    }
    
    @EventTarget
    private void onSprint(final SprintEvent event) {
        if (this.canSprint()) {
            event.setSprinting(true);
        }
    }
    
    @EventTarget
    private void onPacketSend(final PacketSendEvent event) {
        if (event.getState().equals(Event.State.PRE) && this.clientSide && event.getPacket() instanceof C0BPacketEntityAction) {
            final C0BPacketEntityAction packet = (C0BPacketEntityAction)event.getPacket();
            if (packet.func_180764_b() == C0BPacketEntityAction.Action.START_SPRINTING || packet.func_180764_b() == C0BPacketEntityAction.Action.STOP_SPRINTING) {
                event.setCancelled(true);
            }
        }
    }
    
    private boolean canSprint() {
        return this.auto && (!ClientUtils.player().isCollidedHorizontally && !ClientUtils.player().isSneaking() && (!this.legit || new NoSlowdown().getInstance().isEnabled() || (this.legit && ClientUtils.player().getFoodStats().getFoodLevel() > 5 && !ClientUtils.player().isUsingItem()))) && (this.multiDirection ? (ClientUtils.player().movementInput.moveForward != 0.0f || ClientUtils.player().movementInput.moveStrafe != 0.0f) : (ClientUtils.player().movementInput.moveForward > 0.0f));
    }
}
