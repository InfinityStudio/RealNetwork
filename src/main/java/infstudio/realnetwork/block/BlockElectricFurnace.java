package infstudio.realnetwork.block;

import infstudio.realnetwork.RealNetwork;
import infstudio.realnetwork.client.gui.GuiLoader;
import infstudio.realnetwork.item.ItemLoader;
import infstudio.realnetwork.tileentity.TileEntityElectricFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockElectricFurnace extends BlockSwitch {

    public BlockElectricFurnace() {
        super();
        setName("electric_furnace");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityElectricFurnace(110.0D, getName());
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            ItemStack stackHand = playerIn.getHeldItemMainhand();
            if (stackHand.getItem().equals(ItemLoader.itemTool)) return false;
            playerIn.openGui(RealNetwork.instance, GuiLoader.GUI_ELECTRIC_FURNACE, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public Class getTileEntity() {
        return TileEntityElectricFurnace.class;
    }
}
