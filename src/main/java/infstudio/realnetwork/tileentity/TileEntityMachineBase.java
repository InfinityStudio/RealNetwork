package infstudio.realnetwork.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityMachineBase extends TileEntityWireBase {

    protected EnumFacing port[] = new EnumFacing[2];

    public TileEntityMachineBase() {
        super();
    }

    public TileEntityMachineBase(double R) {
        super(R);
        port[0] = EnumFacing.NORTH;
        port[1] = EnumFacing.SOUTH;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        int portIndex[] = compound.getIntArray("Port");
        if (portIndex.length > 0) {
            port[0] = getFaceByIndex(portIndex[0]);
            port[1] = getFaceByIndex(portIndex[1]);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        int portIndex[] = new int[] {port[0].getIndex(), port[1].getIndex()};
        compound.setIntArray("Port", portIndex);
        return super.writeToNBT(compound);
    }

    public EnumFacing[] getPort() {
        return port;
    }

    public void setPort(EnumFacing facing) {
        port[0] = facing;
        port[1] = facing.getOpposite();
    }

    @Override
    public double getI() {
        return Math.abs((phi[port[0].getIndex()]-phi[port[1].getIndex()])/R);
    }
}
