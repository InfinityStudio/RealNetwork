package infstudio.realnetwork.item;

import infstudio.realnetwork.RealNetwork;
import infstudio.realnetwork.tileentity.TileEntityMachineBase;
import infstudio.realnetwork.tileentity.TileEntityResistance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemTestTool extends Item {

    public ItemTestTool() {
        super();
        setRegistryName("testtool");
        setUnlocalizedName("testtool");
        setCreativeTab(RealNetwork.rnTab);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            if (worldIn.getTileEntity(pos) instanceof TileEntityMachineBase) {
                TileEntityMachineBase tile = (TileEntityMachineBase) worldIn.getTileEntity(pos);
                player.sendMessage(new TextComponentString("port[0]="+tile.getPort()[0].getName()+", port[1]="+tile.getPort()[1].getName()));
                player.sendMessage(new TextComponentString("I="+String.format("%.2f", tile.getI())+"A"));
            }
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
