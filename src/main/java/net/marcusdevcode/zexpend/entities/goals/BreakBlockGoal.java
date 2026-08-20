package net.marcusdevcode.zexpend.entities.goals;

import net.marcusdevcode.zexpend.entities.custom.CustomZombieEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class BreakBlockGoal extends Goal {
    private final CustomZombieEntity mob;
    private int breakCooldown;

    public BreakBlockGoal(CustomZombieEntity mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = mob.getTarget();
        return target != null
                && mob.level() instanceof ServerLevel serverLevel
                && serverLevel.getGameRules().get(GameRules.MOB_GRIEFING)
                && isBreakableBlockInTheWay(target);
    }

    private boolean isBreakableBlockInTheWay(LivingEntity target) {
        BlockPos pos = blockAheadTowards(target);
        BlockState state = mob.level().getBlockState(pos);
        float hardness = state.getDestroySpeed(mob.level(), pos);
        return !state.isAir() && hardness >= 0.0F && hardness <= 4.0F;
    }

    private BlockPos blockAheadTowards(LivingEntity target) {
        Vec3 dir = target.position().subtract(mob.position()).normalize();
        return BlockPos.containing(mob.getX() + dir.x, mob.getEyeY(), mob.getZ() + dir.z);
    }

    @Override
    public void tick() {
        if (breakCooldown-- <= 0) {
            LivingEntity target = mob.getTarget();
            if (target != null) {
                mob.level().destroyBlock(blockAheadTowards(target), false, mob);
            }
            breakCooldown = 20;
        }
    }
}
