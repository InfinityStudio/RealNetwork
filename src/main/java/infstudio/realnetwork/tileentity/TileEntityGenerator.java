package infstudio.realnetwork.tileentity;

import infstudio.realnetwork.util.FuncSin;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityGenerator extends TileEntityMachineBase {

    protected FuncSin E;
    protected double energy = 0, energyCapacity;

    public TileEntityGenerator() {
        super();
    }

    public TileEntityGenerator(double R, double Em, double phi, double capacity, String name) {
        super(R, name);
        setE(Em, phi);
        setEnergyCapacity(capacity);
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
        energy = compound.getDouble("Energy");
        energyCapacity = compound.getDouble("EnergyCapacity");
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
        compound.setDouble("Energy", energy);
        compound.setDouble("EnergyCapacity", energyCapacity);
        return super.writeToNBT(compound);
    }

    public void setE(double A, double phi) {
        this.E = new FuncSin(phi, A);
    }

    public FuncSin getE() {
        return E;
    }

    public void setEnergyCapacity(double capacity) {
        this.energyCapacity = capacity;
    }

    public double getEnergyCapacity() {
        return this.energyCapacity;
    }

    public double getEnergy() {
        return this.energy;
    }

    public void incEnergy(double amount) {
        this.energy += amount;
        if (this.energy > this.energyCapacity) this.energy = this.energyCapacity;
    }

    public void decEnergy(double amount) {
        this.energy -= amount;
        if (this.energy < 0) this.energy = 0;
    }

    public void setPositive(EnumFacing positive) {
        this.port[0] = positive;
    }

    public void setNegative(EnumFacing negative) {
        this.port[1] = negative;
    }

    @Override
    public double getP() {
        return isWorking() ? getE().getEffective()*(getE().getEffective()-getU())/R : 0.0D;
    }

}
