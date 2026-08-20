package net.marcusdevcode.zexpend.entities;

import net.marcusdevcode.zexpend.ZexpendMod;
import net.marcusdevcode.zexpend.entities.custom.CustomZombieEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.Map;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, ZexpendMod.MOD_ID);

    private static final Map<ZombieVariants, DeferredHolder<EntityType<?>, EntityType<CustomZombieEntity>>> ZOMBIE_TYPES =
            new EnumMap<>(ZombieVariants.class);

    static {
        for (ZombieVariants variant : ZombieVariants.values()) {
            float width = 0.6F * variant.getSizeScale();
            float height = 1.95F * variant.getSizeScale();
            ZOMBIE_TYPES.put(variant, ENTITY_TYPES.register(variant.getId(), () ->
                    EntityType.Builder.<CustomZombieEntity>of((type, level) -> new CustomZombieEntity(type, level, variant), MobCategory.MONSTER)
                            .sized(width, height)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(ZexpendMod.MOD_ID, variant.getId())))));
        }
    }

    public static EntityType<CustomZombieEntity> get(ZombieVariants variant) {
        return ZOMBIE_TYPES.get(variant).get();
    }

    public static Map<ZombieVariants, DeferredHolder<EntityType<?>, EntityType<CustomZombieEntity>>> all() {
        return ZOMBIE_TYPES;
    }
}
