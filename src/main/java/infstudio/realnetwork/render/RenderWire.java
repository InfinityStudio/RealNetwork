package infstudio.realnetwork.render;

import infstudio.realnetwork.RealNetwork;
import infstudio.realnetwork.block.BlockWire;
import infstudio.realnetwork.render.model.ModelWire;
import infstudio.realnetwork.tileentity.TileEntityMachineBase;
import infstudio.realnetwork.tileentity.TileEntityWire;
import infstudio.realnetwork.tileentity.TileEntityWireBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWire extends TileEntitySpecialRenderer<TileEntityWire> {

    private final ModelWire model = new ModelWire();
    private ResourceLocation texture = new ResourceLocation(RealNetwork.MODID+":textures/blocks/wire.png");

    public RenderWire() {

    }

    @Override
    public void render(TileEntityWire te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
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
        } else this.bindTexture(texture);
        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.translate((float)x, (float)y + 1.0F, (float)z + 1.0F);
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        GlStateManager.translate(0.5F, 0.5F, 0.5F);
        GlStateManager.translate(-0.5F, -0.5F, -0.5F);
        model.renderPost();
        World world = te.getWorld();
        BlockPos pos = te.getPos();
        BlockWire block = (BlockWire)world.getBlockState(pos).getBlock();
        if (block.canConnect(world, pos, pos.north(), EnumFacing.NORTH)) model.renderNorth();
        if (block.canConnect(world, pos, pos.south(), EnumFacing.SOUTH)) model.renderSouth();
        if (block.canConnect(world, pos, pos.east(), EnumFacing.EAST)) model.renderEast();
        if (block.canConnect(world, pos, pos.west(), EnumFacing.WEST)) model.renderWest();
        if (block.canConnect(world, pos, pos.up(), EnumFacing.UP)) model.renderUp();
        if (block.canConnect(world, pos, pos.down(), EnumFacing.DOWN)) model.renderDown();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        if (destroyStage >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }

}
