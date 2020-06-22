package infstudio.realnetwork.tileentity;

import infstudio.realnetwork.util.FuncSin;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;

public class TileEntityWireBase extends TileEntity implements ITickable {

    protected double R;
    protected FuncSin phi[] = new FuncSin[] {new FuncSin(), new FuncSin(), new FuncSin(), new FuncSin(), new FuncSin(), new FuncSin(), new FuncSin()};
    protected String name;
    protected double damage;

    public TileEntityWireBase() {

    }

    public TileEntityWireBase(double R, String name) {
        setResistance(R);
        this.name = name;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        for (int i = 0; i < 7; ++i) {
            NBTTagCompound nbt = (NBTTagCompound)compound.getTag("Phi"+i);
            double[] arrayPhi = new double[nbt.getInteger("PhiSize")];
            for (int j = 0; j < arrayPhi.length; ++j) {
                arrayPhi[j] = nbt.getDouble("Phi"+j);
            }
            double[] arrayA = new double[nbt.getInteger("ASize")];
            for (int j = 0; j < arrayA.length; ++j) {
                arrayA[j] = nbt.getDouble("A"+j);
            }
            phi[i] = new FuncSin(arrayPhi, arrayA);
        }
        R = compound.getDouble("Resistance");
        damage = compound.getDouble("Damage");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        for (int i = 0; i < 7; ++i) {
            NBTTagCompound nbt = new NBTTagCompound();
            double[] arrayPhi = phi[i].arrayPhi();
            for (int j = 0; j < arrayPhi.length; ++j) {
                nbt.setDouble("Phi"+j, arrayPhi[j]);
            }
            nbt.setInteger("PhiSize", arrayPhi.length);
            double[] arrayA = phi[i].arrayA();
            for (int j = 0; j < arrayPhi.length; ++j) {
                nbt.setDouble("A"+j, arrayA[j]);
            }
            nbt.setInteger("ASize", arrayA.length);
            compound.setTag("Phi"+i, nbt);
        }
        compound.setDouble("Resistance", R);
        compound.setDouble("Damage", damage);
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

    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString(name);
    }

    public double getResistance() {
        return R;
    }

    public void setResistance(double R) {
        this.R = R;
    }

    public double getU() {
        return FuncSin.add(phi[0], phi[1].getOpposite()).getEffective();
    }

    public double getI() {
        return getU()/R;
    }

    public double getP() {
        return getU()*getI();
    }

    public void setPhi(FuncSin phi, int index) {
        this.phi[index] = phi;
    }

    public void setPhi(FuncSin phi[]) {
        this.phi = phi;
    }

    public void setDamage(double damage) {
        this.damage = damage > 100.0D ? 100.0D : damage;
    }

    public double getDamage() {
        return this.damage;
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

    @Override
    public void update() {
        if (!this.world.isRemote && this.getDamage() >= 100.0D) {
            this.world.createExplosion(null, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), 0.1F, true);
        }
    }

}
