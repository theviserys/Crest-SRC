package me.aristhena.crest.module.modules.render;

import java.util.ArrayList;
import java.util.List;
import me.aristhena.crest.friend.FriendManager;
import me.aristhena.crest.module.Module;
import me.aristhena.crest.module.Module.Mod;
import me.aristhena.crest.option.Option.Op;
import me.aristhena.event.EventTarget;
import me.aristhena.event.events.NametagRenderEvent;
import me.aristhena.event.events.Render3DEvent;
import me.aristhena.utils.ClientUtils;
import me.aristhena.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

@Mod(shown = false)
public class Tags extends Module {
    @Op(min = 0.01D, max = 0.2D, increment = 0.01D)
    private double scale = 0.1D;

    @Op
    private boolean armor = true;

    @EventTarget(3)
    private void onRender3DEvent(Render3DEvent event) {
        GlStateManager.pushMatrix();
        for (Object o : (ClientUtils.mc()).theWorld.loadedEntityList) {
            Entity ent = (Entity) o;
            if (ent == (ClientUtils.mc()).thePlayer)
                continue;
            if (ent instanceof EntityPlayer) {
                double posX = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * event.getPartialTicks() - RenderManager.renderPosX;
                double posY = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * event.getPartialTicks() - RenderManager.renderPosY + ent.height + 0.5D;
                double posZ = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * event.getPartialTicks() - RenderManager.renderPosZ;
                String str = ent.getDisplayName().getFormattedText();
                if (FriendManager.isFriend(ent.getName()))
                    str = str.replace(ent.getDisplayName().getFormattedText(), "" + FriendManager.getAliasName(ent.getName()));
                String colorString = "§";
                double health = MathUtils.roundToPlace(((EntityPlayer)ent).getHealth(), 2);
                if (health >= 12.0D) {
                    colorString = String.valueOf(colorString) + "2";
                } else if (health >= 4.0D) {
                    colorString = String.valueOf(colorString) + "6";
                } else {
                    colorString = String.valueOf(colorString) + "4";
                }
                str = String.valueOf(str) + " | " + colorString + health;
                float dist = (ClientUtils.mc()).thePlayer.getDistanceToEntity(ent);
                float scale = 0.02672F;
                float factor = (float)((dist <= 8.0F) ? (8.0D * this.scale) : (dist * this.scale));
                scale *= factor;
                GlStateManager.pushMatrix();
                GlStateManager.disableDepth();
                GlStateManager.translate(posX, posY, posZ);
                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(-(ClientUtils.mc()).renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef((ClientUtils.mc()).renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
                GlStateManager.scale(-scale, -scale, scale);
                GlStateManager.disableLighting();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                Tessellator tessellator = Tessellator.getInstance();
                WorldRenderer worldRenderer = tessellator.getWorldRenderer();
                GlStateManager.disableTexture2D();
                worldRenderer.startDrawingQuads();
                int stringWidth = ClientUtils.clientFont().getStringWidth(str) / 2;
                GL11.glColor3f(0.0F, 0.0F, 0.0F);
                GL11.glLineWidth(1.0E-6F);
                GL11.glBegin(3);
                GL11.glVertex2d((-stringWidth - 2), -0.8D);
                GL11.glVertex2d((-stringWidth - 2), 8.8D);
                GL11.glVertex2d((-stringWidth - 2), 8.8D);
                GL11.glVertex2d((stringWidth + 2), 8.8D);
                GL11.glVertex2d((stringWidth + 2), 8.8D);
                GL11.glVertex2d((stringWidth + 2), -0.8D);
                GL11.glVertex2d((stringWidth + 2), -0.8D);
                GL11.glVertex2d((-stringWidth - 2), -0.8D);
                GL11.glEnd();
                worldRenderer.setColorRGBA_I(0, 100);
                worldRenderer.addVertex((-stringWidth - 2), -0.8D, 0.0D);
                worldRenderer.addVertex((-stringWidth - 2), 8.8D, 0.0D);
                worldRenderer.addVertex((stringWidth + 2), 8.8D, 0.0D);
                worldRenderer.addVertex((stringWidth + 2), -0.8D, 0.0D);
                tessellator.draw();
                GlStateManager.enableTexture2D();
                ClientUtils.clientFont().drawString(str, (-ClientUtils.clientFont().getStringWidth(str) / 2), 0.0D, -1);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
                if (this.armor && ent instanceof EntityPlayer) {
                    List<ItemStack> itemsToRender = new ArrayList<>();
                    ItemStack hand = ((EntityPlayer)ent).getEquipmentInSlot(0);
                    if (hand != null)
                        itemsToRender.add(hand);
                    for (int i = 4; i > 0; i--) {
                        ItemStack stack = ((EntityPlayer)ent).getEquipmentInSlot(i);
                        if (stack != null)
                            itemsToRender.add(stack);
                    }
                    int x = -(itemsToRender.size() * 8);
                    for (ItemStack stack : itemsToRender) {
                        GlStateManager.disableDepth();
                        RenderHelper.enableGUIStandardItemLighting();
                        (ClientUtils.mc().getRenderItem()).zLevel = -100.0F;
                        ClientUtils.mc().getRenderItem().renderItemIntoGUI(stack, x, -18, false);
                        ClientUtils.mc().getRenderItem().renderItemOverlays((Minecraft.getMinecraft()).fontRendererObj, stack, x, -18);
                        (ClientUtils.mc().getRenderItem()).zLevel = 0.0F;
                        RenderHelper.disableStandardItemLighting();
                        GlStateManager.enableDepth();
                        String text = "";
                        if (stack != null) {
                            int y = 0;
                            int sLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
                            int fLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
                            int kLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, stack);
                            if (sLevel > 0) {
                                GL11.glDisable(2896);
                                drawEnchantTag("Sh" + sLevel, x, y);
                                y -= 9;
                            }
                            if (fLevel > 0) {
                                GL11.glDisable(2896);
                                drawEnchantTag("Fir" + fLevel, x, y);
                                y -= 9;
                            }
                            if (kLevel > 0) {
                                GL11.glDisable(2896);
                                drawEnchantTag("Kb" + kLevel, x, y);
                            } else if (stack.getItem() instanceof net.minecraft.item.ItemArmor) {
                                int pLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack);
                                int tLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack);
                                int uLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
                                if (pLevel > 0) {
                                    GL11.glDisable(2896);
                                    drawEnchantTag("P" + pLevel, x, y);
                                    y -= 9;
                                }
                                if (tLevel > 0) {
                                    GL11.glDisable(2896);
                                    drawEnchantTag("Th" + tLevel, x, y);
                                    y -= 9;
                                }
                                if (uLevel > 0) {
                                    GL11.glDisable(2896);
                                    drawEnchantTag("Unb" + uLevel, x, y);
                                }
                            } else if (stack.getItem() instanceof net.minecraft.item.ItemBow) {
                                int powLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
                                int punLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
                                int fireLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack);
                                if (powLevel > 0) {
                                    GL11.glDisable(2896);
                                    drawEnchantTag("Pow" + powLevel, x, y);
                                    y -= 9;
                                }
                                if (punLevel > 0) {
                                    GL11.glDisable(2896);
                                    drawEnchantTag("Pun" + punLevel, x, y);
                                    y -= 9;
                                }
                                if (fireLevel > 0) {
                                    GL11.glDisable(2896);
                                    drawEnchantTag("Fir" + fireLevel, x, y);
                                }
                            } else if (stack.getRarity() == EnumRarity.EPIC) {
                                drawEnchantTag("", x, y);
                            }
                            x += 16;
                        }
                    }
                }
                GlStateManager.disableLighting();
                GlStateManager.popMatrix();
            }
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
    }

    private static void drawEnchantTag(String text, int x, int y) {
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        x = (int)(x * 1.75D);
        y -= 4;
        GL11.glScalef(0.57F, 0.57F, 0.57F);
        ClientUtils.clientFont().drawStringWithShadow(text, x, (-36 - y), -1286);
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }

    @EventTarget
    private void onNametagRender(NametagRenderEvent event) {
        event.setCancelled(true);
    }
}
