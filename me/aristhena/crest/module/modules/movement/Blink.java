package me.aristhena.crest.module.modules.movement;

import me.aristhena.crest.module.*;
import net.minecraft.client.entity.*;
import net.minecraft.network.*;
import me.aristhena.crest.option.*;
import me.aristhena.utils.*;
import com.mojang.authlib.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import me.aristhena.event.events.*;
import net.minecraft.network.play.client.*;
import me.aristhena.event.*;
import java.util.*;

@Module.Mod
public class Blink extends Module
{
    private EntityOtherPlayerMP blinkEntity;
    private List<Packet> packetList;
    @Option.Op(name = "Delay Block Place")
    private boolean blockPlace;
    @Option.Op(name = "Delay Attack")
    private boolean attack;
    @Option.Op(name = "Delay All")
    private boolean all;
    
    public Blink() {
        this.packetList = new ArrayList<Packet>();
        this.blockPlace = true;
        this.attack = true;
    }
    
    @Override
    public void enable() {
        if (ClientUtils.player() == null) {
            return;
        }
        this.blinkEntity = new EntityOtherPlayerMP(ClientUtils.world(), new GameProfile(new UUID(69L, 96L), "Blink"));
        this.blinkEntity.inventory = ClientUtils.player().inventory;
        this.blinkEntity.inventoryContainer = ClientUtils.player().inventoryContainer;
        this.blinkEntity.setPositionAndRotation(ClientUtils.player().posX, ClientUtils.player().posY, ClientUtils.player().posZ, ClientUtils.player().rotationYaw, ClientUtils.player().rotationPitch);
        this.blinkEntity.rotationYawHead = ClientUtils.player().rotationYawHead;
        ClientUtils.world().addEntityToWorld(this.blinkEntity.getEntityId(), this.blinkEntity);
        super.enable();
    }
    
    @EventTarget
    private void onPacketSend(final PacketSendEvent event) {
        if (this.all || event.getPacket() instanceof C0BPacketEntityAction || event.getPacket() instanceof C03PacketPlayer || (this.attack && (event.getPacket() instanceof C02PacketUseEntity || event.getPacket() instanceof C0APacketAnimation)) || (this.blockPlace && event.getPacket() instanceof C08PacketPlayerBlockPlacement)) {
            this.packetList.add(event.getPacket());
            event.setCancelled(true);
        }
    }
    
    @Override
    public void disable() {
        super.disable();
        for (final Packet packet : this.packetList) {
            ClientUtils.packet(packet);
        }
        this.packetList.clear();
        ClientUtils.world().removeEntityFromWorld(this.blinkEntity.getEntityId());
    }
}
