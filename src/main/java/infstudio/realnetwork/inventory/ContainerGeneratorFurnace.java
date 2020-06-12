package infstudio.realnetwork.inventory;

import infstudio.realnetwork.tileentity.TileEntityGeneratorFurnace;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerGeneratorFurnace extends ContainerMachineBase {

    private TileEntityGeneratorFurnace tile;

    public ContainerGeneratorFurnace(InventoryPlayer playerInventory, TileEntityGeneratorFurnace tile) {
        super(playerInventory, tile);
        this.tile = tile;
    }

}
