package infstudio.realnetwork.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityGenerator extends TileEntityMachineBase {

    private double E;

    public TileEntityGenerator() {
        super();
    }

    public TileEntityGenerator(double R, double E) {
        super(R);
        setE(E);
        setPositive(EnumFacing.NORTH);
        setNegative(EnumFacing.SOUTH);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        E = compound.getDouble("E");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setDouble("E", E);
        return super.writeToNBT(compound);
    }

    public double getE() {
        return E;
    }

    public void setE(double E) {
        this.E = E;
    }

    public void setPositive(EnumFacing positive) {
        this.port[0] = positive;
    }

    public void setNegative(EnumFacing negative) {
        this.port[1] = negative;
    }

}
