package me.aristhena.crest.module.modules.clans;

import me.aristhena.crest.module.*;
import me.aristhena.crest.option.*;
import me.aristhena.event.events.*;
import me.aristhena.utils.*;
import net.minecraft.network.play.client.*;
import me.aristhena.event.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

@Module.Mod
public class Soup extends Module
{
    @Option.Op(min = 0.5, max = 10.0, increment = 0.5)
    private double health;
    @Option.Op(min = 50.0, max = 1000.0, increment = 25.0)
    private double delay;
    private Timer timer;
    
    public Soup() {
        this.health = 4.5;
        this.delay = 250.0;
        this.timer = new Timer();
    }
    
    @EventTarget(3)
    private void onUpdate(final UpdateEvent event) {
        if (event.getState().equals(Event.State.PRE)) {
            final int soupSlot = this.getSoup();
            if (soupSlot != -1 && ClientUtils.player().getHealth() <= this.health * 2.0 && this.timer.delay((float)this.delay) && ClientUtils.player().isCollidedVertically) {
                final int currentItem = ClientUtils.player().inventory.currentItem;
                ClientUtils.packet(new C09PacketHeldItemChange(soupSlot));
                ClientUtils.packet(new C08PacketPlayerBlockPlacement(ClientUtils.player().inventory.getCurrentItem()));
                ClientUtils.packet(new C09PacketHeldItemChange(currentItem));
                for (int i = 0; i < 80; ++i) {
                    ClientUtils.packet(new C03PacketPlayer(true));
                }
                this.timer.reset();
            }
        }
    }
    
    private int getSoup() {
        for (int i = 0; i <= 8; ++i) {
            final ItemStack stack = ClientUtils.player().inventory.getStackInSlot(i);
            if (stack != null) {
                if (Item.getIdFromItem(stack.getItem()) == Item.getIdFromItem(Items.mushroom_stew)) {
                    return i;
                }
            }
        }
        return -1;
    }
}
