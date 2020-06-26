package infstudio.realnetwork.tileentity;

import infstudio.realnetwork.core.NetWork;
import infstudio.realnetwork.item.ItemAppliance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityGeneratorBattery extends TileEntityGenerator {

    private ItemStackHandler invApp = new ItemStackHandler() {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            TileEntityGeneratorBattery.this.markDirty();
        }
    };
    private double lastP = 0.0D;
    private int tick = 0;

    public TileEntityGeneratorBattery() {
        super();
    }

    public TileEntityGeneratorBattery(double R, double Em, double phi, double capacity, String name) {
        super(R, Em, phi, capacity, name);
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
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(invApp);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.invApp.deserializeNBT(compound.getCompoundTag("App"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("App", this.invApp.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public void update() {
        super.update();
        if (!this.world.isRemote) {
            ItemStack stackApp = invApp.getStackInSlot(0);
            if (!stackApp.isEmpty()) {
                tick = (tick+1)%100;
                if (tick == 0) this.lastP = 0;
                ItemAppliance itemApp = (ItemAppliance)stackApp.getItem();
                if (itemApp.getEnergy(stackApp) >= this.lastP) {
                    if (Math.abs(this.getE().A.get(0)) < 1e-8) {
                        this.setE(311.0D, 0.0D);
                        new NetWork(this.world, this.pos);
                        this.lastP = this.getP();
                        if (itemApp.getEnergy(stackApp) < this.lastP) {
                            this.setE(0.0D, 0.0D);
                            new NetWork(this.world, this.pos);
                        }
                    }
                }
                if (itemApp.getEnergy(stackApp) < this.getP() && this.getE().A.get(0) > 0.0D) {
                    this.setE(0.0D, 0.0D);
                    new NetWork(this.world, this.pos);
                }
                if (this.isWorking()) {
                    itemApp.decEnergy(stackApp, this.getP());
                }
            }
        }
    }

}
