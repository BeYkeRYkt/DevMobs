package ru.BeYkeRYkt.DevMobsPack;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ru.BeYkeRYkt.DevMobs.implementation.entity.CustomEntity;

public class FasterZombie extends CustomEntity {

    public FasterZombie() {
        super("fasterZombie", ChatColor.GREEN + "Faster Zombie", EntityType.ZOMBIE);
    }
    
    @Override
    public void onEntitySpawn(Location loc, LivingEntity entity) {
        World world = loc.getWorld();
        world.playSound(loc, Sound.GLASS, 10, 1);
        world.playEffect(loc, Effect.POTION_BREAK, 2);
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
    }
}