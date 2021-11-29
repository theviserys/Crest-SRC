package me.aristhena.crest.gui.click.component.slot;

import me.aristhena.crest.module.*;
import me.aristhena.crest.module.modules.render.*;
import me.aristhena.utils.*;
import me.aristhena.crest.gui.click.*;
import java.util.*;
import me.aristhena.crest.gui.click.component.window.*;

public class ModuleSlotComponent extends SlotComponent<Module>
{
    private ModuleWindow parentWindow;
    
    public ModuleSlotComponent(final Module parent, final double x, final double y, final double width, final double height, final ModuleWindow parentWindow) {
        super(parent, x, y, width, height);
        this.parentWindow = parentWindow;
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        final boolean useDarkTheme = ((Gui)new Gui().getInstance()).isDarkTheme();
        RenderUtils.rectangleBordered(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), 0.5, RenderUtils.blend(useDarkTheme ? -14540254 : -13421773, -16777216, this.getParent().isEnabled() ? (this.hovering(mouseX, mouseY) ? 0.6f : 0.7f) : (this.hovering(mouseX, mouseY) ? 0.8f : 1.0f)), -15658735);
        RenderUtils.rectangle(this.getX() + 1.0, this.getY() + 0.5, this.getX() + this.getWidth() - 1.0, this.getY() + 1.0, this.getParent().isEnabled() ? 536870912 : 553648127);
        ClientUtils.clientFont().drawCenteredString(this.getParent().getDisplayName(), this.getX() + this.getWidth() / 2.0, this.getY() + 0.5 + this.getHeight() / 2.0 - ClientUtils.clientFont().FONT_HEIGHT / 2, -1);
        if (this.getOptionWindow() != null) {
            this.getOptionWindow().draw(mouseX, mouseY);
        }
    }
    
    @Override
    public void click(final int mouseX, final int mouseY, final int button) {
        if (this.getOptionWindow() != null) {
            this.getOptionWindow().click(mouseX, mouseY, button);
        }
        if (this.hovering(mouseX, mouseY)) {
            if (button == 0) {
                this.getParent().toggle();
            }
            else if (button == 1) {
                if (this.getOptionWindow() == null) {
                    for (final Window window : ClickGui.getInstance().getWindows()) {
                        for (Object o : window.getSlotList()) {
                            SlotComponent slot = (SlotComponent)o;
                            slot.setOptionWindow(null);
                        }
                    }
                    this.setOptionWindow(new OptionWindow(this.getParent(), this.getX() + this.getWidth() + 4.0, this.parentWindow.getY(), this.getWidth(), this.parentWindow));
                }
                else {
                    this.setOptionWindow(null);
                }
            }
        }
    }
    
    public void drag(final double xDifference, final double yDifference, final double[] startOffset) {
        this.setX(this.getX() - xDifference);
        this.setY(this.getY() - yDifference);
        if (this.getOptionWindow() != null) {
            this.getOptionWindow().drag(xDifference, yDifference, startOffset);
        }
    }
    
    @Override
    public void drag(final int mouseX, final int mouseY, final int button) {
        if (this.getOptionWindow() != null) {
            this.getOptionWindow().drag(mouseX, mouseY, button);
        }
    }
    
    @Override
    public void release(final int mouseX, final int mouseY, final int button) {
    }
    
    @Override
    public void keyPress(final int keyInt, final char keyChar) {
        if (this.getOptionWindow() != null) {
            this.getOptionWindow().keyPress(keyInt, keyChar);
        }
    }
}
