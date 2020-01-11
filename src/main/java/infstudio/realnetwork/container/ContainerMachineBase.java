package infstudio.realnetwork.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerMachineBase extends Container {

    public ContainerMachineBase() {

    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

}
