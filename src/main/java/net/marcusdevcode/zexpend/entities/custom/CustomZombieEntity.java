package net.marcusdevcode.zexpend.entities.custom;

import net.marcusdevcode.zexpend.entities.DaylightBurnHelper;
import net.marcusdevcode.zexpend.entities.PackSpawnHelper;
import net.marcusdevcode.zexpend.entities.ZombieAbility;
import net.marcusdevcode.zexpend.entities.ZombieVariants;
import net.marcusdevcode.zexpend.entities.goals.BreakBlockGoal;
import net.marcusdevcode.zexpend.entities.goals.DiscoPulseGoal;
import net.marcusdevcode.zexpend.entities.goals.SummonMinionsGoal;
import net.marcusdevcode.zexpend.entities.goals.TeleportTowardTargetGoal;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.projectile.hurtingprojectile.SmallFireball;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownSplashPotion;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class CustomZombieEntity extends Zombie implements RangedAttackMob {
    private static final Set<ZombieVariants> MUSIC_LOVERS = EnumSet.of(ZombieVariants.DISCO, ZombieVariants.COOL_GIRL);
    private static final List<net.minecraft.core.Holder<net.minecraft.sounds.SoundEvent>> RANDOM_SONGS = List.of(
            SoundEvents.MUSIC_DISC_CAT, SoundEvents.MUSIC_DISC_BLOCKS, SoundEvents.MUSIC_DISC_CHIRP,
            SoundEvents.MUSIC_DISC_MELLOHI, SoundEvents.MUSIC_DISC_STAL, SoundEvents.MUSIC_DISC_STRAD,
            SoundEvents.MUSIC_DISC_WARD, SoundEvents.MUSIC_DISC_MALL, SoundEvents.MUSIC_DISC_FAR,
            SoundEvents.MUSIC_DISC_PIGSTEP, SoundEvents.MUSIC_DISC_OTHERSIDE);
    private static final int MUSIC_INTERVAL_TICKS = 20 * 15;

    private final ZombieVariants variant;
    private int musicCooldown;

    public CustomZombieEntity(EntityType<? extends Zombie> type, Level level, ZombieVariants variant) {
        super(type, level);
        this.variant = variant;
        addAbilityGoals();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        DaylightBurnHelper.tick(this);
        if (this.level() instanceof ServerLevel && MUSIC_LOVERS.contains(variant)
                && this.onGround() && this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-4D) {
            if (musicCooldown-- <= 0) {
                var song = RANDOM_SONGS.get(this.random.nextInt(RANDOM_SONGS.size()));
                this.level().playSound(null, this.blockPosition(), song.value(), SoundSource.HOSTILE, 1.0F, 1.0F);
                musicCooldown = MUSIC_INTERVAL_TICKS;
            }
        }
    }

    public ZombieVariants getVariant() {
        return variant;
    }

    public static AttributeSupplier.Builder createAttributes(ZombieVariants variant) {
        return Zombie.createAttributes()
                .add(Attributes.MAX_HEALTH, variant.getMaxHealth())
                .add(Attributes.ATTACK_DAMAGE, variant.getAttackDamage())
                .add(Attributes.MOVEMENT_SPEED, 0.23D * variant.getSpeedMultiplier());
    }

    /**
     * Called from the constructor (not {@link #registerGoals()}) because registerGoals() runs during the
     * Zombie superclass constructor, before {@code variant} has been assigned on this subclass.
     */
    private void addAbilityGoals() {
        switch (variant.getAbility()) {
            case TELEPORT -> this.goalSelector.addGoal(3, new TeleportTowardTargetGoal(this));
            case SUMMON_MINIONS -> this.goalSelector.addGoal(3, new SummonMinionsGoal(this));
            case BREAK_BLOCKS -> this.goalSelector.addGoal(2, new BreakBlockGoal(this));
            case DISCO_PULSE -> this.goalSelector.addGoal(3, new DiscoPulseGoal(this));
            case RANGED_LASER, THROW_POTION, THROW_FIRE ->
                    this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0D, 40, 16.0F));
            default -> {
            }
        }
    }

    public boolean teleportTowards(LivingEntity target) {
        double x = target.getX() + (this.random.nextDouble() - 0.5D) * 4.0D;
        double y = target.getY() + this.random.nextInt(3) - 1;
        double z = target.getZ() + (this.random.nextDouble() - 0.5D) * 4.0D;
        return this.randomTeleport(x, y, z, true);
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, net.minecraft.world.entity.Entity target) {
        boolean result = super.doHurtTarget(level, target);
        if (result && variant.getAbility() == ZombieAbility.IGNITE_ON_HIT && target instanceof LivingEntity living) {
            living.igniteForSeconds(5);
        }
        return result;
    }

    @Override
    public boolean fireImmune() {
        return variant.getAbility() == ZombieAbility.IGNITE_ON_HIT || super.fireImmune();
    }

    @Override
    public void die(DamageSource cause) {
        if (variant.getAbility() == ZombieAbility.EXPLODE_ON_DEATH
                && this.level() instanceof ServerLevel serverLevel
                && !this.isRemoved()) {
            serverLevel.explode(this, this.getX(), this.getY(), this.getZ(), 2.0F, Level.ExplosionInteraction.MOB);
        }
        super.die(cause);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                         EntitySpawnReason spawnReason, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnReason, spawnGroupData);
        switch (variant.getAbility()) {
            case HEAVY_ARMOR -> {
                this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
                this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
                this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
                this.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));
            }
            case DUAL_WIELD -> this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.IRON_SWORD));
            default -> {
            }
        }
        PackSpawnHelper.spawnCompanions(this, level, spawnReason, PackSpawnHelper.Category.ZOMBIE);
        return data;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        switch (variant.getAbility()) {
            case RANGED_LASER -> shootLaser(target);
            case THROW_POTION -> throwPotion(target);
            case THROW_FIRE -> throwFireball(target);
            default -> {
            }
        }
    }

    private void shootLaser(LivingEntity target) {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        Vec3 from = this.getEyePosition();
        Vec3 to = target.getEyePosition();
        Vec3 step = to.subtract(from).normalize();
        for (double d = 0.0D; d < from.distanceTo(to); d += 0.5D) {
            Vec3 point = from.add(step.scale(d));
            serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK, point.x, point.y, point.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        target.hurtServer(serverLevel, this.damageSources().mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
    }

    private void throwPotion(LivingEntity target) {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        ItemStack potionStack = new ItemStack(Items.SPLASH_POTION);
        potionStack.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.HARMING));
        ThrownSplashPotion potion = new ThrownSplashPotion(this.level(), this, potionStack);
        double dx = target.getX() - this.getX();
        double dy = target.getY(0.5D) - potion.getY();
        double dz = target.getZ() - this.getZ();
        double dist = Math.sqrt(dx * dx + dz * dz);
        potion.shoot(dx, dy + dist * 0.2D, dz, 0.75F, 8.0F);
        serverLevel.addFreshEntity(potion);
    }

    private void throwFireball(LivingEntity target) {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        double dx = target.getX() - this.getX();
        double dy = target.getY(0.5D) - this.getY(0.5D);
        double dz = target.getZ() - this.getZ();
        SmallFireball fireball = new SmallFireball(this.level(), this, new Vec3(dx, dy, dz).normalize());
        fireball.setPos(this.getX(), this.getEyeY() - 0.1D, this.getZ());
        serverLevel.addFreshEntity(fireball);
    }
}
