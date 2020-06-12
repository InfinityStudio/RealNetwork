package infstudio.realnetwork.common;

import infstudio.realnetwork.block.BlockLoader;
import infstudio.realnetwork.client.gui.GuiLoader;
import infstudio.realnetwork.item.ItemLoader;
import infstudio.realnetwork.network.NetworkLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public abstract class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        new BlockLoader();
        new ItemLoader();
        new GuiLoader();
        new NetworkLoader();
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

}
