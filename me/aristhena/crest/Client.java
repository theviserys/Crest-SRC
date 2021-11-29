package me.aristhena.crest;

import me.aristhena.utils.*;
import me.aristhena.crest.module.*;
import me.aristhena.crest.command.*;
import me.aristhena.crest.option.*;
import me.aristhena.crest.friend.*;
import me.aristhena.crest.gui.click.*;

public final class Client
{
    public static final String NAME = "Crest";
    public static final double VERSION = 1.6;
    
    private Client() {
    }
    
    public static void start() {
        ClientUtils.loadClientFont();
        ModuleManager.start();
        CommandManager.start();
        OptionManager.start();
        FriendManager.start();
        ClickGui.start();
    }
}
