package infstudio.realnetwork.tileentity;

import infstudio.realnetwork.util.FacingHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityMachineBase extends TileEntityWireBase {

    protected EnumFacing port[] = new EnumFacing[2];
    protected EnumFacing facing;

    public TileEntityMachineBase() {
        super();
    }

    public TileEntityMachineBase(double R) {
        super(R);
        port[0] = EnumFacing.WEST;
        port[1] = EnumFacing.EAST;
        facing = EnumFacing.NORTH;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        int portIndex[] = compound.getIntArray("Port");
        if (portIndex.length > 0) {
            port[0] = getFaceByIndex(portIndex[0]);
            port[1] = getFaceByIndex(portIndex[1]);
        }
        facing = getFaceByIndex(compound.getInteger("Facing"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        int portIndex[] = new int[] {port[0].getIndex(), port[1].getIndex()};
        compound.setIntArray("Port", portIndex);
        compound.setInteger("Facing", facing.getIndex());
        return super.writeToNBT(compound);
    }

    public EnumFacing[] getPort() {
        return port;
    }

    @Override
    public double getU() {
        return Math.abs(phi[port[0].getIndex()]-phi[port[1].getIndex()]);
    }

    public EnumFacing getFacing() {
        return facing;
    }

    public void setFacing(EnumFacing facing) {
        FacingHelper oldHelper = new FacingHelper(this.facing);
        FacingHelper newHelper = new FacingHelper(facing);
        this.port[0] = newHelper.getFacing(oldHelper.getRelative(this.port[0]));
        this.port[1] = newHelper.getFacing(oldHelper.getRelative(this.port[1]));
        this.facing = facing;
    }

    public void setPort(EnumFacing facing, int index) {
        this.port[index] = facing;
    }

}
