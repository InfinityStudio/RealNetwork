package infstudio.realnetwork.gui;

import infstudio.realnetwork.container.ContainerVoltmeter;

public class GuiVoltmeter extends GuiMachineBase {

    private ContainerVoltmeter container;

    public GuiVoltmeter(ContainerVoltmeter inventorySlotsIn) {
        super(inventorySlotsIn);
        this.container = inventorySlotsIn;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }

}
