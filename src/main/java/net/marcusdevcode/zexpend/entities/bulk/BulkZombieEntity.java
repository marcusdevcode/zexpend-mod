package net.marcusdevcode.zexpend.entities.bulk;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;

public class BulkZombieEntity extends Zombie {
    private final int textureIndex;

    public BulkZombieEntity(EntityType<? extends Zombie> type, Level level, int textureIndex) {
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
