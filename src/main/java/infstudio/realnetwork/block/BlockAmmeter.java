package infstudio.realnetwork.block;

import infstudio.realnetwork.tileentity.TileEntityAmmeter;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class BlockAmmeter extends BlockResistance {

    public BlockAmmeter() {
        super();
        setName("ammeter");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityAmmeter(1e-3);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntityAmmeter tile = (TileEntityAmmeter) worldIn.getTileEntity(pos);
            playerIn.sendMessage(new TextComponentString("I="+String.format("%.2f", tile.getI())+"A"));
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

}
