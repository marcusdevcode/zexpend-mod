package net.marcusdevcode.zexpend.entities;

import net.marcusdevcode.zexpend.ZexpendMod;
import net.marcusdevcode.zexpend.entities.bulk.BulkDrownedEntity;
import net.marcusdevcode.zexpend.entities.bulk.BulkHuskEntity;
import net.marcusdevcode.zexpend.entities.bulk.BulkZombieEntity;
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
        for (int n : ModBulkEntities.allZombies().keySet()) {
            event.put(ModBulkEntities.zombie(n), BulkZombieEntity.createAttributes().build());
        }
        for (int n : ModBulkEntities.allHusks().keySet()) {
            event.put(ModBulkEntities.husk(n), BulkHuskEntity.createAttributes().build());
        }
        for (int n : ModBulkEntities.allDrowned().keySet()) {
            event.put(ModBulkEntities.drowned(n), BulkDrownedEntity.createAttributes().build());
        }
    }
}
