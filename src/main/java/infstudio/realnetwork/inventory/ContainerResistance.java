package infstudio.realnetwork.inventory;

import infstudio.realnetwork.tileentity.TileEntityResistance;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerResistance extends ContainerMachineBase {

    private TileEntityResistance tile;
    private int R;

    public ContainerResistance(InventoryPlayer playerInventory, TileEntityResistance tile) {
        super(playerInventory, tile);
        this.tile = tile;
        this.R = (int)(tile.getResistance()*10);
    }

    public int getR() {
        return R;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < listeners.size(); ++i) {
            IContainerListener iContainerListener = listeners.get(i);
            iContainerListener.sendWindowProperty(this, 1, (int)(tile.getResistance()*10));
        }
        R = (int)(tile.getResistance()*10);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
        if (id == 1) R = data;
    }

}
