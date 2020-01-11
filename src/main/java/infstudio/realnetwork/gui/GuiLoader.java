package infstudio.realnetwork.gui;

import infstudio.realnetwork.RealNetwork;
import infstudio.realnetwork.container.ContainerAmmeter;
import infstudio.realnetwork.container.ContainerVoltmeter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nullable;

public class GuiLoader implements IGuiHandler {

    public static final int GUI_AMMETER = 1;
    public static final int GUI_VOLTMETER = 2;

    public GuiLoader() {
        NetworkRegistry.INSTANCE.registerGuiHandler(RealNetwork.MODID, this);
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case GUI_AMMETER: return new ContainerAmmeter();
            case GUI_VOLTMETER: return new ContainerVoltmeter();
            default: return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case GUI_AMMETER: return new GuiAmmeter(new ContainerAmmeter());
            case GUI_VOLTMETER: return new GuiVoltmeter(new ContainerVoltmeter());
            default: return null;
        }
    }

}
