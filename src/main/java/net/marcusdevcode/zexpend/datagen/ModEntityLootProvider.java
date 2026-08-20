package net.marcusdevcode.zexpend.datagen;

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
            add(ModEntities.get(variant), LootTable.lootTable().withPool(
                    LootPool.lootPool().add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))))));
        }
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return ModEntities.all().values().stream().map(holder -> (EntityType<?>) holder.get());
    }
}
