package ru.BeYkeRYkt.DevMobsPack;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ru.BeYkeRYkt.DevMobs.implementation.entity.CustomEntity;

public class HeavyZombie extends CustomEntity {

    public HeavyZombie() {
        super("heavyZombie", ChatColor.RED + "Heavy Zombie", EntityType.ZOMBIE);
    }
    
    @Override
    public void onEntitySpawn(Location loc, LivingEntity entity) {
        World world = loc.getWorld();
        Zombie zombie = (Zombie) entity;
        world.playSound(loc, Sound.ANVIL_LAND, 10, 1);
        world.playEffect(loc, Effect.CRIT, 0);
        zombie.setBaby(false);
        zombie.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1));
    }
    
    @Override
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        event.setDamage((event.getDamage() / 100) * 40);
    }
}