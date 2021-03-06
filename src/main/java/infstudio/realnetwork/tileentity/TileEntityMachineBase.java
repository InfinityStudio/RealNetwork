package infstudio.realnetwork.tileentity;

import infstudio.realnetwork.util.FacingHelper;
import infstudio.realnetwork.util.FuncSin;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityMachineBase extends TileEntityWireBase {

    protected double EPS = 1e-8;
    protected EnumFacing port[] = new EnumFacing[2];
    protected EnumFacing facing;
    protected int damageTime;

    public TileEntityMachineBase() {
        super();
    }

    public TileEntityMachineBase(double R, String name) {
        super(R, name);
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
        damageTime = compound.getInteger("DamageTime");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        int portIndex[] = new int[] {port[0].getIndex(), port[1].getIndex()};
        compound.setIntArray("Port", portIndex);
        compound.setInteger("Facing", facing.getIndex());
        compound.setInteger("DamageTime", damageTime);
        return super.writeToNBT(compound);
    }

    public EnumFacing[] getPort() {
        return port;
    }

    @Override
    public double getU() {
        return FuncSin.add(phi[port[0].getIndex()], phi[port[1].getIndex()].getOpposite()).getEffective();
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

    public boolean isWorking() {
        return getI() > EPS;
    }

}
