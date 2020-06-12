package infstudio.realnetwork.client.gui;

import infstudio.realnetwork.RealNetwork;
import infstudio.realnetwork.tileentity.TileEntityGenerator;
import infstudio.realnetwork.tileentity.TileEntityMachineBase;
import infstudio.realnetwork.util.EnumRelativeFacing;
import infstudio.realnetwork.util.FacingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiButtonFacing extends GuiButton {

    protected static final ResourceLocation sideTexture = new ResourceLocation(RealNetwork.MODID+":textures/blocks/machine/side.png");
    protected static final ResourceLocation portTexture = new ResourceLocation(RealNetwork.MODID+":textures/blocks/machine/port.png");
    protected static final ResourceLocation posTexture = new ResourceLocation(RealNetwork.MODID+":textures/blocks/machine/positive.png");
    protected static final ResourceLocation negTexture = new ResourceLocation(RealNetwork.MODID+":textures/blocks/machine/negative.png");

    private int isPort;
    private TileEntityMachineBase tile;

    public GuiButtonFacing(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    public GuiButtonFacing(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, TileEntityMachineBase tile, EnumRelativeFacing facing) {
        this(buttonId, x, y, widthIn, heightIn, buttonText);
        this.tile = tile;
        FacingHelper helper = new FacingHelper(tile.getFacing());
        if (tile instanceof TileEntityGenerator) {
            if (helper.getFacing(facing).equals(tile.getPort()[0])) {
                isPort = 1;
            } else if (helper.getFacing(facing).equals(tile.getPort()[1])) {
                isPort = 2;
            } else {
                isPort = 0;
            }
        } else {
            if (helper.getFacing(facing).equals(tile.getPort()[0]) || helper.getFacing(facing).equals(tile.getPort()[1])) {
                isPort = 1;
            } else {
                isPort = 0;
            }
        }
    }

    public void changeStatus() {
        if (tile instanceof TileEntityGenerator) {
            isPort = (isPort+1)%3;
        } else {
            isPort ^= 1;
        }
    }

    public int isPort() {
        return isPort;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            if (tile instanceof TileEntityGenerator) {
                switch (isPort) {
                    case 1: mc.getTextureManager().bindTexture(posTexture); break;
                    case 2: mc.getTextureManager().bindTexture(negTexture); break;
                    default: mc.getTextureManager().bindTexture(sideTexture);
                }
            } else {
                if (isPort == 0) {
                    mc.getTextureManager().bindTexture(sideTexture);
                } else {
                    mc.getTextureManager().bindTexture(portTexture);
                }
            }
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            GlStateManager.scale(0.0625F, 0.0625F, 0.0625F);
            this.drawTexturedModalRect(this.x*16, this.y*16, 0, 0, 256, 256);
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        }
    }

}
