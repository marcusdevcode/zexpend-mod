package net.marcusdevcode.zexpend.datagen;

import net.marcusdevcode.zexpend.ZexpendMod;
import net.marcusdevcode.zexpend.entities.ModBulkEntities;
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
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_NETHER_ZOMBIE_SPAWNS = key("add_nether_zombie_spawns");
    public static final ResourceKey<BiomeModifier> ADD_OVERWORLD_ZOMBIE_SPAWNS = key("add_overworld_zombie_spawns");
    public static final ResourceKey<BiomeModifier> ADD_DESERT_HUSK_SPAWNS = key("add_desert_husk_spawns");
    public static final ResourceKey<BiomeModifier> ADD_OCEAN_DROWNED_SPAWNS = key("add_ocean_drowned_spawns");
    public static final ResourceKey<BiomeModifier> ADD_RIVER_DROWNED_SPAWNS = key("add_river_drowned_spawns");

    private static final int BULK_SPAWN_WEIGHT = 1;

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
        for (int n : ModBulkEntities.allZombies().keySet()) {
            overworldSpawns.add(new MobSpawnSettings.SpawnerData(ModBulkEntities.zombie(n), 1, 2), BULK_SPAWN_WEIGHT);
        }
        context.register(ADD_OVERWORLD_ZOMBIE_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD), overworldSpawns.build()));

        WeightedList.Builder<MobSpawnSettings.SpawnerData> huskSpawns = WeightedList.builder();
        for (int n : ModBulkEntities.allHusks().keySet()) {
            huskSpawns.add(new MobSpawnSettings.SpawnerData(ModBulkEntities.husk(n), 1, 2), BULK_SPAWN_WEIGHT);
        }
        context.register(ADD_DESERT_HUSK_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_DESERT), huskSpawns.build()));

        WeightedList.Builder<MobSpawnSettings.SpawnerData> drownedSpawns = WeightedList.builder();
        for (int n : ModBulkEntities.allDrowned().keySet()) {
            drownedSpawns.add(new MobSpawnSettings.SpawnerData(ModBulkEntities.drowned(n), 1, 1), BULK_SPAWN_WEIGHT);
        }
        context.register(ADD_OCEAN_DROWNED_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OCEAN), drownedSpawns.build()));
        context.register(ADD_RIVER_DROWNED_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_RIVER), drownedSpawns.build()));
    }

    private static int spawnWeight(ZombieVariants variant) {
        return switch (variant) {
            case NOTCH, KING, FREDBEAR, PVZ, APOCALYPSE -> 4;
            case HEROBRINE, HEROBRINES_MOM, HEROBRINE_MINION -> 6;
            default -> 15;
        };
    }
}
