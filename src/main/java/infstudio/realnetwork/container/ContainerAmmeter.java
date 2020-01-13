package infstudio.realnetwork.container;

import infstudio.realnetwork.tileentity.TileEntityAmmeter;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerAmmeter extends ContainerMachineBase {

    private TileEntityAmmeter tile;
    private int I;

    public ContainerAmmeter(InventoryPlayer playerInventory, TileEntityAmmeter tile) {
        super(playerInventory, tile);
        this.tile = tile;
        this.I = (int)(tile.getI()*100);
    }

    public int getI() {
        return I;
    }

}
