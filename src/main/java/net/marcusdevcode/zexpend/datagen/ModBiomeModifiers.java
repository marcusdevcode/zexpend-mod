package net.marcusdevcode.zexpend.datagen;

import net.marcusdevcode.zexpend.ZexpendMod;
import net.marcusdevcode.zexpend.entities.ModEntities;
import net.marcusdevcode.zexpend.entities.ZombieVariants;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_NETHER_ZOMBIE_SPAWNS = key("add_nether_zombie_spawns");
    public static final ResourceKey<BiomeModifier> ADD_OVERWORLD_ZOMBIE_SPAWNS = key("add_overworld_zombie_spawns");

    private static ResourceKey<BiomeModifier> key(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                Identifier.fromNamespaceAndPath(ZexpendMod.MOD_ID, name));
    }

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);

        context.register(ADD_NETHER_ZOMBIE_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_NETHER),
                WeightedList.<MobSpawnSettings.SpawnerData>builder()
                        .add(new MobSpawnSettings.SpawnerData(ModEntities.get(ZombieVariants.NETHER), 2, 3), 20)
                        .build()));

        WeightedList.Builder<MobSpawnSettings.SpawnerData> overworldSpawns = WeightedList.builder();
        for (ZombieVariants variant : ZombieVariants.values()) {
            if (variant == ZombieVariants.NETHER) {
                continue;
            }
            overworldSpawns.add(new MobSpawnSettings.SpawnerData(ModEntities.get(variant), 1, 2), spawnWeight(variant));
        }
        context.register(ADD_OVERWORLD_ZOMBIE_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD), overworldSpawns.build()));
    }

    private static int spawnWeight(ZombieVariants variant) {
        return switch (variant) {
            case NOTCH, KING, FREDBEAR, PVZ, APOCALYPSE -> 2;
            case HEROBRINE, HEROBRINES_MOM, HEROBRINE_MINION -> 3;
            default -> 8;
        };
    }
}
