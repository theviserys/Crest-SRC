package me.aristhena.crest.gui.account.component;

import me.aristhena.crest.gui.account.AccountScreen;
import me.aristhena.utils.RenderUtils;
import org.lwjgl.opengl.GL11;
import me.aristhena.utils.ClientUtils;

public class Button {
    private static int nullColor;
    public String text;
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private int fillColor;
    private int borderColor = 1280595028;
    private int fadeState = 0;
    private static final int MAX_FADE = 15;
    private int minHex;
    private int maxHex;
    private int mouseX;
    private int mouseY;

    public Button(String text, int x1, int x2, int y1, int y2, int minHex, int maxHex) {
        this.text = text;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.minHex = minHex;
        this.maxHex = maxHex;
    }

    public void draw(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.updateFade();
        RenderUtils.rectangle(this.x1, this.y1, this.x2, this.y2, (AccountScreen.getInstance().selectedButton != null && AccountScreen.getInstance().selectedButton.equals(this)) ? (this.isOver() ? -1275068416 : -1610612736) : (this.isOver() ? -1778384896 : 1677721600));        this.drawCenteredString(this.text, 1.2f);
    }

    public void drawCenteredString(String text, float scale) {
        boolean tooLong = false;
        while ((float)ClientUtils.clientFont().getStringWidth(text) * scale > (float)this.getWidth()) {
            text = text.substring(0, text.length() - 1);
            tooLong = true;
        }
        if (tooLong) {
            text = text.substring(0, text.length() - 4);
            text = String.valueOf(text) + "...";
        }
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        int strWidth = ClientUtils.clientFont().getStringWidth(text);
        strWidth = (int)((float)strWidth * scale);
        int x = this.x1 + this.getWidth() / 2 - strWidth / 2;
        int y = this.y1 + this.getHeight() / 2 - 4;
        x = (int)((float)x / scale);
        y = (int)((float)y / scale);
        ClientUtils.clientFont().drawStringWithShadow(text, x, y, -1);
        GL11.glScalef((float)(1.0f / scale), (float)(1.0f / scale), (float)(1.0f / scale));
    }

    public void updateFade() {
        if (this.isOver() && this.fadeState < 15) {
            ++this.fadeState;
        } else if (!this.isOver() && this.fadeState > 0) {
            --this.fadeState;
        }
        double ratio = (double)this.fadeState / 15.0;
        this.fillColor = this.getFadeHex(this.minHex, this.maxHex, ratio);
    }

    public void onClick(int button) {
    }

    public boolean isOver() {
        if (this.mouseX >= this.x1 && this.mouseX <= this.x2 && this.mouseY >= this.y1 && this.mouseY <= this.y2) {
            return true;
        }
        return false;
    }

    public int getFadeHex(int hex1, int hex2, double ratio) {
        int r = hex1 >> 16;
        int g = hex1 >> 8 & 255;
        int b = hex1 & 255;
        r = (int)((double)r + (double)((hex2 >> 16) - r) * ratio);
        g = (int)((double)g + (double)((hex2 >> 8 & 255) - g) * ratio);
        b = (int)((double)b + (double)((hex2 & 255) - b) * ratio);
        return r << 16 | g << 8 | b;
    }

    public int getWidth() {
        return this.x2 - this.x1;
    }

    public int getHeight() {
        return this.y2 - this.y1;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getBorderColor() {
        return this.borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public int getX1() {
        return this.x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return this.x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY1() {
        return this.y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getY2() {
        return this.y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public int getFillColor() {
        return this.fillColor;
    }
}

