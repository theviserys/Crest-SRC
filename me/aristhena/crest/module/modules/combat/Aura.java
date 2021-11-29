package me.aristhena.crest.module.modules.combat;

import me.aristhena.crest.module.*;
import me.aristhena.crest.module.modules.combat.aura.*;
import me.aristhena.crest.option.*;
import me.aristhena.event.*;
import me.aristhena.utils.*;
import me.aristhena.event.events.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import me.aristhena.crest.friend.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.potion.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

@Module.Mod
public class Aura extends Module
{
    private Switch switchMode;
    private Vanilla vanilla;
    private Single single;
    @Option.Op(min = 0.0, max = 20.0, increment = 0.25)
    public double speed;
    @Option.Op(min = 0.1, max = 7.0, increment = 0.1)
    private double range;
    @Option.Op(min = 0.0, max = 180.0, increment = 5.0)
    public double degrees;
    @Option.Op(name = "Ticks Existed", min = 0.0, max = 25.0, increment = 1.0)
    public double ticksExisted;
    @Option.Op
    private boolean players;
    @Option.Op
    private boolean monsters;
    @Option.Op
    private boolean animals;
    @Option.Op
    private boolean bats;
    @Option.Op
    private boolean villagers;
    @Option.Op
    private boolean golems;
    @Option.Op
    private boolean noswing;
    @Option.Op
    public boolean noslowdown;
    @Option.Op
    public boolean criticals;
    @Option.Op(name = "Auto Block")
    public boolean autoblock;
    @Option.Op(name = "Armor Check")
    private boolean armorCheck;
    @Option.Op(name = "Attack Friends")
    private boolean attackFriends;
    private boolean jumpNextTick;
    
    public Aura() {
        this.switchMode = new Switch("Switch", true, this);
        this.vanilla = new Vanilla("Vanilla", false, this);
        this.single = new Single("Single", false, this);
        this.speed = 8.0;
        this.range = 4.2;
        this.degrees = 90.0;
        this.ticksExisted = 10.0;
        this.players = true;
    }
    
    @Override
    public void postInitialize() {
        OptionManager.getOptionList().add(this.switchMode);
        OptionManager.getOptionList().add(this.vanilla);
        OptionManager.getOptionList().add(this.single);
        this.updateSuffix();
        super.postInitialize();
    }
    
    @Override
    public void enable() {
        this.single.enable();
        super.enable();
    }
    
    @EventTarget
    private void onJump(final JumpEvent event) {
        if (StateManager.offsetLastPacketAura()) {
            event.setCancelled(this.jumpNextTick = true);
        }
    }
    
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        if (this.jumpNextTick && !StateManager.offsetLastPacketAura()) {
            ClientUtils.player().jump();
            this.jumpNextTick = false;
        }
        this.vanilla.onUpdate(event);
        this.switchMode.onUpdate(event);
        this.single.onUpdate(event);
    }
    
    @EventTarget
    private void onTick(final TickEvent event) {
        this.updateSuffix();
    }
    
    private void updateSuffix() {
        if (this.switchMode.getValue()) {
            this.setSuffix("Switch");
        }
        else if (this.vanilla.getValue()) {
            this.setSuffix("Vanilla");
        }
        else {
            this.setSuffix("Single");
        }
    }
    
    public boolean isEntityValid(final Entity entity) {
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLiving = (EntityLivingBase)entity;
            if (!ClientUtils.player().isEntityAlive() || !entityLiving.isEntityAlive() || entityLiving.getDistanceToEntity(ClientUtils.player()) > (ClientUtils.player().canEntityBeSeen(entityLiving) ? this.range : 3.0)) {
                return false;
            }
            if (entityLiving.ticksExisted < this.ticksExisted) {
                return false;
            }
            if (this.players && entityLiving instanceof EntityPlayer) {
                final EntityPlayer entityPlayer = (EntityPlayer)entityLiving;
                return (!FriendManager.isFriend(entityPlayer.getName()) || this.attackFriends) && (!this.armorCheck || this.hasArmor(entityPlayer));
            }
            if (this.monsters && (entityLiving instanceof EntityMob || entityLiving instanceof EntityGhast || entityLiving instanceof EntityDragon || entityLiving instanceof EntityWither || entityLiving instanceof EntitySlime || (entityLiving instanceof EntityWolf && ((EntityWolf)entityLiving).getOwner() != ClientUtils.player()))) {
                return true;
            }
            if (this.golems && entityLiving instanceof EntityGolem) {
                return true;
            }
            if (this.animals && (entityLiving instanceof EntityAnimal || entityLiving instanceof EntitySquid)) {
                return true;
            }
            if (this.bats && entityLiving instanceof EntityBat) {
                return true;
            }
            if (this.villagers && entityLiving instanceof EntityVillager) {
                return true;
            }
        }
        return false;
    }
    
    private boolean hasArmor(final EntityPlayer player) {
        final ItemStack boots = player.inventory.armorInventory[0];
        final ItemStack pants = player.inventory.armorInventory[1];
        final ItemStack chest = player.inventory.armorInventory[2];
        final ItemStack head = player.inventory.armorInventory[3];
        return boots != null || pants != null || chest != null || head != null;
    }
    
    public void attack(final EntityLivingBase entity) {
        this.attack(entity, this.criticals);
    }
    
    public void attack(final EntityLivingBase entity, final boolean crit) {
        this.swingItem();
        final float sharpLevel = EnchantmentHelper.func_152377_a(ClientUtils.player().getHeldItem(), entity.getCreatureAttribute());
        final boolean vanillaCrit = ClientUtils.player().fallDistance > 0.0f && !ClientUtils.player().onGround && !ClientUtils.player().isOnLadder() && !ClientUtils.player().isInWater() && !ClientUtils.player().isPotionActive(Potion.blindness) && ClientUtils.player().ridingEntity == null;
        ClientUtils.packet(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
        if (crit || vanillaCrit) {
            ClientUtils.player().onCriticalHit(entity);
        }
        if (sharpLevel > 0.0f) {
            ClientUtils.player().onEnchantmentCritical(entity);
        }
    }
    
    private void swingItem() {
        if (!this.noswing) {
            ClientUtils.player().swingItem();
        }
    }
    
    @Override
    public void disable() {
        StateManager.setOffsetLastPacketAura(false);
        super.disable();
    }
}
