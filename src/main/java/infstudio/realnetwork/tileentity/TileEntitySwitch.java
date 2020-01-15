package infstudio.realnetwork.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntitySwitch extends TileEntityMachineBase {

    private boolean status;

    public TileEntitySwitch() {
        super();
    }

    public TileEntitySwitch(double R, String name) {
        super(R, name);
        this.status = false;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.status = compound.getBoolean("Status");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("Status", this.status);
        return super.writeToNBT(compound);
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void changeStatus() {
        this.setStatus(!this.status);
    }

}
