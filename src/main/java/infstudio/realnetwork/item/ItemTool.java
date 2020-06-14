package infstudio.realnetwork.item;

import infstudio.realnetwork.RealNetwork;
import infstudio.realnetwork.block.BlockWireBase;
import infstudio.realnetwork.core.NetWork;
import infstudio.realnetwork.tileentity.TileEntityMachineBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemTool extends Item {

    public ItemTool() {
        super();
        setRegistryName("tool");
        setTranslationKey("tool");
        setCreativeTab(RealNetwork.rnTab);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            if (worldIn.getTileEntity(pos) instanceof TileEntityMachineBase) {
                ((TileEntityMachineBase) worldIn.getTileEntity(pos)).setFacing(player.getHorizontalFacing().getOpposite());
                worldIn.notifyBlockUpdate(pos, worldIn.getBlockState(pos), worldIn.getBlockState(pos), 2);
                new NetWork(worldIn, pos);
                if (worldIn.getBlockState(pos.east()).getBlock() instanceof BlockWireBase) new NetWork(worldIn, pos.east());
                if (worldIn.getBlockState(pos.west()).getBlock() instanceof BlockWireBase) new NetWork(worldIn, pos.west());
                if (worldIn.getBlockState(pos.north()).getBlock() instanceof BlockWireBase) new NetWork(worldIn, pos.north());
                if (worldIn.getBlockState(pos.south()).getBlock() instanceof BlockWireBase) new NetWork(worldIn, pos.south());
            }
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

}
