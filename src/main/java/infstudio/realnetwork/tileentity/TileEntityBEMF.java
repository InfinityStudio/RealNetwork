package infstudio.realnetwork.tileentity;

import net.minecraft.util.EnumFacing;

public class TileEntityBEMF extends TileEntityResistance {

    protected double BE;

    public TileEntityBEMF() {
        super();
    }

    public TileEntityBEMF(double R, double BE) {
        super(R);
        setBE(BE);
    }

    public double getBE() {
        return BE;
    }

    public void setBE(double BE) {
        this.BE = BE;
    }

}
