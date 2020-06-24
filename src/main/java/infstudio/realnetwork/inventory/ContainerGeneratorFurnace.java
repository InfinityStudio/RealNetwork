package infstudio.realnetwork.inventory;

import infstudio.realnetwork.item.ItemAppliance;
import infstudio.realnetwork.tileentity.TileEntityGeneratorFurnace;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ContainerGeneratorFurnace extends ContainerMachineBase {

    private TileEntityGeneratorFurnace tile;
    private IItemHandler itemFuel, itemApp, itemFluid;
    private int burnTime, curItemBurnTime, fluidAmount, fluidCapacity, energy, energyCapacity;

    public ContainerGeneratorFurnace(InventoryPlayer playerInventory, TileEntityGeneratorFurnace tile) {
        super(playerInventory, tile);
        this.tile = tile;
        this.burnTime = tile.getBurnTime();
        this.curItemBurnTime = tile.getCurItemBurnTime();
        this.fluidAmount = tile.getFluidAmount();
        this.fluidCapacity = tile.getFluidCapacity();
        this.energy = (int)tile.getEnergy();
        this.energyCapacity = (int)tile.getEnergyCapacity();
        this.itemFuel = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        this.itemApp = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
        this.itemFluid = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        this.addSlotToContainer(new SlotFuel(itemFuel, 0, 71, 32));
        this.addSlotToContainer(new SlotAppliance(itemApp, 0, 95, 20));
        this.addSlotToContainer(new SlotFluid(itemFluid, 0, 95, 44));
    }

    public int getFluidAmount() {
        return this.fluidAmount;
    }

    public int getFluidCapacity() {
        return this.fluidCapacity;
    }

    public boolean isBurning() {
        return getBurnTime() > 0;
    }

    public int getBurnTime() {
        return this.burnTime;
    }

    public int getCurItemBurnTime() {
        return this.curItemBurnTime;
    }

    public int getEnergy() {
        return this.energy;
    }

    public int getEnergyCapacity() {
        return this.energyCapacity;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < listeners.size(); ++i) {
            IContainerListener iContainerListener = listeners.get(i);
            if (burnTime != tile.getBurnTime()) {
                iContainerListener.sendWindowProperty(this, 1, tile.getBurnTime());
            }
            if (curItemBurnTime != tile.getCurItemBurnTime()) {
                iContainerListener.sendWindowProperty(this, 2, tile.getCurItemBurnTime());
            }
            if (fluidAmount != tile.getFluidAmount()) {
                iContainerListener.sendWindowProperty(this, 3, tile.getFluidAmount());
            }
            if (fluidCapacity != tile.getFluidCapacity()) {
                iContainerListener.sendWindowProperty(this, 4, tile.getFluidCapacity());
            }
            if (energy != (int)tile.getEnergy()) {
                iContainerListener.sendWindowProperty(this, 5, (int)tile.getEnergy());
            }
            if (energyCapacity != (int)tile.getEnergyCapacity()) {
                iContainerListener.sendWindowProperty(this, 6, (int)tile.getEnergyCapacity());
            }
        }
        this.burnTime = tile.getBurnTime();
        this.curItemBurnTime = tile.getCurItemBurnTime();
        this.fluidAmount = tile.getFluidAmount();
        this.fluidCapacity = tile.getFluidCapacity();
        this.energy = (int)tile.getEnergy();
        this.energyCapacity = (int)tile.getEnergyCapacity();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
        if (id == 1) burnTime = data;
        if (id == 2) curItemBurnTime = data;
        if (id == 3) fluidAmount = data;
        if (id == 4) fluidCapacity = data;
        if (id == 5) energy = data;
        if (id == 6) energyCapacity = data;
    }

}
