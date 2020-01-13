package infstudio.realnetwork.gui;

import infstudio.realnetwork.RealNetwork;
import infstudio.realnetwork.container.ContainerAmmeter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiAmmeter extends GuiMachineBase {

    private ContainerAmmeter container;

    public GuiAmmeter(ContainerAmmeter inventorySlotsIn) {
        super(inventorySlotsIn);
        this.texture = new ResourceLocation(RealNetwork.MODID+":textures/gui/container/ammeter.png");
        this.frontTexture = new ResourceLocation(RealNetwork.MODID+":textures/blocks/machine/ammeter_front.png");
        this.container = inventorySlotsIn;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int offsetX = (this.width-this.xSize)/2, offsetY = (this.height-this.ySize)/2;
        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
    }

    private void drawNumber(int index, int number) {
        drawTexturedModalRect(81+index*16, 17, number*15, 166, 14, 30);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(texture);
        int I = container.getI();
        drawNumber(0, I/10000);
        drawNumber(1, (I/1000)%10);
        drawNumber(2, (I/100)%10);
        drawNumber(3, (I/10)%10);
        drawNumber(4, I%10);
    }

}
