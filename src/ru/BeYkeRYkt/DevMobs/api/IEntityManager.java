package ru.BeYkeRYkt.DevMobs.api;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

import ru.BeYkeRYkt.DevMobs.api.entity.ICustomEntity;

public interface IEntityManager {
    
    public boolean registerEntity(ICustomEntity entity);
    
    public boolean unregisterEntity(String id);
    
    public ICustomEntity getEntityFromDisplayName(String name);
    
    public ICustomEntity getEntityFromId(String id);
    
    public void spawnEntityRandom(Location location, EntityType type);
    
    public void spawnEntity(Location location, ICustomEntity entity, int level);
    
    public boolean availableEntityType(EntityType type);
    
    public Plugin getPlugin();
    
    public Random getRandom();
}