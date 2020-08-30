package snownee.jade;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import snownee.jade.client.ItemTooltipHandler;

@Mod(Jade.MODID)
public class Jade {
    public static final String MODID = "jade";

    public Jade() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, JadeCommonConfig.spec);
        FMLJavaModLoadingContext.get().getModEventBus().register(JadeCommonConfig.class);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientInit);
    }

    private void clientInit(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(ItemTooltipHandler.getInstance());
    }

    private void init(FMLCommonSetupEvent event) {
        JadeCommonConfig.refresh();
    }
}
