package me.aristhena.crest.module.modules.render;

import me.aristhena.crest.module.*;
import me.aristhena.crest.option.*;
import me.aristhena.event.*;
import me.aristhena.event.events.*;
import me.aristhena.utils.*;
import java.util.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import me.aristhena.crest.friend.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;

@Module.Mod(shown = false)
public class ESP extends Module
{
    @Option.Op
    private boolean players;
    @Option.Op
    private boolean monsters;
    @Option.Op
    private boolean animals;
    @Option.Op
    private boolean outline;
    private int state;
    private float r;
    private float g;
    private float b;
    private static float[] rainbowArray;
    
    public ESP() {
        this.players = true;
        this.r = 0.33f;
        this.g = 0.34f;
        this.b = 0.33f;
    }
    
    @EventTarget
    private void onPreUpdate(final UpdateEvent event) {
        if (event.getState() == Event.State.PRE) {
            ESP.rainbowArray = this.getRainbow();
        }
    }
    
    @EventTarget(0)
    private void onRender3D(final Render3DEvent event) {
        if (this.outline) {
            return;
        }
        for (final Object o : ClientUtils.world().loadedEntityList) {
            if (o instanceof EntityLivingBase && o != ClientUtils.mc().thePlayer) {
                final EntityLivingBase entity = (EntityLivingBase)o;
                int color = -16777216;
                int thingyt = 1174405120;
                if (entity.hurtTime != 0) {
                    color = -6750208;
                    thingyt = 1184432128;
                }
                if (!this.checkValidity(entity)) {
                    continue;
                }
                RenderUtils.drawEsp(entity, event.getPartialTicks(), color, thingyt);
            }
        }
    }
    
    public void renderOne(final Entity ent) {
        if (!this.checkValidity((EntityLivingBase)ent)) {
            return;
        }
        GL11.glPushAttrib(1048575);
        GL11.glDisable(3008);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(0.2f);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glEnable(2960);
        GL11.glClear(1024);
        GL11.glClearStencil(15);
        GL11.glStencilFunc(512, 1, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        this.colorLines(ent);
        GL11.glLineWidth(1.5f);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6913);
    }
    
    private void colorLines(final Entity ent) {
        int color = 13762557;
        if (FriendManager.isFriend(ent.getName())) {
            color = 3394815;
        }
        this.color(color, 1.0f);
    }
    
    private void color(final int color, final float alpha) {
        final int hex = color;
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }
    
    public void renderTwo(final Entity ent) {
        if (!this.checkValidity((EntityLivingBase)ent)) {
            return;
        }
        GL11.glStencilFunc(512, 0, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6914);
    }
    
    public void renderThree(final Entity ent) {
        if (!this.checkValidity((EntityLivingBase)ent)) {
            return;
        }
        GL11.glStencilFunc(514, 1, 15);
        GL11.glStencilOp(7680, 7680, 7680);
        GL11.glPolygonMode(1032, 6913);
    }
    
    public void renderFour(final Entity ent) {
        if (!this.checkValidity((EntityLivingBase)ent)) {
            return;
        }
        GL11.glEnable(10754);
        GL11.glPolygonOffset(1.0f, -2000000.0f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
    }
    
    public void renderFive(final Entity ent) {
        if (!this.checkValidity((EntityLivingBase)ent)) {
            return;
        }
        GL11.glPolygonOffset(1.0f, 2000000.0f);
        GL11.glDisable(10754);
        GL11.glDisable(2960);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        GL11.glPopAttrib();
    }
    
    private boolean checkValidity(final EntityLivingBase entity) {
        if (entity instanceof EntityOtherPlayerMP) {
            return this.players;
        }
        return (this.monsters && entity instanceof EntityMob) || (this.animals && entity instanceof EntityAnimal) || (this.animals && entity instanceof EntityBat);
    }
    
    private float[] getRainbow() {
        if (this.state == 0) {
            this.r += (float)0.02;
            this.b -= (float)0.02;
            if (this.r >= 0.85) {
                ++this.state;
            }
        }
        else if (this.state == 1) {
            this.g += (float)0.02;
            this.r -= (float)0.02;
            if (this.g >= 0.85) {
                ++this.state;
            }
        }
        else {
            this.b += (float)0.02;
            this.g -= (float)0.02;
            if (this.b >= 0.85) {
                this.state = 0;
            }
        }
        return new float[] { this.r, this.g, this.b };
    }
    
    public boolean isOutline() {
        return this.outline;
    }
}
