package net.marcusdevcode.zexpend.item;

import net.marcusdevcode.zexpend.ZexpendMod;
import net.marcusdevcode.zexpend.entities.ModEntities;
import net.marcusdevcode.zexpend.entities.ZombieVariants;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.Map;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ZexpendMod.MOD_ID);

    private static final Map<ZombieVariants, DeferredHolder<Item, SpawnEggItem>> SPAWN_EGGS =
            new EnumMap<>(ZombieVariants.class);

    static {
        for (ZombieVariants variant : ZombieVariants.values()) {
            SPAWN_EGGS.put(variant, ITEMS.registerItem(variant.getId() + "_spawn_egg", properties ->
                    new SpawnEggItem(properties.spawnEgg(ModEntities.get(variant)))));
        }
    }

    public static DeferredHolder<Item, SpawnEggItem> spawnEgg(ZombieVariants variant) {
        return SPAWN_EGGS.get(variant);
    }

    public static Map<ZombieVariants, DeferredHolder<Item, SpawnEggItem>> allSpawnEggs() {
        return SPAWN_EGGS;
    }
}
