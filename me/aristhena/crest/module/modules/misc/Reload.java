package me.aristhena.crest.module.modules.misc;

import me.aristhena.utils.*;
import me.aristhena.crest.module.*;
import me.aristhena.crest.option.*;
import me.aristhena.crest.friend.*;
import me.aristhena.crest.gui.click.*;

@Module.Mod(displayName = "Load Config")
public class Reload extends Module
{
    @Override
    public void enable() {
        ClientUtils.mc().currentScreen = null;
        ModuleManager.load();
        OptionManager.load();
        FriendManager.load();
        ClickGui.getInstance().load();
    }
}
