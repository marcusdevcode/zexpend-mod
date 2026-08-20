package net.marcusdevcode.zexpend.entities;

/**
 * Single source of truth for every custom zombie type: id, texture, ability and base stats.
 * Add a new zombie by adding a new line here (reusing an existing {@link ZombieAbility} or adding a new one).
 */
public enum ZombieVariants {
    DISCO("disco_zombie", "disco_zombie.png", ZombieAbility.DISCO_PULSE, 1.0F, 1.0F, 20.0D, 3.0D),
    NETHER("nether_zombie", "nether_zombie.png", ZombieAbility.IGNITE_ON_HIT, 1.0F, 1.0F, 22.0D, 3.0D),
    ALCHEMIST("zombie_alchemist", "zombie_alchemist.png", ZombieAbility.THROW_POTION, 1.0F, 1.0F, 20.0D, 3.0D),
    CHEF("zombie_chef", "zombie_chef.png", ZombieAbility.THROW_FIRE, 1.0F, 1.0F, 20.0D, 3.0D),
    CREEPER("zombie_creeper", "zombie_creeper.png", ZombieAbility.EXPLODE_ON_DEATH, 1.0F, 1.0F, 20.0D, 3.0D),
    CYBORG("zombie_cyborg", "zombie_cyborg.png", ZombieAbility.RANGED_LASER, 1.0F, 1.0F, 24.0D, 3.0D),
    DWARF("zombie_dwarf", "zombie_dwarf.png", ZombieAbility.SMALL_FAST, 0.6F, 1.4F, 16.0D, 3.0D),
    HEROBRINE("zombie_herobrine", "zombie_herobrine.png", ZombieAbility.TELEPORT, 1.0F, 1.0F, 26.0D, 4.0D),
    KING("zombie_king", "zombie_king.png", ZombieAbility.SUMMON_MINIONS, 1.15F, 1.0F, 40.0D, 5.0D),
    KNIGHT("zombie_knight", "zombie_knight.png", ZombieAbility.HEAVY_ARMOR, 1.0F, 1.0F, 30.0D, 4.0D),
    MINER("zombie_miner", "zombie_miner.png", ZombieAbility.BREAK_BLOCKS, 1.0F, 1.0F, 20.0D, 3.0D),
    NOTCH("zombie_notch", "zombie_notch.png", ZombieAbility.BOSS_STATS, 1.15F, 1.0F, 60.0D, 6.0D),
    PA("zombie_pa", "zombie_pa.png", ZombieAbility.NONE, 1.0F, 1.0F, 20.0D, 3.0D),
    PIRATE("zombie_pirate", "zombie_pirate.png", ZombieAbility.DUAL_WIELD, 1.0F, 1.0F, 20.0D, 3.5D);

    private final String id;
    private final String textureFile;
    private final ZombieAbility ability;
    private final float sizeScale;
    private final float speedMultiplier;
    private final double maxHealth;
    private final double attackDamage;

    ZombieVariants(String id, String textureFile, ZombieAbility ability, float sizeScale, float speedMultiplier, double maxHealth, double attackDamage) {
        this.id = id;
        this.textureFile = textureFile;
        this.ability = ability;
        this.sizeScale = sizeScale;
        this.speedMultiplier = speedMultiplier;
        this.maxHealth = maxHealth;
        this.attackDamage = attackDamage;
    }

    public String getId() {
        return id;
    }

    public String getTextureFile() {
        return textureFile;
    }

    public ZombieAbility getAbility() {
        return ability;
    }

    public float getSizeScale() {
        return sizeScale;
    }

    public float getSpeedMultiplier() {
        return speedMultiplier;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public double getAttackDamage() {
        return attackDamage;
    }
}
