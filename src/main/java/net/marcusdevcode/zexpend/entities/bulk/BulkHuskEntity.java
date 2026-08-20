package net.marcusdevcode.zexpend.entities.bulk;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.zombie.Husk;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;

public class BulkHuskEntity extends Husk {
    private final int textureIndex;

    public BulkHuskEntity(EntityType<? extends Husk> type, Level level, int textureIndex) {
        super(type, level);
        this.textureIndex = textureIndex;
    }

    public int getTextureIndex() {
        return textureIndex;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes();
    }
}
