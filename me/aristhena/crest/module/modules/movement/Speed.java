package me.aristhena.crest.module.modules.movement;

import me.aristhena.crest.module.*;
import me.aristhena.crest.module.modules.movement.speed.*;
import me.aristhena.crest.option.*;
import me.aristhena.event.*;
import me.aristhena.event.events.*;
import me.aristhena.utils.*;
import net.minecraft.potion.*;

@Module.Mod
public class Speed extends Module
{
    public Latest latest;
    public Bhop bhop;
    private Vanilla vanilla;
    private Minez minez;
    @Option.Op(min = 0.2, max = 10.0, increment = 0.05)
    public double speed;
    
    public Speed() {
        this.latest = new Latest("Latest", true, this);
        this.bhop = new Bhop("Bhop", false, this);
        this.vanilla = new Vanilla("Vanilla", false, this);
        this.minez = new Minez("Minez", false, this);
        this.speed = 0.5;
    }
    
    @Override
    public void postInitialize() {
        OptionManager.getOptionList().add(this.latest);
        OptionManager.getOptionList().add(this.bhop);
        OptionManager.getOptionList().add(this.vanilla);
        OptionManager.getOptionList().add(this.minez);
        this.updateSuffix();
        super.postInitialize();
    }
    
    @Override
    public void enable() {
        this.latest.enable();
        this.bhop.enable();
        this.vanilla.enable();
        this.minez.enable();
        super.enable();
    }
    
    @EventTarget
    private void onMove(final MoveEvent event) {
        this.latest.onMove(event);
        this.bhop.onMove(event);
        this.vanilla.onMove(event);
        this.minez.onMove(event);
    }
    
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        this.latest.onUpdate(event);
        this.bhop.onUpdate(event);
        this.vanilla.onUpdate(event);
        this.minez.onUpdate(event);
    }
    
    @EventTarget
    private void onTick(final TickEvent event) {
        this.updateSuffix();
    }
    
    private void updateSuffix() {
        if (this.latest.getValue()) {
            this.setSuffix("Latest");
        }
        else if (this.bhop.getValue()) {
            this.setSuffix("Bhop");
        }
        else if (this.vanilla.getValue()) {
            this.setSuffix("Vanilla");
        }
        else {
            this.setSuffix("Minez");
        }
    }
    
    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (ClientUtils.player().isPotionActive(Potion.moveSpeed)) {
            final int amplifier = ClientUtils.player().getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
}
