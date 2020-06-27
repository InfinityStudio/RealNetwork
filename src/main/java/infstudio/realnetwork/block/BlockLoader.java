package infstudio.realnetwork.block;

import infstudio.realnetwork.RealNetwork;
import infstudio.realnetwork.client.render.RenderMachineBase;
import infstudio.realnetwork.client.render.RenderWire;
import infstudio.realnetwork.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber(modid = RealNetwork.MODID)
public class BlockLoader {

    public static BlockWire blockWire = new BlockWire();
    public static BlockGenerator blockGenerator = new BlockGenerator();
    public static BlockGeneratorFurnace blockGeneratorFurnace = new BlockGeneratorFurnace();
    public static BlockGeneratorBattery blockGeneratorBattery = new BlockGeneratorBattery();
    public static BlockResistance blockResistance = new BlockResistance();
    public static BlockAmmeter blockAmmeter = new BlockAmmeter();
    public static BlockVoltmeter blockVoltmeter = new BlockVoltmeter();
    public static BlockSwitch blockSwitch = new BlockSwitch();
    public static BlockElectricFurnace blockElectricFurnace = new BlockElectricFurnace();

    public BlockLoader() {

    }

    private static void registerBlock(BlockWireBase block, RegistryEvent.Register<Block> event) {
        block.setRegistryName(RealNetwork.MODID, block.getName());
        event.getRegistry().register(block);
        GameRegistry.registerTileEntity(block.getTileEntity(), new ResourceLocation(RealNetwork.MODID, block.getName()));
    }

    @SubscribeEvent
    public static void registerBlock(RegistryEvent.Register<Block> event) {
        registerBlock(blockWire, event);
        registerBlock(blockGenerator, event);
        registerBlock(blockGeneratorFurnace, event);
        registerBlock(blockGeneratorBattery, event);
        registerBlock(blockResistance, event);
        registerBlock(blockAmmeter, event);
        registerBlock(blockVoltmeter, event);
        registerBlock(blockSwitch, event);
        registerBlock(blockElectricFurnace, event);
    }

    private static void registerItem(BlockWireBase block, RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()).setCreativeTab(block.getCreativeTab()).setTranslationKey(block.getTranslationKey()));
    }

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        registerItem(blockWire, event);
        registerItem(blockGenerator, event);
        registerItem(blockGeneratorFurnace, event);
        registerItem(blockGeneratorBattery, event);
        registerItem(blockResistance, event);
        registerItem(blockAmmeter, event);
        registerItem(blockVoltmeter, event);
        registerItem(blockSwitch, event);
        registerItem(blockElectricFurnace, event);
    }

    private static void registerRender(Block block) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

    @SubscribeEvent
    public static void registerRender(ModelRegistryEvent event) {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineBase.class, new RenderMachineBase());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWire.class, new RenderWire());
        registerRender(blockWire);
        registerRender(blockGenerator);
        registerRender(blockGeneratorFurnace);
        registerRender(blockGeneratorBattery);
        registerRender(blockResistance);
        registerRender(blockAmmeter);
        registerRender(blockVoltmeter);
        registerRender(blockSwitch);
        registerRender(blockElectricFurnace);
    }

}
