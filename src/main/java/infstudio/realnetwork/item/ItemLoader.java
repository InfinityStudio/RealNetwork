package infstudio.realnetwork.item;

import infstudio.realnetwork.RealNetwork;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = RealNetwork.MODID)
public class ItemLoader {

    public static ItemTool itemTool = new ItemTool();
    public static ItemTestTool itemTestTool = new ItemTestTool();
    public static ItemBattery itemBattery = new ItemBattery();

    public ItemLoader() {

    }

    private static void registerItem(Item item, RegistryEvent.Register<Item> event) {
        event.getRegistry().register(item);
    }

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        registerItem(itemTool, event);
        registerItem(itemTestTool, event);
        registerItem(itemBattery, event);
    }

    private static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    @SubscribeEvent
    public static void registerRender(ModelRegistryEvent event) {
        registerRender(itemTool);
        registerRender(itemTestTool);
        registerRender(itemBattery);
    }

}
