package me.aristhena.crest.module.modules.combat;

import me.aristhena.crest.module.*;
import me.aristhena.crest.option.*;
import me.aristhena.event.events.*;
import me.aristhena.utils.*;
import me.aristhena.utils.Timer;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.potion.*;
import net.minecraft.item.*;
import java.util.*;
import me.aristhena.event.*;

@Module.Mod(displayName = "Auto Potion")
public class AutoPot extends Module
{
    @Option.Op(min = 0.5, max = 10.0, increment = 0.5)
    private double health;
    @Option.Op(min = 200.0, max = 1000.0, increment = 25.0)
    private double delay;
    private Timer timer;
    
    public AutoPot() {
        this.health = 4.5;
        this.delay = 250.0;
        this.timer = new Timer();
    }
    
    @EventTarget(3)
    private void onUpdate(final UpdateEvent event) {
        switch (event.getState()) {
            case PRE: {
                if (this.getPotionFromInventory() != -1 && ClientUtils.player().getHealth() <= this.health * 2.0 && this.timer.delay((float)this.delay)) {
                    event.setAlwaysSend(true);
                    event.setPitch(110.0f);
                    break;
                }
                break;
            }
            case POST: {
                final int potSlot = this.getPotionFromInventory();
                if (potSlot != -1 && ClientUtils.player().getHealth() <= this.health * 2.0 && this.timer.delay((float)this.delay)) {
                    final int currentItem = ClientUtils.player().inventory.currentItem;
                    this.swap(potSlot, 8);
                    ClientUtils.packet(new C09PacketHeldItemChange(8));
                    ClientUtils.packet(new C08PacketPlayerBlockPlacement(ClientUtils.player().inventory.getCurrentItem()));
                    ClientUtils.packet(new C09PacketHeldItemChange(currentItem));
                    this.timer.reset();
                    break;
                }
                break;
            }
        }
    }
    
    protected void swap(final int slot, final int hotbarNum) {
        ClientUtils.playerController().windowClick(ClientUtils.player().inventoryContainer.windowId, slot, hotbarNum, 2, ClientUtils.player());
    }
    
    private int getPotionFromInventory() {
        int pot = -1;
        int counter = 0;
        for (int i = 1; i < 45; ++i) {
            if (ClientUtils.player().inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = ClientUtils.player().inventoryContainer.getSlot(i).getStack();
                final Item item = is.getItem();
                if (item instanceof ItemPotion) {
                    final ItemPotion potion = (ItemPotion)item;
                    if (potion.getEffects(is) != null) {
                        for (final Object o : potion.getEffects(is)) {
                            final PotionEffect effect = (PotionEffect)o;
                            if (effect.getPotionID() == Potion.heal.id && ItemPotion.isSplash(is.getItemDamage())) {
                                ++counter;
                                pot = i;
                            }
                        }
                    }
                }
            }
        }
        this.setSuffix(new StringBuilder(String.valueOf(counter)).toString());
        return pot;
    }
}
