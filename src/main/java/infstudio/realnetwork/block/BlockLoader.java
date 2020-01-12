package infstudio.realnetwork.block;

import infstudio.realnetwork.RealNetwork;
import infstudio.realnetwork.render.RenderMachineBase;
import infstudio.realnetwork.render.RenderWire;
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

    private static BlockWireBase[] blockList = new BlockWireBase[] {
            new BlockWire(), new BlockGenerator(), new BlockResistance(), new BlockBEMF(), new BlockAmmeter(), new BlockVoltmeter()
    };

    public BlockLoader() {

    }

    @SubscribeEvent
    public static void registerBlock(RegistryEvent.Register<Block> event) {
        for (int i = 0; i < blockList.length; ++i) {
            blockList[i].setRegistryName(RealNetwork.MODID, blockList[i].getName());
            event.getRegistry().register(blockList[i]);
        }
        GameRegistry.registerTileEntity(TileEntityWire.class, new ResourceLocation(RealNetwork.MODID, blockList[0].getName()));
        GameRegistry.registerTileEntity(TileEntityGenerator.class, new ResourceLocation(RealNetwork.MODID, blockList[1].getName()));
        GameRegistry.registerTileEntity(TileEntityResistance.class, new ResourceLocation(RealNetwork.MODID, blockList[2].getName()));
        GameRegistry.registerTileEntity(TileEntityBEMF.class, new ResourceLocation(RealNetwork.MODID, blockList[3].getName()));
        GameRegistry.registerTileEntity(TileEntityAmmeter.class, new ResourceLocation(RealNetwork.MODID, blockList[4].getName()));
        GameRegistry.registerTileEntity(TileEntityVoltmeter.class, new ResourceLocation(RealNetwork.MODID, blockList[5].getName()));
    }

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        for (int i = 0; i < blockList.length; ++i) {
            event.getRegistry().register(new ItemBlock(blockList[i]).setRegistryName(blockList[i].getRegistryName()).setCreativeTab(blockList[i].getCreativeTabToDisplayOn()).setUnlocalizedName(blockList[i].getUnlocalizedName()));
        }
    }

    private static void registerRender(Block block) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

    @SubscribeEvent
    public static void registerRender(ModelRegistryEvent event) {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineBase.class, new RenderMachineBase());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWire.class, new RenderWire());
    }

}
