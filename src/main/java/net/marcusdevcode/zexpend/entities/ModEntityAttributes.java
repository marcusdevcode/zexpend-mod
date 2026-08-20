package net.marcusdevcode.zexpend.entities;

import net.marcusdevcode.zexpend.ZexpendMod;
import net.marcusdevcode.zexpend.entities.custom.CustomZombieEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = ZexpendMod.MOD_ID)
public class ModEntityAttributes {
    @SubscribeEvent
    static void onCreateAttributes(EntityAttributeCreationEvent event) {
        for (ZombieVariants variant : ZombieVariants.values()) {
            event.put(ModEntities.get(variant), CustomZombieEntity.createAttributes(variant).build());
        }
    }
}
