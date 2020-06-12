package infstudio.realnetwork.block;

import infstudio.realnetwork.RealNetwork;
import infstudio.realnetwork.client.gui.GuiLoader;
import infstudio.realnetwork.tileentity.TileEntityVoltmeter;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockVoltmeter extends BlockResistance {

    public BlockVoltmeter() {
        super();
        setName("voltmeter");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityVoltmeter(1e3, getName());
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            playerIn.openGui(RealNetwork.instance, GuiLoader.GUI_VOLTMETER, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public Class getTileEntity() {
        return TileEntityVoltmeter.class;
    }

}
