package net.marcusdevcode.zexpend.entities.bulk;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.zombie.Drowned;
import net.minecraft.world.level.Level;

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
}
