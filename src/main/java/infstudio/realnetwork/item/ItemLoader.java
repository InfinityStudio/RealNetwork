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

    private static Item itemList[] = new Item[] {
        new ItemTool(), new ItemTestTool()
    };

    public ItemLoader() {

    }

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        for (int i = 0; i < itemList.length; ++i) {
            event.getRegistry().register(itemList[i]);
        }
    }

    private static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    @SubscribeEvent
    public static void registerRender(ModelRegistryEvent event) {
        for (int i = 0; i < itemList.length; ++i) {
            registerRender(itemList[i]);
        }
    }

}
