package me.aristhena.crest.module.modules.misc;

import me.aristhena.crest.module.*;
import me.aristhena.event.events.*;
import me.aristhena.utils.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import me.aristhena.event.*;

@Module.Mod(displayName = "Screen Walk")
public class ScreenWalk extends Module
{
    @EventTarget
    private void onRender(final Render2DEvent event) {
        if (ClientUtils.mc().currentScreen != null && !(ClientUtils.mc().currentScreen instanceof GuiChat)) {
            if (Keyboard.isKeyDown(200)) {
                ClientUtils.pitch(ClientUtils.pitch() - 2.0f);
            }
            if (Keyboard.isKeyDown(208)) {
                ClientUtils.pitch(ClientUtils.pitch() + 2.0f);
            }
            if (Keyboard.isKeyDown(203)) {
                ClientUtils.yaw(ClientUtils.yaw() - 3.0f);
            }
            if (Keyboard.isKeyDown(205)) {
                ClientUtils.yaw(ClientUtils.yaw() + 3.0f);
            }
        }
    }
}
