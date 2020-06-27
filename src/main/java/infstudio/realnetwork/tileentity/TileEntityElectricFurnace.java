package infstudio.realnetwork.tileentity;

import infstudio.realnetwork.core.NetWork;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityElectricFurnace extends TileEntitySwitch {

    private ItemStackHandler invInput = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            TileEntityElectricFurnace.this.markDirty();
        }
    };
    private ItemStackHandler invOutput = new ItemStackHandler() {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            TileEntityElectricFurnace.this.markDirty();
        }
    };
    private double cookEnergy = 0.0D;
    private double totalCookEnergy = 0.0D;

    public TileEntityElectricFurnace() {

    }

    public TileEntityElectricFurnace(double R, String name) {
        super(R, name);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)) {
            if (facing == EnumFacing.UP) return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(invInput);
            else return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(invOutput);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.cookEnergy = compound.getDouble("CookEnergy");
        this.totalCookEnergy = compound.getDouble("TotalCookEnergy");
        this.invInput.deserializeNBT(compound.getCompoundTag("Input"));
        this.invOutput.deserializeNBT(compound.getCompoundTag("Output"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setDouble("CookEnergy", this.cookEnergy);
        compound.setDouble("TotalCookEnergy", this.totalCookEnergy);
        compound.setTag("Input", this.invInput.serializeNBT());
        compound.setTag("Output", this.invOutput.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public boolean isWorking() {
        return getI() > EPS;
    }

    public double getCookEnergy() {
        return this.cookEnergy;
    }

    public double getTotalCookEnergy() {
        return this.totalCookEnergy;
    }

    @Override
    public void update() {
        super.update();
        boolean flag = false;
        if (this.getU() > 220.0D) {
            this.setDamage(damage+1e-4*Math.exp(1e-2*(this.getU()-220.0D)));
            flag = true;
        }
        if (!world.isRemote) {
            if (this.isWorking()) {
                if (this.cookEnergy > 0.0D) {
                    this.cookEnergy -= this.getP();
                    flag = true;
                }
                if (this.cookEnergy <= 0.0D && this.totalCookEnergy > 0.0D) {
                    this.cookEnergy = this.totalCookEnergy = 0.0D;
                    this.invOutput.insertItem(0, FurnaceRecipes.instance().getSmeltingResult(this.invInput.getStackInSlot(1)).copy(), false);
                    this.invInput.setStackInSlot(1, ItemStack.EMPTY);
                    this.setStatus(false);
                    new NetWork(this.world, this.pos);
                    flag = true;
                }
            }
            if (Math.abs(this.cookEnergy) < EPS) {
                ItemStack stackInput1 = this.invInput.getStackInSlot(0), stackOutput1 = this.invOutput.getStackInSlot(0);
                if (!stackInput1.isEmpty()) {
                    ItemStack stackOutput2 = FurnaceRecipes.instance().getSmeltingResult(stackInput1);
                    if (!stackOutput2.isEmpty()) {
                        if (stackOutput1.isEmpty() || (stackOutput1.getItem().equals(stackOutput2.getItem()) && stackOutput1.getCount()+stackOutput2.getCount() <= stackOutput1.getMaxStackSize())) {
                            this.invInput.setStackInSlot(1, stackInput1.splitStack(1));
                            this.totalCookEnergy = this.cookEnergy = 44000.0D;
                            this.setStatus(true);
                            flag = true;
                        }
                    }
                }
            }
        }
        if (flag) this.markDirty();
    }

}
