package me.aristhena.crest.module.modules.misc;

import me.aristhena.crest.module.*;
import me.aristhena.crest.option.*;
import me.aristhena.event.events.*;
import me.aristhena.utils.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import me.aristhena.event.*;

@Module.Mod(displayName = "Inventory Cleaner")
public class InventoryCleaner extends Module
{
    @Option.Op(min = 0.0, max = 1000.0, increment = 50.0)
    private double delay;
    private Timer timer;
    
    public InventoryCleaner() {
        this.delay = 50.0;
        this.timer = new Timer();
    }
    
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        if (event.getState() == Event.State.POST) {
            final InventoryPlayer invp = ClientUtils.player().inventory;
            for (int i = 9; i < 45; ++i) {
                final ItemStack itemStack = ClientUtils.player().inventoryContainer.getSlot(i).getStack();
                if (itemStack != null) {
                    itemStack.getItem();
                    if (this.timer.delay((float)this.delay)) {
                        ClientUtils.playerController().windowClick(0, i, 0, 0, ClientUtils.player());
                        ClientUtils.playerController().windowClick(0, -999, 0, 0, ClientUtils.player());
                        this.timer.reset();
                    }
                }
            }
        }
    }
}
