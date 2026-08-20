package net.marcusdevcode.zexpend.datagen;

import net.marcusdevcode.zexpend.ZexpendMod;
import net.marcusdevcode.zexpend.entities.ModBulkEntities;
import net.marcusdevcode.zexpend.entities.ModEntities;
import net.marcusdevcode.zexpend.entities.ZombieVariants;
import net.marcusdevcode.zexpend.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, ZexpendMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("zexpendmod.configuration.title", "Zexpend Mod Configs");
        add("zexpendmod.configuration.section.zexpendmod.common.toml", "Zexpend Mod Configs");
        add("zexpendmod.configuration.section.zexpendmod.common.toml.title", "Zexpend Mod Configs");
        add("zexpendmod.configuration.items", "Item List");
        add("zexpendmod.configuration.logDirtBlock", "Log Dirt Block");
        add("zexpendmod.configuration.magicNumberIntroduction", "Magic Number Text");
        add("zexpendmod.configuration.magicNumber", "Magic Number");
        add("itemGroup.zexpendmod.zombies", "Zexpend: Zombies");

        for (ZombieVariants variant : ZombieVariants.values()) {
            String displayName = toDisplayName(variant);
            addEntityType(ModEntities.all().get(variant), displayName);
            add(ModItems.spawnEgg(variant).get(), displayName + " Spawn Egg");
        }
        for (int n : ModBulkEntities.allZombies().keySet()) {
            String name = "Zombie #" + n;
            addEntityType(ModBulkEntities.allZombies().get(n), name);
            add(ModItems.allBulkZombieEggs().get(n).get(), name + " Spawn Egg");
        }
        for (int n : ModBulkEntities.allHusks().keySet()) {
            String name = "Husk #" + n;
            addEntityType(ModBulkEntities.allHusks().get(n), name);
            add(ModItems.allBulkHuskEggs().get(n).get(), name + " Spawn Egg");
        }
        for (int n : ModBulkEntities.allDrowned().keySet()) {
            String name = "Drowned #" + n;
            addEntityType(ModBulkEntities.allDrowned().get(n), name);
            add(ModItems.allBulkDrownedEggs().get(n).get(), name + " Spawn Egg");
        }
    }

    private String toDisplayName(ZombieVariants variant) {
        String stripped = variant.getId().replace("zombie_", "").replace("_zombie", "");
        StringBuilder sb = new StringBuilder();
        for (String part : stripped.split("_")) {
            if (!part.isEmpty()) {
                sb.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1)).append(' ');
            }
        }
        return sb.toString().trim() + " Zombie";
    }
}
