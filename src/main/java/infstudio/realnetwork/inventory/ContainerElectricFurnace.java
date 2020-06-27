package infstudio.realnetwork.inventory;

import infstudio.realnetwork.tileentity.TileEntityElectricFurnace;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ContainerElectricFurnace extends ContainerMachineBase {

    private TileEntityElectricFurnace tile;
    private IItemHandler itemInput, itemOutput;
    private int cookEnergy;
    private int totalCookEnergy;

    public ContainerElectricFurnace(InventoryPlayer playerInventory, TileEntityElectricFurnace tile) {
        super(playerInventory, tile);
        this.tile = tile;
        this.cookEnergy = (int)tile.getCookEnergy();
        this.totalCookEnergy = (int)tile.getTotalCookEnergy();
        this.itemInput = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        this.itemOutput = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
        this.addSlotToContainer(new SlotItemHandler(this.itemInput, 0, 80, 32));
        this.addSlotToContainer(new SlotItemHandler(this.itemOutput, 0, 134, 32) {
            @Override
            public boolean isItemValid(@Nonnull ItemStack stack) {
                return false;
            }
        });
    }

    public int getCookEnergy() {
        return this.cookEnergy;
    }

    public int getTotalCookEnergy() {
        return this.totalCookEnergy;
    }

    public boolean isBurning() {
        return this.cookEnergy > 0.0D;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < listeners.size(); ++i) {
            IContainerListener iContainerListener = listeners.get(i);
            iContainerListener.sendWindowProperty(this, 1, (int)tile.getCookEnergy());
            iContainerListener.sendWindowProperty(this, 2, (int)tile.getTotalCookEnergy());
        }
        this.cookEnergy = (int)tile.getCookEnergy();
        this.totalCookEnergy = (int)tile.getTotalCookEnergy();
    }

    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
        if (id == 1) cookEnergy = data;
        if (id == 2) totalCookEnergy = data;
    }

}
