package infstudio.realnetwork.inventory;

import infstudio.realnetwork.tileentity.TileEntityMachineBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineBase extends Container {

    private TileEntityMachineBase tileMachine;
    private int damage;

    public ContainerMachineBase(InventoryPlayer playerInventory, TileEntityMachineBase tileMachine) {
        this.tileMachine = tileMachine;
        this.damage = (int)tileMachine.getDamage();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int k = 0; k < 9; ++k) {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    public TileEntityMachineBase getTileMachine() {
        return tileMachine;
    }

    public int getDamage() {
        return this.damage;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < listeners.size(); ++i) {
            IContainerListener iContainerListener = listeners.get(i);
            iContainerListener.sendWindowProperty(this, 0, (int)tileMachine.getDamage());
        }
        damage = (int)tileMachine.getDamage();
    }

    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
        if (id == 0) damage = data;
    }

}
