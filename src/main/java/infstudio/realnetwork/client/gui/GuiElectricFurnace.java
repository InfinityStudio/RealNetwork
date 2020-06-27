package infstudio.realnetwork.client.gui;

import infstudio.realnetwork.RealNetwork;
import infstudio.realnetwork.inventory.ContainerElectricFurnace;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiElectricFurnace extends GuiMachineBase {

    private ContainerElectricFurnace container;

    public GuiElectricFurnace(ContainerElectricFurnace inventorySlotsIn) {
        super(inventorySlotsIn);
        this.texture = new ResourceLocation(RealNetwork.MODID+":textures/gui/container/electric_furnace.png");
        this.frontTexture = new ResourceLocation(RealNetwork.MODID+":textures/blocks/machine/electric_furnace_front.png");
        this.container = inventorySlotsIn;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(this.texture);
        if (container.isBurning()) {
            int cookEnergy = container.getCookEnergy(), totalCookEnergy = container.getTotalCookEnergy();
            drawTexturedModalRect(103, 31, 176, 0, (int)Math.ceil(24.0*(totalCookEnergy-cookEnergy)/totalCookEnergy), 17);
        }
    }

}
