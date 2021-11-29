package me.aristhena.crest.gui.account.screen;

import me.aristhena.crest.account.AccountManager;
import me.aristhena.crest.account.Alt;
import me.aristhena.crest.gui.account.AccountScreen;
import me.aristhena.crest.gui.account.component.Button;
import me.aristhena.utils.RenderUtils;
import me.aristhena.utils.ClientUtils;
import me.aristhena.utils.minecraft.GuiTextField;

public class ScreenAddAccount extends Screen
{
    private AddButton addButton;
    private GuiTextField emailText;
    private GuiTextField passText;
    private GuiTextField nameText;
    
    public ScreenAddAccount() {
        this.addButton = new AddButton();
        this.emailText = new GuiTextField(-5, ClientUtils.clientFont(), ClientUtils.mc().currentScreen.width / 2 - 60, ClientUtils.mc().currentScreen.height / 2 - 13 - 80, 120, 26);
        this.passText = new GuiTextField(-4, ClientUtils.clientFont(), ClientUtils.mc().currentScreen.width / 2 - 60, ClientUtils.mc().currentScreen.height / 2 - 13 - 40, 120, 26);
        this.nameText = new GuiTextField(-3, ClientUtils.clientFont(), ClientUtils.mc().currentScreen.width / 2 - 60, ClientUtils.mc().currentScreen.height / 2 - 13, 120, 26);
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        if (!this.emailText.isFocused() && !this.emailText.getText().equals("\u00A77Username") && !this.emailText.getText().equals("\u00A7cCannot Be Blank") && this.emailText.getText().length() == 0) {
            this.emailText.setText("\u00A77Username");
        }
        if (!this.passText.isFocused() && !this.passText.getText().equals("\u00A77Password") && this.passText.getText().length() == 0) {
            this.passText.setText("\u00A77Password");
        }
        if (!this.nameText.isFocused() && !this.nameText.getText().equals("\u00A77Name") && this.nameText.getText().length() == 0) {
            this.nameText.setText("\u00A77Name");
        }
        RenderUtils.rectangle(0.0, 0.0, ClientUtils.mc().currentScreen.width, ClientUtils.mc().currentScreen.height, -804253680);
        this.addButton.draw(mouseX, mouseY);
        this.emailText.drawTextBox();
        this.passText.drawTextBox();
        this.nameText.drawTextBox();
    }
    
    @Override
    public void onClick(final int x, final int y, final int mouseButton) {
        if (this.emailText.getText().equals("\u00A77Username") || this.emailText.getText().equals("\u00A7cCannot Be Blank")) {
            this.emailText.setText("");
        }
        if (this.passText.getText().equals("\u00A77Password")) {
            this.passText.setText("");
        }
        if (this.nameText.getText().equals("\u00A77Name")) {
            this.nameText.setText("");
        }
        if (this.addButton.isOver()) {
            this.addButton.onClick(mouseButton);
        }
        this.emailText.mouseClicked(x, y, mouseButton);
        this.passText.mouseClicked(x, y, mouseButton);
        this.nameText.mouseClicked(x, y, mouseButton);
    }
    
    @Override
    public void onKeyPress(final char c, final int key) {
        this.emailText.textboxKeyTyped(c, key);
        this.passText.textboxKeyTyped(c, key);
        this.nameText.textboxKeyTyped(c, key);
    }
    
    @Override
    public void update() {
        this.emailText.updateCursorCounter();
        this.passText.updateCursorCounter();
        this.nameText.updateCursorCounter();
    }
    
    private class AddButton extends Button
    {
        public AddButton() {
            super("Add Account", ClientUtils.mc().currentScreen.width / 2 - 40, ClientUtils.mc().currentScreen.width / 2 + 40, ClientUtils.mc().currentScreen.height / 2 - 13 + 40, ClientUtils.mc().currentScreen.height / 2 + 13 + 40, -15921907, -16777216);
        }
        
        @Override
        public void onClick(final int button) {
            if (ScreenAddAccount.this.emailText.getText().length() == 0 || ScreenAddAccount.this.emailText.getText().equals("\u00A77Username") || ScreenAddAccount.this.emailText.getText().equals("\u00A7cCannot Be Blank")) {
                ScreenAddAccount.this.emailText.setText("\u00A7cCannot Be Blank");
                return;
            }
            AccountManager.addAlt(0, new Alt(ScreenAddAccount.this.emailText.getText(), ScreenAddAccount.this.nameText.getText(), ScreenAddAccount.this.passText.getText()));
            AccountScreen.getInstance().currentScreen = null;
            AccountManager.save();
            AccountScreen.getInstance().initGui();
            AccountScreen.getInstance().info = "\u00A7aAlt Added";
        }
    }
}
