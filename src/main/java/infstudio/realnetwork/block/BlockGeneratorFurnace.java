package infstudio.realnetwork.block;

import infstudio.realnetwork.RealNetwork;
import infstudio.realnetwork.client.gui.GuiLoader;
import infstudio.realnetwork.tileentity.TileEntityGeneratorFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockGeneratorFurnace extends BlockGenerator {

    public BlockGeneratorFurnace() {
        super();
        setName("generator_furnace");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityGeneratorFurnace(0.1D, 0.0D, 0.0D, 1e6, getName());
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            playerIn.openGui(RealNetwork.instance, GuiLoader.GUI_GENERATOR_FURNACE, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public Class getTileEntity() {
        return TileEntityGeneratorFurnace.class;
    }

}
