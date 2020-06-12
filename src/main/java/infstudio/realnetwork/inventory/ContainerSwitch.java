package infstudio.realnetwork.inventory;

import infstudio.realnetwork.tileentity.TileEntitySwitch;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerSwitch extends ContainerMachineBase{

    private TileEntitySwitch tile;

    public ContainerSwitch(InventoryPlayer playerInventory, TileEntitySwitch tile) {
        super(playerInventory, tile);
        this.tile = tile;
    }

    public TileEntitySwitch getTile() {
        return this.tile;
    }

}
