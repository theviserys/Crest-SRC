package me.aristhena.crest.gui.account.component;


import me.aristhena.crest.account.Alt;
import me.aristhena.crest.account.LoginThread;

public class AltButton extends Button
{
    private Alt alt;
    
    public AltButton(final String text, final int x1, final int x2, final int y1, final int y2, final int minHex, final int maxHex, final Alt alt) {
        super(text, x1, x2, y1, y2, minHex, maxHex);
        this.alt = alt;
    }
    
    @Override
    public void onClick(final int button) {
        final LoginThread thread = new LoginThread(this.alt);
        thread.start();
    }
    
    public Alt getAlt() {
        return this.alt;
    }
    
    public void setAlt(final Alt alt) {
        this.alt = alt;
    }
}
