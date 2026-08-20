package net.marcusdevcode.zexpend.entities;

import net.marcusdevcode.zexpend.ZexpendMod;
import net.marcusdevcode.zexpend.entities.bulk.BulkDrownedEntity;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = ZexpendMod.MOD_ID)
public class ModSpawnPlacements {
    @SubscribeEvent
    static void onRegisterSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        for (ZombieVariants variant : ZombieVariants.values()) {
            event.register(ModEntities.get(variant), SpawnPlacementTypes.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules,
                    RegisterSpawnPlacementsEvent.Operation.AND);
        }
        for (int n : ModBulkEntities.allZombies().keySet()) {
            event.register(ModBulkEntities.zombie(n), SpawnPlacementTypes.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules,
                    RegisterSpawnPlacementsEvent.Operation.AND);
        }
        for (int n : ModBulkEntities.allHusks().keySet()) {
            event.register(ModBulkEntities.husk(n), SpawnPlacementTypes.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkSurfaceMonstersSpawnRules,
                    RegisterSpawnPlacementsEvent.Operation.AND);
        }
        for (int n : ModBulkEntities.allDrowned().keySet()) {
            event.register(ModBulkEntities.drowned(n), SpawnPlacementTypes.IN_WATER,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawnPlacements::checkBulkDrownedSpawnRules,
                    RegisterSpawnPlacementsEvent.Operation.AND);
        }
    }

    private static boolean checkBulkDrownedSpawnRules(
            net.minecraft.world.entity.EntityType<BulkDrownedEntity> type, net.minecraft.world.level.ServerLevelAccessor level,
            net.minecraft.world.entity.EntitySpawnReason reason, net.minecraft.core.BlockPos pos, net.minecraft.util.RandomSource random) {
        return level.getDifficulty() != Difficulty.PEACEFUL
                && level.getFluidState(pos).is(FluidTags.WATER)
                && level.getRawBrightness(pos, 0) == 0;
    }
}
