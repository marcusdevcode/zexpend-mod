package net.marcusdevcode.zexpend.entities.bulk;

import net.marcusdevcode.zexpend.entities.DaylightBurnHelper;
import net.marcusdevcode.zexpend.entities.PackSpawnHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.zombie.Drowned;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class BulkDrownedEntity extends Drowned {
    private final int textureIndex;

    public BulkDrownedEntity(EntityType<? extends Drowned> type, Level level, int textureIndex) {
        super(type, level);
        this.textureIndex = textureIndex;
    }

    public int getTextureIndex() {
        return textureIndex;
    }

    @Override
    public boolean canBreatheUnderwater() {
        // Vanilla Drowned only survives underwater because minecraft:drowned is in the
        // minecraft:can_breathe_under_water entity type tag; this custom entity type
        // (zexpendmod:drowned_N) isn't in that tag, so without this override it drowns for real.
        return true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Drowned.createAttributes();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        // Unlike vanilla Drowned (never burns), this mod's drowned burn in daylight on land like
        // its zombies/husks do, gated by the same config toggle; isSunBurnTick()'s wet check
        // already skips burning while submerged, so no separate water guard is needed here.
        DaylightBurnHelper.tick(this);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                         EntitySpawnReason spawnReason, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnReason, spawnGroupData);
        PackSpawnHelper.spawnCompanions(this, level, spawnReason, PackSpawnHelper.Category.DROWNED);
        return data;
    }
}
