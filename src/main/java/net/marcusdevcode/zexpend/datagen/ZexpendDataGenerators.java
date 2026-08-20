package net.marcusdevcode.zexpend.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.concurrent.CompletableFuture;

public class ZexpendDataGenerators {
    public static void onGatherDataServer(GatherDataEvent.Server event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new ModLootTableProvider(output, lookupProvider));
        generator.addProvider(true, new ModEntityTypeTagsProvider(output, lookupProvider));

        RegistrySetBuilder biomeModifierBuilder = new RegistrySetBuilder()
                .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);
        event.createDatapackRegistryObjects(biomeModifierBuilder);
    }

    public static void onGatherDataClient(GatherDataEvent.Client event) {
        event.getGenerator().addProvider(true, new ModLanguageProvider(event.getGenerator().getPackOutput()));
    }
}
