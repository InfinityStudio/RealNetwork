package infstudio.realnetwork.inventory;

import infstudio.realnetwork.tileentity.TileEntityVoltmeter;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerVoltmeter extends ContainerMachineBase {

    private TileEntityVoltmeter tile;
    private int U;

    public ContainerVoltmeter(InventoryPlayer playerInventory, TileEntityVoltmeter tile) {
        super(playerInventory, tile);
        this.tile = tile;
        this.U = (int)(tile.getU()*100);
    }

    public int getU() {
        return U;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < listeners.size(); ++i) {
            IContainerListener iContainerListener = listeners.get(i);
            iContainerListener.sendWindowProperty(this, 1, (int)(tile.getU()*10));
        }
        U = (int)(tile.getU()*100);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
        if (id == 1) U = data;
    }

}
