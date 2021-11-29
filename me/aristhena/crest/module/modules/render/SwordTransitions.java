package me.aristhena.crest.module.modules.render;

import me.aristhena.crest.module.*;
import me.aristhena.crest.option.*;

@Module.Mod(displayName = "Sword Transitions")
public class SwordTransitions extends Module
{
    @Option.Op(min = -2.0, max = 2.0, increment = 0.1)
    private double x;
    @Option.Op(min = -2.0, max = 2.0, increment = 0.1)
    private double y;
    @Option.Op(min = -2.0, max = 2.0, increment = 0.1)
    private double z;
    
    public SwordTransitions() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
}
