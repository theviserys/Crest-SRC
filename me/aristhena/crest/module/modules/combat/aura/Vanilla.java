package me.aristhena.crest.module.modules.combat.aura;

import me.aristhena.crest.module.*;
import me.aristhena.event.events.*;
import me.aristhena.crest.module.modules.combat.*;
import me.aristhena.utils.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.util.*;
import me.aristhena.event.*;

public class Vanilla extends AuraMode
{
    public Vanilla(final String name, final boolean value, final Module module) {
        super(name, value, module);
    }
    
    @Override
    public boolean onUpdate(final UpdateEvent event) {
        if (super.onUpdate(event)) {
            switch (event.getState()) {
                case PRE: {
                    final Aura auraModule = (Aura)this.getModule();
                    boolean targets = false;
                    for (final Entity entity : ClientUtils.loadedEntityList()) {
                        if (auraModule.isEntityValid(entity)) {
                            targets = true;
                        }
                    }
                    if (targets && auraModule.criticals) {
                        ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + 0.001, ClientUtils.z(), true));
                        event.setGround(false);
                        event.setAlwaysSend(true);
                    }
                    if (targets && auraModule.autoblock && ClientUtils.player().getCurrentEquippedItem() != null && ClientUtils.player().getCurrentEquippedItem().getItem() instanceof ItemSword) {
                        ClientUtils.playerController().sendUseItem(ClientUtils.player(), ClientUtils.world(), ClientUtils.player().getCurrentEquippedItem());
                    }
                    for (final Entity entity : ClientUtils.loadedEntityList()) {
                        if (auraModule.isEntityValid(entity)) {
                            auraModule.attack((EntityLivingBase)entity);
                        }
                    }
                    break;
                }
            }
        }
        return true;
    }
}
