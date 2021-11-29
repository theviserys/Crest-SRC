package net.minecraft.util;

import me.aristhena.crest.module.modules.misc.ScreenWalk;
import me.aristhena.utils.ClientUtils;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.GameSettings;
import org.lwjgl.input.Keyboard;

public class MovementInputFromOptions extends MovementInput
{
    private final GameSettings gameSettings;
    private static final String __OBFID = "CL_00000937";

    public MovementInputFromOptions(GameSettings p_i1237_1_)
    {
        this.gameSettings = p_i1237_1_;
    }

    public void updatePlayerMoveState() {
        if (new ScreenWalk().getInstance().isEnabled() && !(ClientUtils.mc().currentScreen instanceof GuiChat)) {
            this.moveStrafe = 0.0f;
            this.moveForward = 0.0f;
            if (Keyboard.isKeyDown(this.gameSettings.keyBindForward.getKeyCode())) {
                ++this.moveForward;
            }
            if (Keyboard.isKeyDown(this.gameSettings.keyBindBack.getKeyCode())) {
                --this.moveForward;
            }
            if (Keyboard.isKeyDown(this.gameSettings.keyBindLeft.getKeyCode())) {
                ++this.moveStrafe;
            }
            if (Keyboard.isKeyDown(this.gameSettings.keyBindRight.getKeyCode())) {
                --this.moveStrafe;
            }
            this.jump = Keyboard.isKeyDown(this.gameSettings.keyBindJump.getKeyCode());
            this.sneak = this.gameSettings.keyBindSneak.getIsKeyPressed();
            if (this.sneak) {
                this.moveStrafe *= (float) 0.3;
                this.moveForward *= (float) 0.3;
            }
        } else {
            this.moveStrafe = 0.0F;
            this.moveForward = 0.0F;

            if (this.gameSettings.keyBindForward.getIsKeyPressed()) {
                ++this.moveForward;
            }

            if (this.gameSettings.keyBindBack.getIsKeyPressed()) {
                --this.moveForward;
            }

            if (this.gameSettings.keyBindLeft.getIsKeyPressed()) {
                ++this.moveStrafe;
            }

            if (this.gameSettings.keyBindRight.getIsKeyPressed()) {
                --this.moveStrafe;
            }

            this.jump = this.gameSettings.keyBindJump.getIsKeyPressed();
            this.sneak = this.gameSettings.keyBindSneak.getIsKeyPressed();

            if (this.sneak) {
                this.moveStrafe = (float) ((double) this.moveStrafe * 0.3D);
                this.moveForward = (float) ((double) this.moveForward * 0.3D);
            }
        }
    }
}
