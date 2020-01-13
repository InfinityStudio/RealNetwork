package infstudio.realnetwork.gui;

import infstudio.realnetwork.RealNetwork;
import infstudio.realnetwork.container.ContainerAmmeter;
import infstudio.realnetwork.container.ContainerVoltmeter;
import infstudio.realnetwork.tileentity.TileEntityAmmeter;
import infstudio.realnetwork.tileentity.TileEntityVoltmeter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
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
            case GUI_AMMETER: return new ContainerAmmeter(player.inventory, (TileEntityAmmeter)world.getTileEntity(new BlockPos(x, y, z)));
            case GUI_VOLTMETER: return new ContainerVoltmeter(player.inventory, (TileEntityVoltmeter)world.getTileEntity(new BlockPos(x, y, z)));
            default: return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case GUI_AMMETER: return new GuiAmmeter(new ContainerAmmeter(player.inventory, (TileEntityAmmeter)world.getTileEntity(new BlockPos(x, y, z))));
            case GUI_VOLTMETER: return new GuiVoltmeter(new ContainerVoltmeter(player.inventory, (TileEntityVoltmeter)world.getTileEntity(new BlockPos(x, y, z))));
            default: return null;
        }
    }

}
