package infstudio.realnetwork.inventory;

import infstudio.realnetwork.tileentity.TileEntityGeneratorBattery;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerGeneratorBattery extends ContainerMachineBase {

    private TileEntityGeneratorBattery tile;

    public ContainerGeneratorBattery(InventoryPlayer playerInventory, TileEntityGeneratorBattery tile) {
        super(playerInventory, tile);
        this.tile = tile;
    }

}
