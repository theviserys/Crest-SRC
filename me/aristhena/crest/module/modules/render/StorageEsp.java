package me.aristhena.crest.module.modules.render;

import me.aristhena.crest.module.*;
import me.aristhena.crest.option.*;
import me.aristhena.event.events.*;
import net.minecraft.tileentity.*;
import java.util.*;
import me.aristhena.event.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.util.*;
import java.awt.*;
import me.aristhena.utils.*;
import net.minecraft.client.renderer.*;

@Module.Mod(displayName = "Storage Esp", shown = false)
public class StorageEsp extends Module
{
    @Option.Op
    public boolean chest;
    @Option.Op
    public boolean dispenser;
    @Option.Op
    public boolean enderChest;
    
    public StorageEsp() {
        this.chest = true;
    }
    
    @EventTarget
    private void onRender(final Render3DEvent event) {
        GlStateManager.pushMatrix();
        for (Object o : ClientUtils.world().loadedTileEntityList) {
            TileEntity ent = (TileEntity)o;
            if (!(ent instanceof TileEntityChest) && !(ent instanceof TileEntityDispenser) && !(ent instanceof TileEntityEnderChest)) {
                continue;
            }
            if (ent instanceof TileEntityChest && !this.chest) {
                continue;
            }
            if (ent instanceof TileEntityDispenser && !this.dispenser) {
                continue;
            }
            if (ent instanceof TileEntityEnderChest && !this.enderChest) {
                continue;
            }
            this.drawEsp(ent, event.getPartialTicks());
        }
        GlStateManager.popMatrix();
    }
    
    private void drawEsp(final TileEntity ent, final float pTicks) {
        final double x1 = ent.getPos().getX() - RenderManager.renderPosX;
        final double y1 = ent.getPos().getY() - RenderManager.renderPosY;
        final double z1 = ent.getPos().getZ() - RenderManager.renderPosZ;
        final float[] color = this.getColor(ent);
        AxisAlignedBB box = new AxisAlignedBB(x1, y1, z1, x1 + 1.0, y1 + 1.0, z1 + 1.0);
        if (ent instanceof TileEntityChest) {
            final TileEntityChest chest = TileEntityChest.class.cast(ent);
            if (chest.adjacentChestZPos != null) {
                box = new AxisAlignedBB(x1 + 0.0625, y1, z1 + 0.0625, x1 + 0.9375, y1 + 0.875, z1 + 1.9375);
            }
            else if (chest.adjacentChestXPos != null) {
                box = new AxisAlignedBB(x1 + 0.0625, y1, z1 + 0.0625, x1 + 1.9375, y1 + 0.875, z1 + 0.9375);
            }
            else {
                if (chest.adjacentChestZPos != null || chest.adjacentChestXPos != null || chest.adjacentChestZNeg != null || chest.adjacentChestXNeg != null) {
                    return;
                }
                box = new AxisAlignedBB(x1 + 0.0625, y1, z1 + 0.0625, x1 + 0.9375, y1 + 0.875, z1 + 0.9375);
            }
        }
        else if (ent instanceof TileEntityEnderChest) {
            box = new AxisAlignedBB(x1 + 0.0625, y1, z1 + 0.0625, x1 + 0.9375, y1 + 0.875, z1 + 0.9375);
        }
        RenderUtils.filledBox(box, new Color(color[0], color[1], color[2]).getRGB() & 0x19FFFFFF, true);
        RenderGlobal.drawOutlinedBoundingBox(box, new Color(color[0], color[1], color[2]).getRGB());
    }
    
    private float[] getColor(final TileEntity ent) {
        if (ent instanceof TileEntityChest) {
            return new float[] { 0.0f, 0.9f, 0.9f };
        }
        if (ent instanceof TileEntityDispenser) {
            return new float[] { 0.5f, 0.5f, 0.5f };
        }
        if (ent instanceof TileEntityEnderChest) {
            return new float[] { 0.3f, 0.0f, 0.3f };
        }
        return new float[] { 1.0f, 1.0f, 1.0f };
    }
}
