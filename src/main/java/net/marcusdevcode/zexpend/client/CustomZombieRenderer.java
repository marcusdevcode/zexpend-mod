package net.marcusdevcode.zexpend.client;

import net.marcusdevcode.zexpend.ZexpendMod;
import net.marcusdevcode.zexpend.entities.custom.CustomZombieEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.zombie.Zombie;

public class CustomZombieRenderer extends ZombieRenderer {
    public CustomZombieRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public CustomZombieRenderState createRenderState() {
        return new CustomZombieRenderState();
    }

    @Override
    public void extractRenderState(Zombie entity, ZombieRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        if (entity instanceof CustomZombieEntity customZombie && reusedState instanceof CustomZombieRenderState customState) {
            customState.texture = Identifier.fromNamespaceAndPath(
                    ZexpendMod.MOD_ID, "textures/entity/" + customZombie.getVariant().getTextureFile());
        }
    }

    @Override
    public Identifier getTextureLocation(ZombieRenderState state) {
        if (state instanceof CustomZombieRenderState customState && customState.texture != null) {
            return customState.texture;
        }
        return super.getTextureLocation(state);
    }

    public static class CustomZombieRenderState extends ZombieRenderState {
        public Identifier texture;
    }
}
