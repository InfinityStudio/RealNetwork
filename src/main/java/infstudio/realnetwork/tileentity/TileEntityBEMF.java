package infstudio.realnetwork.tileentity;

public class TileEntityBEMF extends TileEntityResistance {

    protected double BE;

    public TileEntityBEMF() {
        super();
    }

    public TileEntityBEMF(double R, double BE, String name) {
        super(R, name);
        setBE(BE);
    }

    public double getBE() {
        return BE;
    }

    public void setBE(double BE) {
        this.BE = BE;
    }

}
