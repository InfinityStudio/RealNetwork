package infstudio.realnetwork.block;

import infstudio.realnetwork.core.NetWork;
import infstudio.realnetwork.tileentity.TileEntityMachineBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMachineBase extends BlockWireBase {

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        ((TileEntityMachineBase)worldIn.getTileEntity(pos)).setFacing(placer.getHorizontalFacing());
        worldIn.notifyBlockUpdate(pos, worldIn.getBlockState(pos), worldIn.getBlockState(pos), 2);
        NetWork netWork = new NetWork(worldIn, pos);
    }

}
