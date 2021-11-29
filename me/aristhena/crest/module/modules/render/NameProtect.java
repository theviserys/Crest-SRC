package me.aristhena.crest.module.modules.render;

import me.aristhena.crest.module.*;
import me.aristhena.crest.option.*;

@Module.Mod(displayName = "Name Protect", shown = false)
public class NameProtect extends Module
{
    @Option.Op
    private boolean colored;
    
    public NameProtect() {
        this.colored = true;
    }
    
    public boolean getColored() {
        return this.colored;
    }
}
