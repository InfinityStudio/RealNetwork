package infstudio.realnetwork;

import infstudio.realnetwork.common.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = RealNetwork.MODID, name = RealNetwork.NAME, version = RealNetwork.VERSION)
public class RealNetwork {
    public static final String MODID = "realnetwork";
    public static final String NAME = "Real Network";
    public static final String VERSION = "1.0.0";

    @Mod.Instance(RealNetwork.MODID)
    public static RealNetwork instance;

    @SidedProxy(clientSide = "infstudio.realnetwork.client.ClientProxy", serverSide = "infstudio.realnetwork.common.CommonProxy")
    public static CommonProxy proxy;

    public static CreativeTabs rnTab = new CreativeTabs(MODID) {
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(Items.APPLE);
        }
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

}