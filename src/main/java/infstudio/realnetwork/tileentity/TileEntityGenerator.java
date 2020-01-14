package infstudio.realnetwork.tileentity;

import infstudio.realnetwork.util.FuncSin;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityGenerator extends TileEntityMachineBase {

    private FuncSin E;

    public TileEntityGenerator() {
        super();
    }

    public TileEntityGenerator(double R, double Em, double phi, String name) {
        super(R, name);
        setE(Em, phi);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        NBTTagCompound nbt = (NBTTagCompound)compound.getTag("E");
        double[] arrayPhi = new double[nbt.getInteger("PhiSize")];
        for (int j = 0; j < arrayPhi.length; ++j) {
            arrayPhi[j] = nbt.getDouble("Phi"+j);
        }
        double[] arrayA = new double[nbt.getInteger("ASize")];
        for (int j = 0; j < arrayA.length; ++j) {
            arrayA[j] = nbt.getDouble("A"+j);
        }
        E = new FuncSin(arrayPhi, arrayA);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound nbt = new NBTTagCompound();
        double[] arrayPhi = E.arrayPhi();
        for (int j = 0; j < arrayPhi.length; ++j) {
            nbt.setDouble("Phi"+j, arrayPhi[j]);
        }
        nbt.setInteger("PhiSize", arrayPhi.length);
        double[] arrayA = E.arrayA();
        for (int j = 0; j < arrayPhi.length; ++j) {
            nbt.setDouble("A"+j, arrayA[j]);
        }
        nbt.setInteger("ASize", arrayA.length);
        compound.setTag("E", nbt);
        return super.writeToNBT(compound);
    }

    public FuncSin getE() {
        return E;
    }

    public void setE(double A, double phi) {
        this.E = new FuncSin(phi, A);
    }

    public void setPositive(EnumFacing positive) {
        this.port[0] = positive;
    }

    public void setNegative(EnumFacing negative) {
        this.port[1] = negative;
    }

}
