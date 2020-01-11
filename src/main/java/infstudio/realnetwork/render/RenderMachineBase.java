package infstudio.realnetwork.render;

import infstudio.realnetwork.RealNetwork;
import infstudio.realnetwork.render.model.ModelMachineBase;
import infstudio.realnetwork.tileentity.TileEntityMachineBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMachineBase extends TileEntitySpecialRenderer<TileEntityMachineBase>{

    private final ModelMachineBase model = new ModelMachineBase();
    private TextureHelper helper = new TextureHelper();
    private ResourceLocation frontTexture, baseTexture;
    private float angle;

    public RenderMachineBase() {

    }

    @Override
    public void render(TileEntityMachineBase te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        frontTexture = new ResourceLocation(RealNetwork.MODID+":textures/blocks/machine/"+helper.getNameFromTileEntity(te)+".png");
        baseTexture = new ResourceLocation(RealNetwork.MODID+":textures/blocks/machine/base/base.png");
        EnumFacing facing = te.getFacing();
        switch (facing) {
            case NORTH: angle = 0.0F; break;
            case SOUTH: angle = 180.0F; break;
            case WEST: angle = -90.0F; break;
            case EAST: angle = 90.0F; break;
            default: angle = 0.0F; break;
        }

        GlStateManager.enableDepth();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);

        if (destroyStage >= 0) {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        } else this.bindTexture(baseTexture);
        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.translate((float)x, (float)y + 1.0F, (float)z + 1.0F);
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        GlStateManager.translate(0.5F, 0.5F, 0.5F);
        GlStateManager.rotate(angle, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(-0.5F, -0.5F, -0.5F);
        model.renderBase();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        if (destroyStage >= 0) {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        } else this.bindTexture(frontTexture);
        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.translate((float)x, (float)y + 1.0F, (float)z + 1.0F);
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        GlStateManager.translate(0.5F, 0.5F, 0.5F);
        GlStateManager.rotate(angle, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(-0.5F, -0.5F, -0.5F);
        model.renderFront();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        if (destroyStage >= 0)
        {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }

}
