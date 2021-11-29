package me.aristhena.crest.gui.click.component.slot.option.types;

import me.aristhena.crest.module.*;
import me.aristhena.crest.option.types.*;
import me.aristhena.crest.module.modules.render.*;
import me.aristhena.utils.*;

public class VisibilityOptionSlot extends BooleanOptionSlot
{
    private Module module;
    
    public VisibilityOptionSlot(final Module module, final double x, final double y, final double width, final double height) {
        super(null, x, y, width, height);
        this.module = module;
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        final boolean useDarkTheme = ((Gui)new Gui().getInstance()).isDarkTheme();
        RenderUtils.rectangleBordered(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), 0.5, RenderUtils.blend(useDarkTheme ? -14540254 : -13421773, -16777216, this.module.isShown() ? (this.hovering(mouseX, mouseY) ? 0.8f : 1.0f) : (this.hovering(mouseX, mouseY) ? 0.6f : 0.7f)), -15658735);
        RenderUtils.rectangle(this.getX() + 1.0, this.getY() + 0.5, this.getX() + this.getWidth() - 1.0, this.getY() + 1.0, this.module.isShown() ? 553648127 : 536870912);
        ClientUtils.clientFont().drawCenteredString("Hidden", this.getX() + this.getWidth() / 2.0, this.getY() + 0.5 + this.getHeight() / 2.0 - ClientUtils.clientFont().FONT_HEIGHT / 2, -1);
    }
    
    @Override
    public void click(final int mouseX, final int mouseY, final int button) {
        if (button == 0) {
            this.module.setShown(!this.module.isShown());
        }
    }
    
    @Override
    public void drag(final int mouseX, final int mouseY, final int button) {
    }
    
    @Override
    public void release(final int mouseX, final int mouseY, final int button) {
    }
    
    @Override
    public void keyPress(final int keyInt, final char keyChar) {
    }
}
