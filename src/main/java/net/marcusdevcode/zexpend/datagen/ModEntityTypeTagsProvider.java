package net.marcusdevcode.zexpend.datagen;

import net.marcusdevcode.zexpend.ZexpendMod;
import net.marcusdevcode.zexpend.entities.ModEntities;
import net.marcusdevcode.zexpend.entities.ZombieVariants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public ModEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ZexpendMod.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        EntityType<?>[] zombieTypes = java.util.Arrays.stream(ZombieVariants.values())
                .map(ModEntities::get)
                .toArray(EntityType<?>[]::new);
        this.tag(EntityTypeTags.BURN_IN_DAYLIGHT).add(zombieTypes);
    }
}
