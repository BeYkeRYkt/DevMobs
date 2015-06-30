package ru.BeYkeRYkt.DevMobs.api.entity;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreeperPowerEvent;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.EntityPortalExitEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import ru.BeYkeRYkt.DevMobs.api.entity.variant.IEntityVariant;


public interface ICustomEntity {

    public String getID();

    public String getDisplayName();
    
    public String getDisplayNameID();

    public EntityType getEntityType();

    public Collection<IEntityVariant> getVariants();
    
    public IEntityVariant getVariant(int id);
    
    public void addVariant(IEntityVariant variant);
    
    public void removeVariant(int id);
    
    public void spawnEntity(Location loc);

    public void spawnEntity(Location loc, int id);
    
    public void spawnEntity(Location loc, IEntityVariant variant);
    
    public boolean isEnable();
    
    public void setEnable(boolean flag);

    public void onEntityDamage(EntityDamageEvent event);

    public void onEntityDamageByBlock(EntityDamageByBlockEvent event);

    public void onEntityDamageByEntity(EntityDamageByEntityEvent event);

    public void onCreeperPower(CreeperPowerEvent event);

    public void onEntityBreakDoor(EntityBreakDoorEvent event);

    public void onEntityChangeBlock(EntityChangeBlockEvent event);

    public void onEntityCombustByBlock(EntityCombustByBlockEvent event);

    public void onEntityCombustByEntity(EntityCombustByEntityEvent event);

    public void onEntityDeath(EntityDeathEvent event);

    public void onEntityExplode(EntityExplodeEvent event);

    public void onEntityInteract(EntityInteractEvent event);

    public void onEntityPortalEnter(EntityPortalEnterEvent event);

    public void onEntityPortal(EntityPortalEvent event);

    public void onEntityPortalExit(EntityPortalExitEvent event);

    public void onEntityRegainHealth(EntityRegainHealthEvent event);

    public void onEntityShootBowEvent(EntityShootBowEvent event);

    public void onEntitySpawn(Location location, LivingEntity entity);

    public void onEntityTame(EntityTameEvent event);

    public void onEntityTarget(EntityTargetEvent event);

    public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event);

    public void onEntityTeleport(EntityTeleportEvent event);

    public void onHorseJump(HorseJumpEvent event);

    public void onProjectileHit(ProjectileHitEvent event);

    public void onProjectileLaunch(ProjectileLaunchEvent event);

    public void onPlayerInteract(PlayerInteractEntityEvent event);
}