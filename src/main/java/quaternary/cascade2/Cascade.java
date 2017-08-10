package quaternary.cascade2;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quaternary.cascade2.aura.type.CascadeAuraTypes;
import quaternary.cascade2.block.CascadeBlock;
import quaternary.cascade2.block.ModBlocks;
import quaternary.cascade2.item.ModItems;
import quaternary.cascade2.misc.CascadeCreativeTab;
import quaternary.cascade2.net.ModPackets;
import quaternary.cascade2.proxy.CommonProxy;
import quaternary.cascade2.util.CascadeUtilEvents;
import quaternary.cascade2.util.CascadeUtils;

@Mod(modid = Cascade.MODID, name = Cascade.NAME, version = Cascade.VERSION)
public class Cascade {
	public static final String MODID   = "cascade2";
	public static final String NAME    = "Cascade";
	public static final String VERSION = "0";
	
	@Mod.Instance
	public static Cascade INSTANCE = new Cascade();
	
	@SidedProxy(clientSide = "quaternary.cascade2.proxy.ClientProxy",
	            serverSide = "quaternary.cascade2.proxy.CommonProxy")
	public static CommonProxy PROXY;
	
	public static final CascadeCreativeTab CREATIVE_TAB = new CascadeCreativeTab();
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	public static SimpleNetworkWrapper netwrapper;
	
	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent e) {
		PROXY.registerTESRs();
		
		//todo: Dummy
		netwrapper = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
		ModPackets.registerPackets(netwrapper);
		
		CascadeAuraTypes.registerAuraTypes();
		
		MinecraftForge.EVENT_BUS.register(CascadeUtilEvents.class);
	}
	
	@Mod.EventBusSubscriber
	public static class RegistrationEvents {	
		@SubscribeEvent
		public static void blocks(RegistryEvent.Register<Block> e) {
			ModBlocks.registerBlocks(e.getRegistry());
		}
		
		@SubscribeEvent
		public static void items(RegistryEvent.Register<Item> e) {
			ModBlocks.registerItemBlocks(e.getRegistry());
			ModItems.registerItems(e.getRegistry());
		}
				
		@SubscribeEvent
		public static void itemModels(ModelRegistryEvent e) {
			ModBlocks.registerItemBlockModels();
			ModItems.registerItemModels();
		}
	}
}
