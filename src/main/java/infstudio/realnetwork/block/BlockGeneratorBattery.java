package infstudio.realnetwork.block;

import infstudio.realnetwork.RealNetwork;
import infstudio.realnetwork.client.gui.GuiLoader;
import infstudio.realnetwork.item.ItemLoader;
import infstudio.realnetwork.tileentity.TileEntityGeneratorBattery;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

public class BlockGeneratorBattery extends BlockGenerator {

    public BlockGeneratorBattery() {
        super();
        setName("generator_battery");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityGeneratorBattery(10.0D, 0.0D, 0.0D, 0.0D, getName());
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            ItemStack stackHand = playerIn.getHeldItemMainhand();
            if (stackHand.getItem().equals(ItemLoader.itemTool)) return false;
            playerIn.openGui(RealNetwork.instance, GuiLoader.GUI_GENERATOR_BATTERY, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public Class getTileEntity() {
        return TileEntityGeneratorBattery.class;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntityGeneratorBattery tile = (TileEntityGeneratorBattery)worldIn.getTileEntity(pos);
        ItemStack stackApp = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP).getStackInSlot(0);
        if (!stackApp.isEmpty()) InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), stackApp);
        super.breakBlock(worldIn, pos, state);
    }

}
