package net.marcusdevcode.zexpend.entities.goals;

import net.marcusdevcode.zexpend.entities.custom.CustomZombieEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;
import java.util.List;

public class DiscoPulseGoal extends Goal {
    private final CustomZombieEntity mob;

    public DiscoPulseGoal(CustomZombieEntity mob) {
        this.mob = mob;
        this.setFlags(EnumSet.noneOf(Goal.Flag.class));
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return true;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (mob.tickCount % 60 != 0 || !(mob.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        serverLevel.sendParticles(ParticleTypes.WITCH, mob.getX(), mob.getY() + 1.0D, mob.getZ(), 20, 0.5D, 0.5D, 0.5D, 0.05D);
        List<LivingEntity> nearby = serverLevel.getEntitiesOfClass(LivingEntity.class,
                AABB.ofSize(mob.position(), 4.0D, 4.0D, 4.0D), entity -> entity != mob && entity.isAlive());
        for (LivingEntity entity : nearby) {
            double dx = entity.getX() - mob.getX();
            double dz = entity.getZ() - mob.getZ();
            entity.push(dx * 0.3D, 0.3D, dz * 0.3D);
        }
    }
}
