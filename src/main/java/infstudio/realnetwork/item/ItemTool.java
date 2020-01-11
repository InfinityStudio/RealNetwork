package infstudio.realnetwork.item;

import infstudio.realnetwork.RealNetwork;
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
        setUnlocalizedName("tool");
        setCreativeTab(RealNetwork.rnTab);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            if (worldIn.getTileEntity(pos) instanceof TileEntityMachineBase) {
                ((TileEntityMachineBase) worldIn.getTileEntity(pos)).setFacing(player.getHorizontalFacing());
                worldIn.notifyBlockUpdate(pos, worldIn.getBlockState(pos), worldIn.getBlockState(pos), 2);
                NetWork netWork = new NetWork(worldIn, pos);
            }
        }

        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

}
