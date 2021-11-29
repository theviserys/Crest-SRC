package me.aristhena.crest.module.modules.misc;

import me.aristhena.crest.module.*;

@Module.Mod(displayName = "More Inventory")
public class MoreInventory extends Module
{
    public static boolean cancelClose() {
        return new MoreInventory().getInstance().isEnabled();
    }
}
