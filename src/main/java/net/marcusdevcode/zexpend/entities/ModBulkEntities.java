package net.marcusdevcode.zexpend.entities;

import net.marcusdevcode.zexpend.ZexpendMod;
import net.marcusdevcode.zexpend.entities.bulk.BulkDrownedEntity;
import net.marcusdevcode.zexpend.entities.bulk.BulkHuskEntity;
import net.marcusdevcode.zexpend.entities.bulk.BulkSkinManifest;
import net.marcusdevcode.zexpend.entities.bulk.BulkZombieEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Registers one {@link EntityType} per numbered skin file discovered under
 * {@code textures/entity/{zombie,husk,drowned}/} (see {@link BulkSkinManifest}) — each texture gets
 * its own individual entity, not a shared random-skin pool.
 */
public class ModBulkEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, ZexpendMod.MOD_ID);

    private static final Map<Integer, DeferredHolder<EntityType<?>, EntityType<BulkZombieEntity>>> ZOMBIES = new LinkedHashMap<>();
    private static final Map<Integer, DeferredHolder<EntityType<?>, EntityType<BulkHuskEntity>>> HUSKS = new LinkedHashMap<>();
    private static final Map<Integer, DeferredHolder<EntityType<?>, EntityType<BulkDrownedEntity>>> DROWNED = new LinkedHashMap<>();

    static {
        for (int n : BulkSkinManifest.ZOMBIE) {
            String id = "zombie_" + n;
            ZOMBIES.put(n, ENTITY_TYPES.register(id, () ->
                    EntityType.Builder.<BulkZombieEntity>of((type, level) -> new BulkZombieEntity(type, level, n), MobCategory.MONSTER)
                            .sized(0.6F, 1.95F)
                            .build(key(id))));
        }
        for (int n : BulkSkinManifest.HUSK) {
            String id = "husk_" + n;
            HUSKS.put(n, ENTITY_TYPES.register(id, () ->
                    EntityType.Builder.<BulkHuskEntity>of((type, level) -> new BulkHuskEntity(type, level, n), MobCategory.MONSTER)
                            .sized(0.6F, 1.95F)
                            .build(key(id))));
        }
        for (int n : BulkSkinManifest.DROWNED) {
            String id = "drowned_" + n;
            DROWNED.put(n, ENTITY_TYPES.register(id, () ->
                    EntityType.Builder.<BulkDrownedEntity>of((type, level) -> new BulkDrownedEntity(type, level, n), MobCategory.MONSTER)
                            .sized(0.6F, 1.95F)
                            .build(key(id))));
        }
    }

    private static ResourceKey<EntityType<?>> key(String path) {
        return ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(ZexpendMod.MOD_ID, path));
    }

    public static EntityType<BulkZombieEntity> zombie(int n) {
        return ZOMBIES.get(n).get();
    }

    public static EntityType<BulkHuskEntity> husk(int n) {
        return HUSKS.get(n).get();
    }

    public static EntityType<BulkDrownedEntity> drowned(int n) {
        return DROWNED.get(n).get();
    }

    public static Map<Integer, DeferredHolder<EntityType<?>, EntityType<BulkZombieEntity>>> allZombies() {
        return ZOMBIES;
    }

    public static Map<Integer, DeferredHolder<EntityType<?>, EntityType<BulkHuskEntity>>> allHusks() {
        return HUSKS;
    }

    public static Map<Integer, DeferredHolder<EntityType<?>, EntityType<BulkDrownedEntity>>> allDrowned() {
        return DROWNED;
    }
}
