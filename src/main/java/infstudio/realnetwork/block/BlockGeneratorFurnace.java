package infstudio.realnetwork.block;

import infstudio.realnetwork.RealNetwork;
import infstudio.realnetwork.client.gui.GuiLoader;
import infstudio.realnetwork.item.ItemLoader;
import infstudio.realnetwork.tileentity.TileEntityGeneratorFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.CapabilityItemHandler;

public class BlockGeneratorFurnace extends BlockGenerator {

    public BlockGeneratorFurnace() {
        super();
        setName("generator_furnace");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityGeneratorFurnace(10.0D, 0.0D, 0.0D, 1e6, getName());
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            ItemStack stackHand = playerIn.getHeldItemMainhand();
            if (stackHand.getItem().equals(ItemLoader.itemTool)) return false;
            if (stackHand.getItem().equals(Items.WATER_BUCKET)) {
                TileEntityGeneratorFurnace tile = (TileEntityGeneratorFurnace)worldIn.getTileEntity(pos);
                if (tile.tank.fill(new FluidStack(FluidRegistry.WATER, 1000), false) > 0) {
                    tile.tank.fill(new FluidStack(FluidRegistry.WATER, 1000), true);
                    Item itemFluid = stackHand.getItem();
                    tile.markDirty();
                    worldIn.notifyBlockUpdate(pos, worldIn.getBlockState(pos), worldIn.getBlockState(pos), 2);
                    if (!playerIn.isCreative()) stackHand.shrink(1);
                    if (stackHand.isEmpty() && !playerIn.isCreative()) {
                        ItemStack itemFluid1 = itemFluid.getContainerItem(stackHand);
                        playerIn.setHeldItem(EnumHand.MAIN_HAND, itemFluid1);
                    }
                }
            } else {
                playerIn.openGui(RealNetwork.instance, GuiLoader.GUI_GENERATOR_FURNACE, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public Class getTileEntity() {
        return TileEntityGeneratorFurnace.class;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntityGeneratorFurnace tile = (TileEntityGeneratorFurnace)worldIn.getTileEntity(pos);
        ItemStack stackFuel = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP).getStackInSlot(0);
        ItemStack stackApp = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN).getStackInSlot(0);
        ItemStack stackFluid = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH).getStackInSlot(0);
        if (!stackFuel.isEmpty()) InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), stackFuel);
        if (!stackApp.isEmpty()) InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), stackApp);
        if (!stackFluid.isEmpty()) InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), stackFluid);
        super.breakBlock(worldIn, pos, state);
    }

}
