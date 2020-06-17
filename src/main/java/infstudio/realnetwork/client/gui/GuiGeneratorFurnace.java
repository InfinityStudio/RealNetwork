package infstudio.realnetwork.client.gui;

import infstudio.realnetwork.RealNetwork;
import infstudio.realnetwork.inventory.ContainerGeneratorFurnace;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiGeneratorFurnace extends GuiMachineBase {

    private ContainerGeneratorFurnace container;

    public GuiGeneratorFurnace(ContainerGeneratorFurnace inventorySlotsIn) {
        super(inventorySlotsIn);
        this.texture = new ResourceLocation(RealNetwork.MODID+":textures/gui/container/generator_furnace.png");
        this.frontTexture = new ResourceLocation(RealNetwork.MODID+":textures/blocks/machine/generator_furnace_front.png");
        this.container = inventorySlotsIn;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        int fluidAmount = container.getFluidAmount(), fluidCapacity = container.getFluidCapacity();
        mc.getTextureManager().bindTexture(new ResourceLocation("minecraft:textures/blocks/water_still.png"));
        drawTexturedModalRect(119, 69-(int)Math.ceil(58.0*fluidAmount/fluidCapacity), 0, 58-(int)Math.ceil(58.0*fluidAmount/fluidCapacity), 16, (int)Math.ceil(58.0*fluidAmount/fluidCapacity));
        mc.getTextureManager().bindTexture(texture);
        drawTexturedModalRect(119, 69-(int)Math.ceil(58.0*fluidAmount/fluidCapacity), 192, 58-(int)Math.ceil(58.0*fluidAmount/fluidCapacity), 16, (int)Math.ceil(58.0*fluidAmount/fluidCapacity));
        if (container.isBurning()) {
            int burnTime = container.getBurnTime(), curItemBurnTime = container.getCurItemBurnTime();
            drawTexturedModalRect(71, 63-(int)Math.ceil(14.0*burnTime/curItemBurnTime), 0, 180-(int)Math.ceil(14.0*burnTime/curItemBurnTime), 14, (int)Math.ceil(14.0*burnTime/curItemBurnTime));
        }
        int energy = container.getEnergy(), energyCapacity = container.getEnergyCapacity();
        drawTexturedModalRect(143, 69-(int)Math.ceil(58.0*energy/energyCapacity), 176, 58-(int)Math.ceil(58.0*energy/energyCapacity), 16, (int)Math.ceil(58.0*energy/energyCapacity));
    }

}
