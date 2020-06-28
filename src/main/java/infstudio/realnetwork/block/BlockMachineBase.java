package infstudio.realnetwork.block;

import infstudio.realnetwork.core.NetWork;
import infstudio.realnetwork.tileentity.TileEntityMachineBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMachineBase extends BlockWireBase {

    public BlockMachineBase() {
        super();
        setHardness(2.0F);
        setHarvestLevel("pickaxe", 2);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (!worldIn.isRemote) {
            ((TileEntityMachineBase)worldIn.getTileEntity(pos)).setFacing(placer.getHorizontalFacing().getOpposite());
            worldIn.notifyBlockUpdate(pos, worldIn.getBlockState(pos), worldIn.getBlockState(pos), 2);
        }
        new NetWork(worldIn, pos);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public Class getTileEntity() {
        return TileEntityMachineBase.class;
    }

}
