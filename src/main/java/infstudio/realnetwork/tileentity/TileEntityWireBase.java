package infstudio.realnetwork.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;

public class TileEntityWireBase extends TileEntity {

    protected double R;
    protected double phi[] = new double[7];

    public TileEntityWireBase() {

    }

    public TileEntityWireBase(double R) {
        setPhi(new double[] {0, 0, 0, 0, 0, 0, 0});
        setResistance(R);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        for (int i = 0; i < 7; ++i) {
            phi[i] = compound.getDouble("Phi"+i);
        }
        R = compound.getDouble("Resistance");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        for (int i = 0; i < 7; ++i) {
            compound.setDouble("Phi"+i, phi[i]);
        }
        compound.setDouble("Resistance", R);
        return super.writeToNBT(compound);
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound compound = new NBTTagCompound();
        compound = writeToNBT(compound);
        return new SPacketUpdateTileEntity(getPos(), 1, compound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound compound = new NBTTagCompound();
        compound = writeToNBT(compound);
        return compound;
    }

    public double getResistance() {
        return R;
    }

    public void setResistance(double R) {
        this.R = R;
    }

    public double getU() {
        return Math.abs(phi[1]-phi[0]);
    }

    public double getI() {
        return getU()/R;
    }

    public double getP() {
        return getU()*getI();
    }

    public void setPhi(double phiA, double phiB) {
        this.phi[1] = phiA;
        this.phi[0] = phiB;
    }

    public void setPhi(double phi, int index) {
        this.phi[index] = phi;
    }

    public void setPhi(double phi[]) {
        this.phi = phi;
    }

    protected EnumFacing getFaceByIndex(int index) {
        switch (index) {
            case 0: return EnumFacing.DOWN;
            case 1: return EnumFacing.UP;
            case 2: return EnumFacing.NORTH;
            case 3: return EnumFacing.SOUTH;
            case 4: return EnumFacing.WEST;
            case 5: return EnumFacing.EAST;
        }
        return EnumFacing.NORTH;
    }

}
