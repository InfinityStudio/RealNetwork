package infstudio.realnetwork.client.gui;

import infstudio.realnetwork.RealNetwork;
import infstudio.realnetwork.inventory.ContainerResistance;
import infstudio.realnetwork.network.MessageMachineBase;
import infstudio.realnetwork.network.NetworkLoader;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiResistance extends GuiMachineBase {

    private ContainerResistance container;
    private static final int buttonInc[] = {6, 7, 8, 9};
    private static final int buttonDec[] = {10, 11, 12, 13};

    public GuiResistance(ContainerResistance inventorySlotsIn) {
        super(inventorySlotsIn);
        this.texture = new ResourceLocation(RealNetwork.MODID+":textures/gui/container/resistance.png");
        this.frontTexture = new ResourceLocation(RealNetwork.MODID+":textures/blocks/machine/resistance_front.png");
        this.container = inventorySlotsIn;
    }

    @Override
    public void initGui() {
        super.initGui();
        int offsetX = (this.width-this.xSize)/2, offsetY = (this.height-this.ySize)/2;
        this.buttonList.add(new GuiButton(buttonInc[0], offsetX+70, offsetY+10, 25, 20, "+0.1"));
        this.buttonList.add(new GuiButton(buttonInc[1], offsetX+105, offsetY+10, 25, 20, "+1"));
        this.buttonList.add(new GuiButton(buttonInc[2], offsetX+140, offsetY+10, 25, 20, "+10"));
        this.buttonList.add(new GuiButton(buttonDec[0], offsetX+70, offsetY+50, 25, 20, "-0.1"));
        this.buttonList.add(new GuiButton(buttonDec[1], offsetX+105, offsetY+50, 25, 20, "-1"));
        this.buttonList.add(new GuiButton(buttonDec[2], offsetX+140, offsetY+50, 25, 20, "-10"));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        String R = String.format("R=%.1fΩ", 1.0*container.getR()/10);
        this.fontRenderer.drawString(R, 100, 36, 4210752);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        for (int i = 0; i < 4; ++i) {
            if (button.id == buttonInc[i]) {
                int Rnew = container.getR()+(int)Math.pow(10, i);
                container.getTileMachine().setResistance(1.0*Rnew/10);
                MessageMachineBase message = new MessageMachineBase(container.getTileMachine().writeToNBT(new NBTTagCompound()), container.getTileMachine().getPos());
                NetworkLoader.instance.sendToServer(message);
                break;
            }
        }
        for (int i = 0; i < 4; ++i) {
            if (button.id == buttonDec[i]) {
                int Rnew = container.getR()-(int)Math.pow(10, i);
                if (Rnew > 0) {
                    container.getTileMachine().setResistance(1.0*Rnew/10);
                    MessageMachineBase message = new MessageMachineBase(container.getTileMachine().writeToNBT(new NBTTagCompound()), container.getTileMachine().getPos());
                    NetworkLoader.instance.sendToServer(message);
                }
                break;
            }
        }
    }

}
