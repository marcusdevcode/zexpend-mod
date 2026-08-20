package net.marcusdevcode.zexpend.datagen;

import net.marcusdevcode.zexpend.entities.ModBulkEntities;
import net.marcusdevcode.zexpend.entities.ModEntities;
import net.marcusdevcode.zexpend.entities.ZombieVariants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.stream.Stream;

public class ModEntityLootProvider extends EntityLootSubProvider {
    public ModEntityLootProvider(HolderLookup.Provider registries) {
        super(FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    public void generate() {
        for (ZombieVariants variant : ZombieVariants.values()) {
            add(ModEntities.get(variant), rottenFleshLoot());
        }
        for (int n : ModBulkEntities.allZombies().keySet()) {
            add(ModBulkEntities.zombie(n), rottenFleshLoot());
        }
        for (int n : ModBulkEntities.allHusks().keySet()) {
            add(ModBulkEntities.husk(n), rottenFleshLoot());
        }
        for (int n : ModBulkEntities.allDrowned().keySet()) {
            add(ModBulkEntities.drowned(n), rottenFleshLoot());
        }
    }

    private LootTable.Builder rottenFleshLoot() {
        return LootTable.lootTable().withPool(
                LootPool.lootPool().add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))));
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return Stream.of(
                ModEntities.all().values().stream().map(holder -> (EntityType<?>) holder.get()),
                ModBulkEntities.allZombies().values().stream().map(holder -> (EntityType<?>) holder.get()),
                ModBulkEntities.allHusks().values().stream().map(holder -> (EntityType<?>) holder.get()),
                ModBulkEntities.allDrowned().values().stream().map(holder -> (EntityType<?>) holder.get())
        ).flatMap(s -> s);
    }
}
