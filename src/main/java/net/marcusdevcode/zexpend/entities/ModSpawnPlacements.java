package net.marcusdevcode.zexpend.entities;

import net.marcusdevcode.zexpend.Config;
import net.marcusdevcode.zexpend.ZexpendMod;
import net.marcusdevcode.zexpend.entities.bulk.BulkDrownedEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import java.util.function.IntSupplier;

@EventBusSubscriber(modid = ZexpendMod.MOD_ID)
public class ModSpawnPlacements {
    @SubscribeEvent
    static void onRegisterSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        for (ZombieVariants variant : ZombieVariants.values()) {
            event.register(ModEntities.get(variant), SpawnPlacementTypes.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    withChance(Config.ZOMBIE_SPAWN_CHANCE_PERCENT::getAsInt, Monster::checkMonsterSpawnRules),
                    RegisterSpawnPlacementsEvent.Operation.AND);
        }
        for (int n : ModBulkEntities.allZombies().keySet()) {
            event.register(ModBulkEntities.zombie(n), SpawnPlacementTypes.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    withChance(Config.ZOMBIE_SPAWN_CHANCE_PERCENT::getAsInt, Monster::checkMonsterSpawnRules),
                    RegisterSpawnPlacementsEvent.Operation.AND);
        }
        for (int n : ModBulkEntities.allHusks().keySet()) {
            event.register(ModBulkEntities.husk(n), SpawnPlacementTypes.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    withChance(Config.HUSK_SPAWN_CHANCE_PERCENT::getAsInt, Monster::checkSurfaceMonstersSpawnRules),
                    RegisterSpawnPlacementsEvent.Operation.AND);
        }
        for (int n : ModBulkEntities.allDrowned().keySet()) {
            event.register(ModBulkEntities.drowned(n), SpawnPlacementTypes.IN_WATER,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    withChance(Config.DROWNED_SPAWN_CHANCE_PERCENT::getAsInt, ModSpawnPlacements::checkBulkDrownedSpawnRules),
                    RegisterSpawnPlacementsEvent.Operation.AND);
        }
    }

    /**
     * Wraps a base spawn predicate with two independent percent-chance rolls: the general dial
     * ({@link Config#GENERAL_SPAWN_CHANCE_PERCENT}, applies to every mod entity) and the given
     * type-specific dial (zombie/husk/drowned). Both must pass, so either alone can throttle spawns.
     */
    private static <T extends Entity> SpawnPlacements.SpawnPredicate<T> withChance(
            IntSupplier typeChancePercent, SpawnPlacements.SpawnPredicate<T> base) {
        return (entityType, level, spawnReason, pos, random) ->
                random.nextInt(100) < Config.GENERAL_SPAWN_CHANCE_PERCENT.getAsInt()
                        && random.nextInt(100) < typeChancePercent.getAsInt()
                        && base.test(entityType, level, spawnReason, pos, random);
    }

    private static boolean checkBulkDrownedSpawnRules(
            EntityType<BulkDrownedEntity> type, ServerLevelAccessor level,
            EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return level.getDifficulty() != Difficulty.PEACEFUL
                && level.getFluidState(pos).is(FluidTags.WATER)
                && level.getRawBrightness(pos, 0) == 0;
    }
}
