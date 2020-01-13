package infstudio.realnetwork.container;

import infstudio.realnetwork.tileentity.TileEntityVoltmeter;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerVoltmeter extends ContainerMachineBase {

    private TileEntityVoltmeter tile;

    public ContainerVoltmeter(InventoryPlayer playerInventory, TileEntityVoltmeter tile) {
        super(playerInventory, tile);
        this.tile = tile;
    }

}
