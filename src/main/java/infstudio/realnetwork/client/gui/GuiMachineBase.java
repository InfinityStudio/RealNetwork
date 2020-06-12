package infstudio.realnetwork.client.gui;

import infstudio.realnetwork.inventory.ContainerMachineBase;
import infstudio.realnetwork.network.MessageMachineBase;
import infstudio.realnetwork.network.NetworkLoader;
import infstudio.realnetwork.tileentity.TileEntityGenerator;
import infstudio.realnetwork.util.EnumRelativeFacing;
import infstudio.realnetwork.util.FacingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiMachineBase extends GuiContainer {

    protected ResourceLocation texture;
    protected ResourceLocation frontTexture;
    private static final int buttonLeft = 0;
    private static final int buttonRight = 1;
    private static final int buttonTop = 2;
    private static final int buttonBottom = 3;
    private static final int buttonBack = 4;
    private static final int buttonOK = 5;
    private ContainerMachineBase container;
    private int change = 0;


    public GuiMachineBase(ContainerMachineBase inventorySlotsIn) {
        super(inventorySlotsIn);
        this.xSize = 176;
        this.ySize = 166;
        this.container = inventorySlotsIn;
    }

    @Override
    public void initGui() {
        super.initGui();
        int offsetX = (this.width-this.xSize)/2, offsetY = (this.height-this.ySize)/2;
        this.buttonList.add(new GuiButtonFacing(buttonLeft, offsetX+48, offsetY+32, 16, 16, "", container.getTileMachine(), EnumRelativeFacing.LEFT));
        this.buttonList.add(new GuiButtonFacing(buttonRight, offsetX+16, offsetY+32, 16, 16, "", container.getTileMachine(), EnumRelativeFacing.RIGHT));
        this.buttonList.add(new GuiButtonFacing(buttonTop, offsetX+32, offsetY+16, 16, 16, "", container.getTileMachine(), EnumRelativeFacing.TOP));
        this.buttonList.add(new GuiButtonFacing(buttonBottom, offsetX+32, offsetY+48, 16, 16, "", container.getTileMachine(), EnumRelativeFacing.BOTTOM));
        this.buttonList.add(new GuiButtonFacing(buttonBack, offsetX+48, offsetY+48, 16, 16, "", container.getTileMachine(), EnumRelativeFacing.BACK));
        this.buttonList.add(new GuiButton(buttonOK, offsetX+17, offsetY+17, 14, 14, "") {
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
                if (this.visible) {
                    GlStateManager.color(1.0F, 1.0F, 1.0F);
                    mc.getTextureManager().bindTexture(texture);
                    this.drawTexturedModalRect(this.x, this.y, 17, 17, this.width, this.height);
                }
            }
        });
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int offsetX = (this.width-this.xSize)/2, offsetY = (this.height-this.ySize)/2;
        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id) {
            case buttonOK: {
                FacingHelper helper = new FacingHelper(container.getTileMachine().getFacing());
                boolean flag = true;
                EnumFacing facing[] = new EnumFacing[2];
                if (container.getTileMachine() instanceof TileEntityGenerator) {
                    int posCnt = 0, negCnt = 0;
                    for (int i = 0; i < buttonList.size(); ++i) {
                        if (0 <= buttonList.get(i).id && buttonList.get(i).id <= 4) {
                            GuiButtonFacing b = (GuiButtonFacing)buttonList.get(i);
                            if (b.isPort() == 1) {
                                if (posCnt == 1) {
                                    flag = false;
                                    break;
                                }
                                facing[0] = helper.getFacing(EnumRelativeFacing.getFacingByIndex(buttonList.get(i).id));
                                posCnt++;
                            }
                            if (b.isPort() == 2) {
                                if (negCnt == 1) {
                                    flag = false;
                                    break;
                                }
                                facing[1] = helper.getFacing(EnumRelativeFacing.getFacingByIndex(buttonList.get(i).id));
                                negCnt++;
                            }
                        }
                    }
                    if (!(posCnt == 1 && negCnt == 1)) flag = false;
                } else {
                    int cnt = 0;
                    for (int i = 0; i < buttonList.size(); ++i) {
                        if (0 <= buttonList.get(i).id && buttonList.get(i).id <= 4) {
                            GuiButtonFacing b = (GuiButtonFacing)buttonList.get(i);
                            if (b.isPort() == 1) {
                                if (cnt == 2) {
                                    flag = false;
                                    break;
                                }
                                facing[cnt++] = helper.getFacing(EnumRelativeFacing.getFacingByIndex(buttonList.get(i).id));
                            }
                        }
                    }
                    if (cnt < 2) flag = false;
                }
                if (flag) {
                    container.getTileMachine().setPort(facing[0], 0);
                    container.getTileMachine().setPort(facing[1], 1);
                    change = 1;
                    MessageMachineBase message = new MessageMachineBase(container.getTileMachine().writeToNBT(new NBTTagCompound()), container.getTileMachine().getPos());
                    NetworkLoader.instance.sendToServer(message);
                } else {
                    change = 2;
                }
                break;
            }
            case buttonLeft:
            case buttonRight:
            case buttonTop:
            case buttonBottom:
            case buttonBack:
                ((GuiButtonFacing)button).changeStatus(); break;

        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        this.mc.getTextureManager().bindTexture(frontTexture);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GlStateManager.scale(0.0625F, 0.0625F, 0.0625F);
        this.drawTexturedModalRect(32*16, 32*16, 0, 0, 256, 256);
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        switch (change) {
            case 1: {
                String success = I18n.format("gui.change_port_success");
                this.fontRenderer.drawString(success, 17, 70, 0x00ff00);
                break;
            }
            case 2: {
                String fail = I18n.format("gui.change_port_fail");
                this.fontRenderer.drawString(fail, 17, 70, 0xff0000);
                break;
            }
            default: break;
        }
    }

}
