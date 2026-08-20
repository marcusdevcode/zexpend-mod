package net.marcusdevcode.zexpend.item;

import net.marcusdevcode.zexpend.ZexpendMod;
import net.marcusdevcode.zexpend.entities.ModBulkEntities;
import net.marcusdevcode.zexpend.entities.ModEntities;
import net.marcusdevcode.zexpend.entities.ZombieVariants;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ZexpendMod.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ZexpendMod.MOD_ID);

    private static final Map<ZombieVariants, DeferredHolder<Item, SpawnEggItem>> SPAWN_EGGS =
            new EnumMap<>(ZombieVariants.class);
    private static final Map<Integer, DeferredHolder<Item, SpawnEggItem>> BULK_ZOMBIE_EGGS = new LinkedHashMap<>();
    private static final Map<Integer, DeferredHolder<Item, SpawnEggItem>> BULK_HUSK_EGGS = new LinkedHashMap<>();
    private static final Map<Integer, DeferredHolder<Item, SpawnEggItem>> BULK_DROWNED_EGGS = new LinkedHashMap<>();

    static {
        for (ZombieVariants variant : ZombieVariants.values()) {
            SPAWN_EGGS.put(variant, ITEMS.registerItem(variant.getId() + "_spawn_egg", properties ->
                    new SpawnEggItem(properties.spawnEgg(ModEntities.get(variant)))));
        }
        for (int n : ModBulkEntities.allZombies().keySet()) {
            BULK_ZOMBIE_EGGS.put(n, ITEMS.registerItem("zombie_" + n + "_spawn_egg", properties ->
                    new SpawnEggItem(properties.spawnEgg(ModBulkEntities.zombie(n)))));
        }
        for (int n : ModBulkEntities.allHusks().keySet()) {
            BULK_HUSK_EGGS.put(n, ITEMS.registerItem("husk_" + n + "_spawn_egg", properties ->
                    new SpawnEggItem(properties.spawnEgg(ModBulkEntities.husk(n)))));
        }
        for (int n : ModBulkEntities.allDrowned().keySet()) {
            BULK_DROWNED_EGGS.put(n, ITEMS.registerItem("drowned_" + n + "_spawn_egg", properties ->
                    new SpawnEggItem(properties.spawnEgg(ModBulkEntities.drowned(n)))));
        }
    }

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ZOMBIES_TAB = CREATIVE_MODE_TABS.register("zombies_tab", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.zexpendmod.zombies"))
                    .icon(() -> SPAWN_EGGS.get(ZombieVariants.DISCO).get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        allSpawnEggs().values().forEach(egg -> output.accept(egg.get()));
                        allBulkZombieEggs().values().forEach(egg -> output.accept(egg.get()));
                        allBulkHuskEggs().values().forEach(egg -> output.accept(egg.get()));
                        allBulkDrownedEggs().values().forEach(egg -> output.accept(egg.get()));
                    })
                    .build());

    public static DeferredHolder<Item, SpawnEggItem> spawnEgg(ZombieVariants variant) {
        return SPAWN_EGGS.get(variant);
    }

    public static Map<ZombieVariants, DeferredHolder<Item, SpawnEggItem>> allSpawnEggs() {
        return SPAWN_EGGS;
    }

    public static Map<Integer, DeferredHolder<Item, SpawnEggItem>> allBulkZombieEggs() {
        return BULK_ZOMBIE_EGGS;
    }

    public static Map<Integer, DeferredHolder<Item, SpawnEggItem>> allBulkHuskEggs() {
        return BULK_HUSK_EGGS;
    }

    public static Map<Integer, DeferredHolder<Item, SpawnEggItem>> allBulkDrownedEggs() {
        return BULK_DROWNED_EGGS;
    }
}
