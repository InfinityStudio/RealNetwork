package infstudio.realnetwork.block;

import infstudio.realnetwork.RealNetwork;
import infstudio.realnetwork.client.gui.GuiLoader;
import infstudio.realnetwork.tileentity.TileEntityGenerator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockGenerator extends BlockMachineBase {

    public BlockGenerator() {
        super();
        setName("generator");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityGenerator(0.1D, 6.0D, 0.0D, Double.MAX_VALUE, getName());
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            playerIn.openGui(RealNetwork.instance, GuiLoader.GUI_GENERATOR, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public Class getTileEntity() {
        return TileEntityGenerator.class;
    }

}
