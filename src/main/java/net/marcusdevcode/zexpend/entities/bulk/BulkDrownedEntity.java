package net.marcusdevcode.zexpend.entities.bulk;

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

    public static AttributeSupplier.Builder createAttributes() {
        return Drowned.createAttributes();
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
