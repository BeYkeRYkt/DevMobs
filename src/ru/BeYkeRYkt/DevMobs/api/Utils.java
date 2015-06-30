package ru.BeYkeRYkt.DevMobs.api;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import ru.BeYkeRYkt.DevMobs.DevMobs;
import ru.BeYkeRYkt.DevMobs.api.entity.ICustomEntity;
import ru.BeYkeRYkt.DevMobs.api.entity.variant.IEntityVariant;


public class Utils {
    
    private static World world = Bukkit.getWorlds().get(0);
    private static Location location = new Location(world, 0, 0, 0);
    
    public static String parseName(String entityDisplayName){
        entityDisplayName = ChatColor.stripColor(entityDisplayName);
        String[] parts = entityDisplayName.split(" : ");
        return parts[0];
    }
    
    public static int parseId(String entityDisplayName){
        entityDisplayName = ChatColor.stripColor(entityDisplayName);
        String[] parts = entityDisplayName.split(" : ");
        return Integer.parseInt(parts[1]);
    }    
    
    public static IEntityVariant getVariantFromEntity(Entity entity){
        String name = parseName(entity.getCustomName());
        ICustomEntity e = DevMobs.getEntityManager().getEntityFromDisplayName(name);
        return e.getVariant(parseId(entity.getCustomName()));
    }
    
    public static double getHealthFromEntityType(EntityType type){
        double health = 0;
        if(type.isAlive()){
            //switch (type) {
            //    case BLAZE:
            //    case BAT:
            //        return 6;
            //    case CAVE_SPIDER:
            //    case CHICKEN:
            //    case COW:
            //    case CREEPER: 
            //    case ENDER_DRAGON:
            //    case ENDERMAN:
            //    case ENDERMITE:
            //    case GHAST:
            //    case 
            //} - very lazy person :3
            
            LivingEntity entity = (LivingEntity) world.spawnEntity(location, type);
            health = entity.getMaxHealth();
            entity.remove();
        }
        return health;
    }
}