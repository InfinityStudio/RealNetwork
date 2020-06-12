package infstudio.realnetwork.client.gui;

import infstudio.realnetwork.RealNetwork;
import infstudio.realnetwork.inventory.ContainerSwitch;
import infstudio.realnetwork.network.MessageMachineBase;
import infstudio.realnetwork.network.NetworkLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiSwitch extends GuiMachineBase {

    private static final int buttonSwitch = 6;
    private ContainerSwitch container;

    public GuiSwitch(ContainerSwitch inventorySlotsIn) {
        super(inventorySlotsIn);
        this.texture = new ResourceLocation(RealNetwork.MODID+":textures/gui/container/switch.png");
        this.frontTexture = new ResourceLocation(RealNetwork.MODID+":textures/blocks/machine/switch_front.png");
        this.container = inventorySlotsIn;
    }

    @Override
    public void initGui() {
        super.initGui();
        int offsetX = (this.width-this.xSize)/2, offsetY = (this.height-this.ySize)/2;
        this.buttonList.add(new GuiButton(buttonSwitch, offsetX+112, offsetY+32, 16, 16, "") {
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
                if (this.visible) {
                    GlStateManager.color(1.0F, 1.0F, 1.0F);
                    mc.getTextureManager().bindTexture(texture);
                    if (!container.getTile().getStatus()) {
                        this.drawTexturedModalRect(this.x, this.y, 112, 32, this.width, this.height);
                    } else {
                        this.drawTexturedModalRect(this.x, this.y, 0, 166, this.width, this.height);
                    }
                }
            }
        });
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if (button.id == buttonSwitch) {
            container.getTile().changeStatus();
            MessageMachineBase message = new MessageMachineBase(container.getTile().writeToNBT(new NBTTagCompound()), container.getTile().getPos());
            NetworkLoader.instance.sendToServer(message);
        }
    }

}
