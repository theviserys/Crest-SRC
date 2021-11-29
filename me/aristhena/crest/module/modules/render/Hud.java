package me.aristhena.crest.module.modules.render;

import me.aristhena.event.events.*;
import java.text.*;
import me.aristhena.utils.*;
import me.aristhena.crest.module.*;
import java.util.*;
import me.aristhena.event.*;

@Module.Mod(enabled = true, shown = false)
public class Hud extends Module
{
    @EventTarget
    private void onRender2D(final Render2DEvent event) {
        String time = new SimpleDateFormat("hh:mm a").format(new Date());
        if (time.startsWith("0")) {
            time = time.replaceFirst("0", "");
        }
        ClientUtils.clientFont().drawStringWithShadow("§4C§rrest §7" + time, 2.0, 2.0, 6908265);
        int y = 2;
        for (final Module mod : ModuleManager.getModulesForRender()) {
            if (mod.drawDisplayName((float)(event.getWidth() - ClientUtils.clientFont().getStringWidth(String.format("%s" + ((mod.getSuffix().length() > 0) ? " §4[%s]" : ""), mod.getDisplayName(), mod.getSuffix())) - 2), (float)y)) {
                y += 10;
            }
        }
    }
}
