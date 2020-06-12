package infstudio.realnetwork.client.gui;

import infstudio.realnetwork.RealNetwork;
import infstudio.realnetwork.inventory.ContainerGeneratorFurnace;
import net.minecraft.util.ResourceLocation;

public class GuiGeneratorFurnace extends GuiMachineBase {

    private ContainerGeneratorFurnace container;

    public GuiGeneratorFurnace(ContainerGeneratorFurnace inventorySlotsIn) {
        super(inventorySlotsIn);
        this.texture = new ResourceLocation(RealNetwork.MODID+":textures/gui/container/generator_furnace.png");
        this.frontTexture = new ResourceLocation(RealNetwork.MODID+":textures/blocks/machine/generator_furnace_front.png");
        this.container = inventorySlotsIn;
    }

}
