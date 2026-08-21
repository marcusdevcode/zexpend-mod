Zexpend Mod
=======

About
============
Zexpend Mod is a NeoForge mod for Minecraft 1.21.11 that expands the game's undead with a large
roster of new zombie, husk, and drowned variants.

- **Named zombie variants** (`ZombieVariants`): a set of hand-tuned zombies (Disco Zombie, Nether
  Zombie, Zombie Alchemist, Zombie Chef, Zombie Creeper, Cyborg, Dwarf, Herobrine, Zombie King,
  Zombie Knight, Miner, Notch, Pirate, and more), each with its own texture, size, speed, health,
  attack damage, and a special ability (see `ZombieAbility`: ignite on hit, throw potions/fire,
  explode on death, ranged laser, teleport, summon minions, heavy armor, break blocks, boss stats,
  dual wield, disco pulse, etc.).
- **Bulk skin variants**: a large pool of extra zombie, husk, and drowned entity types, one per
  texture discovered under `textures/entity/{zombie,husk,drowned}/` at build/data-gen time
  (`BulkSkinManifest`, `ModBulkEntities`). These give visual variety without unique abilities.
- **Natural spawning**: all of the above can spawn naturally like vanilla zombies/husks/drowned,
  gated by the spawn-chance and pack-size config options described below (`ModSpawnPlacements`,
  `PackSpawnHelper`).
- **Daylight burning**: the mod's zombies can catch fire in sunlight like vanilla zombies, unless
  disabled in config (`DaylightBurnHelper`).

Configuration
============
The mod registers a common config file (via NeoForge's `ModConfigSpec`) at
`config/zexpendmod-common.toml`, generated on first launch. Editing it and restarting (or using
a config-reload mod) changes the mod's runtime behavior without recompiling. The available options
are:

| Option | Type | Default | Range | Effect |
|---|---|---|---|---|
| `items` | list of strings | `["minecraft:iron_ingot"]` | any valid item resource locations | Items logged to the console/log during common setup. Diagnostic only — does not change gameplay. |
| `generalSpawnChancePercent` | int | `100` | 0-100 | Overall percent chance applied to **every** mod zombie/husk/drowned natural spawn attempt, on top of the type-specific percent below. Both rolls must pass, so either dial alone can throttle or disable all mod spawns (set to `0` to stop natural spawning entirely). |
| `zombieSpawnChancePercent` | int | `100` | 0-100 | Percent chance specifically for zombie-type natural spawns (named zombies + bulk zombie skins). |
| `huskSpawnChancePercent` | int | `100` | 0-100 | Percent chance specifically for husk-type natural spawns (bulk husk skins). |
| `drownedSpawnChancePercent` | int | `100` | 0-100 | Percent chance specifically for drowned-type natural spawns (bulk drowned skins). |
| `spawnPackSize` | int | `2` | 1-64 | Total number of mobs (the triggering mob plus companions) that spawn together whenever one of the mod's zombies/husks/drowned spawns naturally. Companions are drawn from the full pool for that category, so packs look mixed rather than clones. Set to `1` to disable companion spawning. |
| `burnZombiesInDaylight` | boolean | `true` | — | Whether the mod's zombies catch fire and take damage in direct sunlight, like vanilla zombies. Set to `false` to let them stand in daylight safely. |

Note that `generalSpawnChancePercent` and the type-specific percent (e.g. `zombieSpawnChancePercent`)
are combined with a logical AND (both dice must succeed), not multiplied as displayed — e.g. general
50% and zombie 50% does not simply mean a 25% chance is guaranteed each attempt, but each is rolled
independently on every spawn attempt.

Installation information
=======

This template repository can be directly cloned to get you started with a new
mod. Simply create a new repository cloned from this one, by following the
instructions provided by [GitHub](https://docs.github.com/en/repositories/creating-and-managing-repositories/creating-a-repository-from-a-template).

Once you have your clone, simply open the repository in the IDE of your choice. The usual recommendation for an IDE is either IntelliJ IDEA or Eclipse.

If at any point you are missing libraries in your IDE, or you've run into problems you can
run `gradlew --refresh-dependencies` to refresh the local cache. `gradlew clean` to reset everything 
{this does not affect your code} and then start the process again.

Mapping Names:
============
By default, the MDK is configured to use the official mapping names from Mojang for methods and fields 
in the Minecraft codebase. These names are covered by a specific license. All modders should be aware of this
license. For the latest license text, refer to the mapping file itself, or the reference copy here:
https://github.com/NeoForged/NeoForm/blob/main/Mojang.md

Additional Resources: 
==========
Community Documentation: https://docs.neoforged.net/  
NeoForged Discord: https://discord.neoforged.net/
