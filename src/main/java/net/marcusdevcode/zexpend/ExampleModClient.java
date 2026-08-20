package net.marcusdevcode.zexpend;

import net.marcusdevcode.zexpend.client.BulkDrownedRenderer;
import net.marcusdevcode.zexpend.client.BulkHuskRenderer;
import net.marcusdevcode.zexpend.client.BulkZombieRenderer;
import net.marcusdevcode.zexpend.client.CustomZombieRenderer;
import net.marcusdevcode.zexpend.entities.ModBulkEntities;
import net.marcusdevcode.zexpend.entities.ModEntities;
import net.marcusdevcode.zexpend.entities.ZombieVariants;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = ZexpendMod.MOD_ID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = ZexpendMod.MOD_ID, value = Dist.CLIENT)
public class ExampleModClient {
    public ExampleModClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        ZexpendMod.LOGGER.info("HELLO FROM CLIENT SETUP");
        ZexpendMod.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent
    static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        for (ZombieVariants variant : ZombieVariants.values()) {
            event.registerEntityRenderer(ModEntities.get(variant), CustomZombieRenderer::new);
        }
        for (int n : ModBulkEntities.allZombies().keySet()) {
            event.registerEntityRenderer(ModBulkEntities.zombie(n), BulkZombieRenderer::new);
        }
        for (int n : ModBulkEntities.allHusks().keySet()) {
            event.registerEntityRenderer(ModBulkEntities.husk(n), BulkHuskRenderer::new);
        }
        for (int n : ModBulkEntities.allDrowned().keySet()) {
            event.registerEntityRenderer(ModBulkEntities.drowned(n), BulkDrownedRenderer::new);
        }
    }
}
