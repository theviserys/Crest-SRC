package me.aristhena.crest.module.modules.player;

import me.aristhena.crest.module.*;
import me.aristhena.event.events.*;
import me.aristhena.utils.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import me.aristhena.event.*;

@Module.Mod(displayName = "Fast Use")
public class FastUse extends Module
{
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        if (event.getState() == Event.State.PRE && ClientUtils.player().getItemInUseDuration() == 16 && !(ClientUtils.player().getItemInUse().getItem() instanceof ItemBow)) {
            for (int i = 0; i < 17; ++i) {
                ClientUtils.packet(new C03PacketPlayer(true));
            }
            ClientUtils.packet(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
    }
}
