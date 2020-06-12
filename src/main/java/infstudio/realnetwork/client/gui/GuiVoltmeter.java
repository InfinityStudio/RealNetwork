package infstudio.realnetwork.client.gui;

import infstudio.realnetwork.RealNetwork;
import infstudio.realnetwork.inventory.ContainerVoltmeter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiVoltmeter extends GuiMachineBase {

    private ContainerVoltmeter container;

    public GuiVoltmeter(ContainerVoltmeter inventorySlotsIn) {
        super(inventorySlotsIn);
        this.texture = new ResourceLocation(RealNetwork.MODID+":textures/gui/container/voltmeter.png");
        this.frontTexture = new ResourceLocation(RealNetwork.MODID+":textures/blocks/machine/voltmeter_front.png");
        this.container = inventorySlotsIn;
    }

    private void drawNumber(int index, int number) {
        drawTexturedModalRect(81+index*16, 17, number*15, 166, 14, 30);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(texture);
        int U = container.getU();
        if (U > 99999) U = 99999;
        drawNumber(0, U/10000);
        drawNumber(1, (U/1000)%10);
        drawNumber(2, (U/100)%10);
        drawNumber(3, (U/10)%10);
        drawNumber(4, U%10);
        String V = "V";
        this.fontRenderer.drawString(V, 117, 52, 4210752);
    }

}
