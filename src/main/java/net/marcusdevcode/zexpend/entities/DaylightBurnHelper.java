package net.marcusdevcode.zexpend.entities;

import net.marcusdevcode.zexpend.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * Config-gated re-implementation of vanilla {@code Mob#burnUndead()}/{@code isSunBurnTick()} (both
 * private, so not overridable). Shared by {@link net.marcusdevcode.zexpend.entities.custom.CustomZombieEntity}
 * and {@link net.marcusdevcode.zexpend.entities.bulk.BulkZombieEntity} so daylight burning can be toggled
 * at runtime via {@link Config#BURN_IN_DAYLIGHT} instead of the old static/baked entity-type tag.
 */
public final class DaylightBurnHelper {
    private DaylightBurnHelper() {
    }

    public static void tick(Mob mob) {
        if (!Config.BURN_IN_DAYLIGHT.getAsBoolean() || !mob.isAlive() || !isSunBurnTick(mob)) {
            return;
        }
        ItemStack helmet = mob.getItemBySlot(EquipmentSlot.HEAD);
        if (!helmet.isEmpty()) {
            if (helmet.isDamageableItem()) {
                Item item = helmet.getItem();
                helmet.setDamageValue(helmet.getDamageValue() + mob.getRandom().nextInt(2));
                if (helmet.getDamageValue() >= helmet.getMaxDamage()) {
                    mob.onEquippedItemBroken(item, EquipmentSlot.HEAD);
                    mob.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                }
            }
        } else {
            mob.igniteForSeconds(8.0F);
        }
    }

    private static boolean isSunBurnTick(Mob mob) {
        if (mob.level().isClientSide()) {
            return false;
        }
        float lightValue = mob.getLightLevelDependentMagicValue();
        BlockPos pos = BlockPos.containing(mob.getX(), mob.getEyeY(), mob.getZ());
        boolean wet = mob.isInWaterOrRain() || mob.isInPowderSnow || mob.wasInPowderSnow;
        return lightValue > 0.5F
                && mob.getRandom().nextFloat() * 30.0F < (lightValue - 0.4F) * 2.0F
                && !wet
                && mob.level().canSeeSky(pos);
    }
}
