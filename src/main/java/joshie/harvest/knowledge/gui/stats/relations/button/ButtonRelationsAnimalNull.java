package joshie.harvest.knowledge.gui.stats.relations.button;

import joshie.harvest.core.base.gui.ButtonBook;
import joshie.harvest.knowledge.gui.stats.GuiStats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextFormatting;

public class ButtonRelationsAnimalNull extends ButtonBook {
    public ButtonRelationsAnimalNull(GuiStats gui, int buttonId, int x, int y) {
        super(gui, buttonId, x, y, "No animals nearby!");
        this.gui = gui;
        this.width = 120;
        this.height = 16;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (visible) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
            mouseDragged(mc, mouseX, mouseY);
            boolean flag = mc.fontRendererObj.getUnicodeFlag();
            mc.fontRendererObj.setUnicodeFlag(true);
            mc.fontRendererObj.drawString(TextFormatting.BOLD + displayString, xPosition + 8, yPosition - 4, 0x857754);
            mc.fontRendererObj.setUnicodeFlag(flag);
            GlStateManager.color(1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return false;
    }
}
