package infstudio.realnetwork.tileentity;

import infstudio.realnetwork.core.NetWork;
import infstudio.realnetwork.item.ItemAppliance;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityGeneratorFurnace extends TileEntityGenerator {

    private ItemStackHandler invFuel = new ItemStackHandler() {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            TileEntityGeneratorFurnace.this.markDirty();
        }
    };
    private ItemStackHandler invApp = new ItemStackHandler() {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            TileEntityGeneratorFurnace.this.markDirty();
        }
    };
    private ItemStackHandler invFluid = new ItemStackHandler() {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            TileEntityGeneratorFurnace.this.markDirty();
        }
    };
    private double lastP = 0.0D;
    private int tick = 0;

    public FluidTank tank = new FluidTank(16000) {
        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return fluid.getFluid().equals(FluidRegistry.WATER) && super.canFillFluidType(fluid);
        }
    };

    private int burnTime = 0, curItemBurnTime = 0;

    public TileEntityGeneratorFurnace() {
        super();
    }

    public TileEntityGeneratorFurnace(double R, double Em, double phi, double capacity, String name) {
        super(R, Em, phi, capacity, name);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)) {
            return true;
        }
        if (CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.equals(capability)) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)) {
            if (facing == EnumFacing.UP) return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(invFuel);
            else if (facing == EnumFacing.DOWN) return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(invApp);
            else return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(invFluid);
        }
        if (CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.equals(capability)) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.invFuel.deserializeNBT(compound.getCompoundTag("Fuel"));
        this.invApp.deserializeNBT(compound.getCompoundTag("App"));
        this.invFluid.deserializeNBT(compound.getCompoundTag("Fluid"));
        this.tank.readFromNBT(compound);
        this.burnTime = compound.getInteger("BurnTime");
        this.curItemBurnTime = compound.getInteger("ItemBurnTime");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("Fuel", this.invFuel.serializeNBT());
        compound.setTag("App", this.invApp.serializeNBT());
        compound.setTag("Fluid", this.invFluid.serializeNBT());
        compound = this.tank.writeToNBT(compound);
        compound.setInteger("BurnTime", this.burnTime);
        compound.setInteger("ItemBurnTime", this.curItemBurnTime);
        return super.writeToNBT(compound);
    }

    public int getBurnTime() {
        return this.burnTime;
    }

    public boolean isBurning() {
        return getBurnTime() > 0;
    }

    public int getCurItemBurnTime() {
        return this.curItemBurnTime;
    }

    public int getFluidAmount() {
        return this.tank.getFluidAmount();
    }

    public int getFluidCapacity() {
        return this.tank.getCapacity();
    }

    @Override
    public void update() {
        super.update();
        boolean flag = false;
        if (this.isBurning()) {
            this.burnTime--;
            if (getFluidAmount() > 0) {
                if (damageTime > 0) damageTime--;
                if (this.tank.drain(2, false) != null) {
                    this.tank.drain(2, true);
                    this.incEnergy(220.0D);
                }
            } else {
                damageTime++;
                this.setDamage(damage+1e-4*Math.exp(1e-2*damageTime));
            }
            flag = true;
        }
        if (!this.world.isRemote) {
            tick = (tick+1)%100;
            if (tick == 0) this.lastP = 0;
            if (this.getEnergy() >= lastP && Math.abs(this.getE().A.get(0)) < 1e-8){
                this.setE(311.0D, 0.0D);
                new NetWork(this.world, this.pos);
                this.lastP = this.getP();
                if (this.getEnergy() < this.lastP) {
                    this.setE(0.0D, 0.0D);
                    new NetWork(this.world, this.pos);
                } else flag = true;
            }
            if (this.getEnergy() < this.getP() && this.getE().A.get(0) > 0) {
                this.setE(0.0D, 0.0D);
                new NetWork(this.world, this.pos);
                flag = true;
            }
            if (this.isWorking()) {
                this.decEnergy(this.getP());
                flag = true;
            }
            ItemStack stackApp = this.invApp.getStackInSlot(0);
            if (this.getEnergy() > 0.0D && !stackApp.isEmpty()) {
                ItemAppliance itemApp = (ItemAppliance)stackApp.getItem();
                if (!itemApp.isFull(stackApp)) {
                    double amount = Math.min(Math.min(itemApp.getCapacity(stackApp)-itemApp.getEnergy(stackApp), 220.0D), this.getEnergy());
                    itemApp.incEnergy(stackApp, amount);
                    this.decEnergy(amount);
                }
            }
            if (this.isBurning()) {
                flag = true;
            } else {
                ItemStack stackFuel = this.invFuel.getStackInSlot(0);
                if (!stackFuel.isEmpty()) {
                    if (TileEntityFurnace.isItemFuel(stackFuel)) {
                        this.burnTime = TileEntityFurnace.getItemBurnTime(stackFuel);
                        this.curItemBurnTime = this.burnTime;
                        Item itemFuel = stackFuel.getItem();
                        stackFuel.shrink(1);
                        flag = true;
                        if (stackFuel.isEmpty()) {
                            ItemStack itemFuel1 = itemFuel.getContainerItem(stackFuel);
                            this.invFuel.setStackInSlot(0, itemFuel1);
                        }
                    }
                }
            }
            ItemStack stackFluid = this.invFluid.getStackInSlot(0);
            if (!stackFluid.isEmpty()) {
                if (stackFluid.getItem().equals(Items.WATER_BUCKET)) {
                    if (this.tank.fill(new FluidStack(FluidRegistry.WATER, 1000), false) > 0) {
                        this.tank.fill(new FluidStack(FluidRegistry.WATER, 1000), true);
                        Item itemFluid = stackFluid.getItem();
                        stackFluid.shrink(1);
                        flag = true;
                        if (stackFluid.isEmpty()) {
                            ItemStack itemFluid1 = itemFluid.getContainerItem(stackFluid);
                            this.invFluid.setStackInSlot(0, itemFluid1);
                        }
                    }
                }
            }
        }
        if (flag) {
            this.markDirty();
        }
    }

}
