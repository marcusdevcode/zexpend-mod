package net.marcusdevcode.zexpend;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // a list of strings that are treated as resource locations for items
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
            .comment("A list of items to log on common setup.")
            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), () -> "", Config::validateItemName);

    // Natural spawn chance dials, each 0-100. The effective chance for any given zombie/husk/drowned
    // is generalSpawnChancePercent% AND its own type's percent, so either dial alone can throttle everything.
    public static final ModConfigSpec.IntValue GENERAL_SPAWN_CHANCE_PERCENT = BUILDER
            .comment("Overall percent chance (0-100) applied to every mod zombie/husk/drowned natural spawn attempt, on top of its type-specific percent below.")
            .defineInRange("generalSpawnChancePercent", 100, 0, 100);

    public static final ModConfigSpec.IntValue ZOMBIE_SPAWN_CHANCE_PERCENT = BUILDER
            .comment("Percent chance (0-100) for zombie-type natural spawns (the named zombies plus all bulk zombie skins).")
            .defineInRange("zombieSpawnChancePercent", 100, 0, 100);

    public static final ModConfigSpec.IntValue HUSK_SPAWN_CHANCE_PERCENT = BUILDER
            .comment("Percent chance (0-100) for husk-type natural spawns (all bulk husk skins).")
            .defineInRange("huskSpawnChancePercent", 100, 0, 100);

    public static final ModConfigSpec.IntValue DROWNED_SPAWN_CHANCE_PERCENT = BUILDER
            .comment("Percent chance (0-100) for drowned-type natural spawns (all bulk drowned skins).")
            .defineInRange("drownedSpawnChancePercent", 100, 0, 100);

    public static final ModConfigSpec.IntValue SPAWN_PACK_SIZE = BUILDER
            .comment("Total number of mobs (this one plus companions) that appear together whenever one of the mod's zombies/husks/drowned spawns naturally. 1 = no companions.")
            .defineInRange("spawnPackSize", 2, 1, 64);

    public static final ModConfigSpec.BooleanValue BURN_IN_DAYLIGHT = BUILDER
            .comment("Whether the mod's zombies catch fire and take damage in direct sunlight, like vanilla zombies.")
            .define("burnZombiesInDaylight", true);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(Identifier.parse(itemName));
    }
}
