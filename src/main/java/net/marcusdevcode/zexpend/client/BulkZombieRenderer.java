package net.marcusdevcode.zexpend.client;

import net.marcusdevcode.zexpend.ZexpendMod;
import net.marcusdevcode.zexpend.entities.bulk.BulkZombieEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.zombie.Zombie;

public class BulkZombieRenderer extends ZombieRenderer {
    public BulkZombieRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public BulkZombieRenderState createRenderState() {
        return new BulkZombieRenderState();
    }

    @Override
    public void extractRenderState(Zombie entity, ZombieRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        if (entity instanceof BulkZombieEntity bulk && reusedState instanceof BulkZombieRenderState bulkState) {
            bulkState.texture = Identifier.fromNamespaceAndPath(
                    ZexpendMod.MOD_ID, "textures/entity/zombie/" + bulk.getTextureIndex() + ".png");
        }
    }

    @Override
    public Identifier getTextureLocation(ZombieRenderState state) {
        if (state instanceof BulkZombieRenderState bulkState && bulkState.texture != null) {
            return bulkState.texture;
        }
        return super.getTextureLocation(state);
    }

    public static class BulkZombieRenderState extends ZombieRenderState {
        public Identifier texture;
    }
}
