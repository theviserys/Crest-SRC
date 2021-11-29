package me.aristhena.crest.module.modules.render;

import me.aristhena.crest.module.*;
import me.aristhena.crest.option.*;
import me.aristhena.utils.*;
import me.aristhena.crest.gui.click.*;
import net.minecraft.client.gui.*;

@Module.Mod(displayName = "Click Gui", keybind = 54, shown = false)
public class Gui extends Module
{
    @Option.Op(name = "Dark Theme")
    private boolean darkTheme;
    
    @Override
    public void enable() {
        ClientUtils.mc().displayGuiScreen(ClickGui.getInstance());
    }
    
    public boolean isDarkTheme() {
        return this.darkTheme;
    }
}
