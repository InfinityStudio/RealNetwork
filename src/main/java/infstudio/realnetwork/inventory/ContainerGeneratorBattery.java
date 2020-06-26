package infstudio.realnetwork.inventory;

import infstudio.realnetwork.tileentity.TileEntityGeneratorBattery;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerGeneratorBattery extends ContainerMachineBase {

    private TileEntityGeneratorBattery tile;
    private IItemHandler itemApp;

    public ContainerGeneratorBattery(InventoryPlayer playerInventory, TileEntityGeneratorBattery tile) {
        super(playerInventory, tile);
        this.tile = tile;
        this.itemApp = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        this.addSlotToContainer(new SlotAppliance(itemApp, 0, 110, 32));
    }

}
