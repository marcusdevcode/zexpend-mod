package net.marcusdevcode.zexpend.entities;

import net.marcusdevcode.zexpend.Config;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.ServerLevelAccessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Runtime-configurable natural-spawn pack size (see {@link Config#SPAWN_PACK_SIZE}), independent of
 * the static min/max count baked into the datagen'd biome spawn modifiers. Called from
 * {@code finalizeSpawn} on every mod zombie/husk/drowned entity.
 * <p>
 * Each companion is independently rolled from the full pool for its {@link Category} (all named +
 * bulk zombie types for ZOMBIE, all bulk husk types for HUSK, all bulk drowned types for DROWNED) so
 * a pack looks like a real mixed group instead of clones of the entity that triggered it.
 * <p>
 * Companions are spawned with {@link EntitySpawnReason#REINFORCEMENT} rather than
 * {@link EntitySpawnReason#NATURAL}, which is what stops this from recursing into itself — the
 * {@code reason != NATURAL} guard below immediately no-ops for companions' own finalizeSpawn calls.
 */
public final class PackSpawnHelper {
    public enum Category {
        ZOMBIE, HUSK, DROWNED
    }

    private static List<EntityType<? extends Mob>> zombiePool;
    private static List<EntityType<? extends Mob>> huskPool;
    private static List<EntityType<? extends Mob>> drownedPool;

    private PackSpawnHelper() {
    }

    public static void spawnCompanions(Mob mob, ServerLevelAccessor level, EntitySpawnReason reason, Category category) {
        if (reason != EntitySpawnReason.NATURAL || !(level instanceof ServerLevel serverLevel)) {
            return;
        }
        List<EntityType<? extends Mob>> pool = pool(category);
        if (pool.isEmpty()) {
            return;
        }
        int extras = Config.SPAWN_PACK_SIZE.getAsInt() - 1;
        for (int i = 0; i < extras; i++) {
            EntityType<? extends Mob> type = pool.get(mob.getRandom().nextInt(pool.size()));
            Entity companion = type.create(serverLevel, EntitySpawnReason.REINFORCEMENT);
            if (companion instanceof Mob companionMob) {
                double x = mob.getX() + (mob.getRandom().nextDouble() - 0.5D) * 4.0D;
                double z = mob.getZ() + (mob.getRandom().nextDouble() - 0.5D) * 4.0D;
                companionMob.snapTo(x, mob.getY(), z, mob.getYRot(), 0.0F);
                companionMob.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(companionMob.blockPosition()),
                        EntitySpawnReason.REINFORCEMENT, null);
                serverLevel.addFreshEntity(companionMob);
            }
        }
    }

    private static List<EntityType<? extends Mob>> pool(Category category) {
        return switch (category) {
            case ZOMBIE -> zombiePool != null ? zombiePool : (zombiePool = buildZombiePool());
            case HUSK -> huskPool != null ? huskPool : (huskPool = buildHuskPool());
            case DROWNED -> drownedPool != null ? drownedPool : (drownedPool = buildDrownedPool());
        };
    }

    private static List<EntityType<? extends Mob>> buildZombiePool() {
        List<EntityType<? extends Mob>> list = new ArrayList<>();
        ModEntities.all().values().forEach(holder -> list.add(holder.get()));
        ModBulkEntities.allZombies().values().forEach(holder -> list.add(holder.get()));
        return list;
    }

    private static List<EntityType<? extends Mob>> buildHuskPool() {
        List<EntityType<? extends Mob>> list = new ArrayList<>();
        ModBulkEntities.allHusks().values().forEach(holder -> list.add(holder.get()));
        return list;
    }

    private static List<EntityType<? extends Mob>> buildDrownedPool() {
        List<EntityType<? extends Mob>> list = new ArrayList<>();
        ModBulkEntities.allDrowned().values().forEach(holder -> list.add(holder.get()));
        return list;
    }
}
