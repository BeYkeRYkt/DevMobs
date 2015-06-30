package ru.BeYkeRYkt.DevMobs.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

import ru.BeYkeRYkt.DevMobs.api.IEntityManager;
import ru.BeYkeRYkt.DevMobs.api.entity.ICustomEntity;

public class EntityManager implements IEntityManager {

    private Map<String, ICustomEntity> entities = new HashMap<String, ICustomEntity>();
    private Random r = new Random();
    private Plugin plugin;

    public EntityManager(Plugin plugin){
        this.plugin = plugin;
    }
    
    @Override
    public boolean registerEntity(ICustomEntity entity) {
        if(!entity.getEntityType().isAlive()){
            getPlugin().getLogger().info("Cannot register entity: " + entity.getDisplayName() + ". Reason: entity is not alive.");
            return false;
        }
        if (entities.containsKey(entity.getID())) {
            return false;
        }
        entities.put(entity.getID(), entity);
        getPlugin().getLogger().info("Registered new entity: " + entity.getDisplayName());
        return true;
    }

    @Override
    public boolean unregisterEntity(String id) {
        if (!entities.containsKey(id)) {
            return false;
        }
        entities.remove(id);
        getPlugin().getLogger().info("Unregistered entity: " + id);
        return false;
    }

    @Override
    public void spawnEntityRandom(Location location, EntityType type) {
        if(!type.isAlive()){
            getPlugin().getLogger().info("Cannot spawn entity. Reason: EntityType is not alive.");
        }
        List<ICustomEntity> list = new ArrayList<ICustomEntity>();
        // collect entities
        for (ICustomEntity entity : entities.values()) {
            if (entity.getEntityType() == type && entity.isEnable()) {
                if (!list.contains(entity)) {
                    list.add(entity);
                }
            }
        }

        if(list.isEmpty())return;
        // collect entity if spawnPercent < randomPercent
        // ICustomEntity entity = list.get(0);
        // int percent = r.nextInt(99);
        // for (ICustomEntity ent : list) {
        //    if (ent.getSpawnPercent() >= percent) {
        //        entity = ent;
        //    }
        //}
        
        int index = r.nextInt(list.size());
        ICustomEntity entity = list.get(index);
        
        if (entity != null) {
            entity.spawnEntity(location);
        }
    }

    @Override
    public void spawnEntity(Location location, ICustomEntity entity, int level) {
        entity.spawnEntity(location, level);
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public boolean availableEntityType(EntityType type) {
        for (ICustomEntity entity : entities.values()) {
            if (entity.getEntityType() == type) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ICustomEntity getEntityFromDisplayName(String name) {
        ICustomEntity entity = null;
        for(ICustomEntity e: entities.values()){
            if(e.getDisplayNameID().equals(name)){
                entity = e;
                break;
            }
        }
        return entity;
    }

    @Override
    public ICustomEntity getEntityFromId(String id) {
        return entities.get(id);
    }

    @Override
    public Random getRandom() {
        return r;
    }
}