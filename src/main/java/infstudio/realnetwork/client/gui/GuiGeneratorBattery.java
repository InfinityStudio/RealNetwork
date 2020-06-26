package infstudio.realnetwork.client.gui;

import infstudio.realnetwork.RealNetwork;
import infstudio.realnetwork.inventory.ContainerGeneratorBattery;
import net.minecraft.util.ResourceLocation;

public class GuiGeneratorBattery extends GuiMachineBase {

    private ContainerGeneratorBattery container;

    public GuiGeneratorBattery(ContainerGeneratorBattery inventorySlotsIn) {
        super(inventorySlotsIn);
        this.texture = new ResourceLocation(RealNetwork.MODID+":textures/gui/container/generator_battery.png");
        this.frontTexture = new ResourceLocation(RealNetwork.MODID+":textures/blocks/machine/generator_battery_front.png");
        this.container = inventorySlotsIn;
    }

}
