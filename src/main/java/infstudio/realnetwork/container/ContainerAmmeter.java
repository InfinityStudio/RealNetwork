package infstudio.realnetwork.container;

import infstudio.realnetwork.tileentity.TileEntityAmmeter;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < listeners.size(); ++i) {
            IContainerListener iContainerListener = listeners.get(i);
            if (I != (int)(tile.getI()*100)) {
                iContainerListener.sendWindowProperty(this, 1, (int)(tile.getI()*100));
            }
        }
        I = (int)(tile.getI()*100);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
        if (id == 1) I = data;
    }

}
