package ru.BeYkeRYkt.DevMobsPack;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ru.BeYkeRYkt.DevMobs.implementation.entity.CustomEntity;

public class SkeletonRider extends CustomEntity {

    public SkeletonRider() {
        super("skeletonRider", ChatColor.AQUA + "Skeleton Rider", EntityType.SKELETON);
    }

    @Override
    public void onEntitySpawn(Location loc, LivingEntity entity) {
        Wolf wolf = (Wolf) loc.getWorld().spawnEntity(loc, EntityType.WOLF);
        wolf.setAdult();
        wolf.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
        wolf.setPassenger(entity);
    }

    @Override
    public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
        if (event.getEntity().getVehicle() != null && event.getEntity().getVehicle().getType() == EntityType.WOLF) {
            Wolf wolf = (Wolf) event.getEntity().getVehicle();

            if (wolf.getTarget() == null && !wolf.getTarget().equals(event.getTarget())) {
                wolf.damage(0, event.getTarget());
            }
        }
    }

}