package me.aristhena.crest.module.modules.misc;

import me.aristhena.crest.module.*;
import me.aristhena.event.events.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import me.aristhena.event.*;

@Module.Mod(displayName = "Anti Cactus")
public class AntiCactus extends Module
{
    @EventTarget
    private void onBoundingBox(final BoundingBoxEvent event) {
        if (event.getBlock() instanceof BlockCactus) {
            event.setBoundingBox(new AxisAlignedBB(event.getBlockPos().getX(), event.getBlockPos().getY(), event.getBlockPos().getZ(), event.getBlockPos().getX() + 1, event.getBoundingBox().maxY, event.getBlockPos().getZ() + 1));
        }
    }
}
