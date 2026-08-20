package net.marcusdevcode.zexpend.client;

import net.marcusdevcode.zexpend.ZexpendMod;
import net.marcusdevcode.zexpend.entities.bulk.BulkHuskEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HuskRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.zombie.Zombie;

public class BulkHuskRenderer extends HuskRenderer {
    public BulkHuskRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public BulkHuskRenderState createRenderState() {
        return new BulkHuskRenderState();
    }

    @Override
    public void extractRenderState(Zombie entity, ZombieRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        if (entity instanceof BulkHuskEntity bulk && reusedState instanceof BulkHuskRenderState bulkState) {
            bulkState.texture = Identifier.fromNamespaceAndPath(
                    ZexpendMod.MOD_ID, "textures/entity/husk/" + bulk.getTextureIndex() + ".png");
        }
    }

    @Override
    public Identifier getTextureLocation(ZombieRenderState state) {
        if (state instanceof BulkHuskRenderState bulkState && bulkState.texture != null) {
            return bulkState.texture;
        }
        return super.getTextureLocation(state);
    }

    public static class BulkHuskRenderState extends ZombieRenderState {
        public Identifier texture;
    }
}
