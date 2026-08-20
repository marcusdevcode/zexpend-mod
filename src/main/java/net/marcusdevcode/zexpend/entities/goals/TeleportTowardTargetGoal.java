package net.marcusdevcode.zexpend.entities.goals;

import net.marcusdevcode.zexpend.entities.custom.CustomZombieEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class TeleportTowardTargetGoal extends Goal {
    private final CustomZombieEntity mob;

    public TeleportTowardTargetGoal(CustomZombieEntity mob) {
        this.mob = mob;
        this.setFlags(EnumSet.noneOf(Goal.Flag.class));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = mob.getTarget();
        if (target == null) {
            return false;
        }
        double distSqr = mob.distanceToSqr(target);
        return distSqr > 16.0D && distSqr < 256.0D && mob.tickCount % 60 == 0;
    }

    @Override
    public void start() {
        LivingEntity target = mob.getTarget();
        if (target != null) {
            mob.teleportTowards(target);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }
}
