package net.marcusdevcode.zexpend.client;

import net.marcusdevcode.zexpend.ZexpendMod;
import net.marcusdevcode.zexpend.entities.bulk.BulkDrownedEntity;
import net.minecraft.client.renderer.entity.DrownedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.zombie.Drowned;

public class BulkDrownedRenderer extends DrownedRenderer {
    public BulkDrownedRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public BulkDrownedRenderState createRenderState() {
        return new BulkDrownedRenderState();
    }

    @Override
    public void extractRenderState(Drowned entity, ZombieRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        if (entity instanceof BulkDrownedEntity bulk && reusedState instanceof BulkDrownedRenderState bulkState) {
            bulkState.texture = Identifier.fromNamespaceAndPath(
                    ZexpendMod.MOD_ID, "textures/entity/drowned/" + bulk.getTextureIndex() + ".png");
        }
    }

    @Override
    public Identifier getTextureLocation(ZombieRenderState state) {
        if (state instanceof BulkDrownedRenderState bulkState && bulkState.texture != null) {
            return bulkState.texture;
        }
        return super.getTextureLocation(state);
    }

    public static class BulkDrownedRenderState extends ZombieRenderState {
        public Identifier texture;
    }
}
