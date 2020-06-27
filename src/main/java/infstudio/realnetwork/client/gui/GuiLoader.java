package infstudio.realnetwork.client.gui;

import infstudio.realnetwork.RealNetwork;
import infstudio.realnetwork.inventory.*;
import infstudio.realnetwork.tileentity.*;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nullable;

public class GuiLoader implements IGuiHandler {

    public static final int GUI_AMMETER = 1;
    public static final int GUI_VOLTMETER = 2;
    public static final int GUI_SWITCH = 3;
    public static final int GUI_GENERATOR = 4;
    public static final int GUI_RESISTANCE = 5;
    public static final int GUI_GENERATOR_FURNACE = 6;
    public static final int GUI_GENERATOR_BATTERY = 7;
    public static final int GUI_ELECTRIC_FURNACE = 8;

    public GuiLoader() {
        NetworkRegistry.INSTANCE.registerGuiHandler(RealNetwork.MODID, this);
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case GUI_AMMETER: return new ContainerAmmeter(player.inventory, (TileEntityAmmeter)world.getTileEntity(new BlockPos(x, y, z)));
            case GUI_VOLTMETER: return new ContainerVoltmeter(player.inventory, (TileEntityVoltmeter)world.getTileEntity(new BlockPos(x, y, z)));
            case GUI_SWITCH: return new ContainerSwitch(player.inventory, (TileEntitySwitch)world.getTileEntity(new BlockPos(x, y, z)));
            case GUI_GENERATOR: return new ContainerGenerator(player.inventory, (TileEntityGenerator)world.getTileEntity(new BlockPos(x, y, z)));
            case GUI_RESISTANCE: return new ContainerResistance(player.inventory, (TileEntityResistance)world.getTileEntity(new BlockPos(x, y, z)));
            case GUI_GENERATOR_FURNACE: return new ContainerGeneratorFurnace(player.inventory, (TileEntityGeneratorFurnace)world.getTileEntity(new BlockPos(x, y, z)));
            case GUI_GENERATOR_BATTERY: return new ContainerGeneratorBattery(player.inventory, (TileEntityGeneratorBattery)world.getTileEntity(new BlockPos(x, y, z)));
            case GUI_ELECTRIC_FURNACE: return new ContainerElectricFurnace(player.inventory, (TileEntityElectricFurnace)world.getTileEntity(new BlockPos(x, y, z)));
            default: return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case GUI_AMMETER: return new GuiAmmeter(new ContainerAmmeter(player.inventory, (TileEntityAmmeter)world.getTileEntity(new BlockPos(x, y, z))));
            case GUI_VOLTMETER: return new GuiVoltmeter(new ContainerVoltmeter(player.inventory, (TileEntityVoltmeter)world.getTileEntity(new BlockPos(x, y, z))));
            case GUI_SWITCH: return new GuiSwitch(new ContainerSwitch(player.inventory, (TileEntitySwitch)world.getTileEntity(new BlockPos(x, y, z))));
            case GUI_GENERATOR: return new GuiGenerator(new ContainerGenerator(player.inventory, (TileEntityGenerator)world.getTileEntity(new BlockPos(x, y, z))));
            case GUI_RESISTANCE: return new GuiResistance(new ContainerResistance(player.inventory, (TileEntityResistance)world.getTileEntity(new BlockPos(x, y, z))));
            case GUI_GENERATOR_FURNACE: return new GuiGeneratorFurnace(new ContainerGeneratorFurnace(player.inventory, (TileEntityGeneratorFurnace)world.getTileEntity(new BlockPos(x, y, z))));
            case GUI_GENERATOR_BATTERY: return new GuiGeneratorBattery(new ContainerGeneratorBattery(player.inventory, (TileEntityGeneratorBattery)world.getTileEntity(new BlockPos(x, y, z))));
            case GUI_ELECTRIC_FURNACE: return new GuiElectricFurnace(new ContainerElectricFurnace(player.inventory, (TileEntityElectricFurnace)world.getTileEntity(new BlockPos(x, y, z))));
            default: return null;
        }
    }

}
