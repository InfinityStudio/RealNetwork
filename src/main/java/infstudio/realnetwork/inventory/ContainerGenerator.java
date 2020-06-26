package infstudio.realnetwork.inventory;

import infstudio.realnetwork.tileentity.TileEntityGenerator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerGenerator extends ContainerMachineBase {

    private TileEntityGenerator tile;
    private int E;

    public ContainerGenerator(InventoryPlayer playerInventory, TileEntityGenerator tile) {
        super(playerInventory, tile);
        this.tile = tile;
        this.E = (int)(tile.getE().A.get(0)*10);
    }

    public int getE() {
        return E;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < listeners.size(); ++i) {
            IContainerListener iContainerListener = listeners.get(i);
            iContainerListener.sendWindowProperty(this, 1, (int)(tile.getE().A.get(0)*10));
        }
        E = (int)(tile.getE().A.get(0)*10);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
        if (id == 1) E = data;
    }

}
