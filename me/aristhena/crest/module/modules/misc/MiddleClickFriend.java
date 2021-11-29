package me.aristhena.crest.module.modules.misc;

import me.aristhena.crest.module.*;
import me.aristhena.event.events.MouseEvent;
import me.aristhena.utils.*;
import net.minecraft.entity.player.*;
import me.aristhena.crest.friend.*;
import me.aristhena.event.*;

@Module.Mod(displayName = "Middle Click Friend")
public class MiddleClickFriend extends Module
{
    @EventTarget
    private void onMouseClick(final MouseEvent event) {
        if (event.getKey() == 2 && ClientUtils.mc().objectMouseOver != null && ClientUtils.mc().objectMouseOver.entityHit != null && ClientUtils.mc().objectMouseOver.entityHit instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)ClientUtils.mc().objectMouseOver.entityHit;
            final String name = player.getName();
            if (FriendManager.isFriend(name)) {
                FriendManager.removeFriend(name);
                ClientUtils.sendMessage("Removed " + name);
            }
            else {
                FriendManager.addFriend(name, name);
                ClientUtils.sendMessage("Added " + name);
            }
        }
    }
}
