package net.marcusdevcode.zexpend.entities.goals;

import net.marcusdevcode.zexpend.entities.custom.CustomZombieEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.zombie.Zombie;

import java.util.EnumSet;

public class SummonMinionsGoal extends Goal {
    private final CustomZombieEntity mob;

    public SummonMinionsGoal(CustomZombieEntity mob) {
        this.mob = mob;
        this.setFlags(EnumSet.noneOf(Goal.Flag.class));
    }

    @Override
    public boolean canUse() {
        return mob.getTarget() != null && mob.tickCount % 600 == 0;
    }

    @Override
    public void start() {
        if (!(mob.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        LivingEntity target = mob.getTarget();
        for (int i = 0; i < 2; i++) {
            Zombie minion = EntityType.ZOMBIE.create(serverLevel, EntitySpawnReason.MOB_SUMMONED);
            if (minion != null) {
                double offsetX = mob.getRandom().nextInt(3) - 1;
                double offsetZ = mob.getRandom().nextInt(3) - 1;
                minion.snapTo(mob.getX() + offsetX, mob.getY(), mob.getZ() + offsetZ, mob.getYRot(), 0.0F);
                minion.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(minion.blockPosition()), EntitySpawnReason.MOB_SUMMONED, null);
                if (target != null) {
                    minion.setTarget(target);
                }
                serverLevel.addFreshEntity(minion);
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }
}
