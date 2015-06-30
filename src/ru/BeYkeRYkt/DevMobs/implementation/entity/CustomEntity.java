package ru.BeYkeRYkt.DevMobs.implementation.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.ChatColor;
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

import ru.BeYkeRYkt.DevMobs.api.Utils;
import ru.BeYkeRYkt.DevMobs.api.entity.ICustomEntity;
import ru.BeYkeRYkt.DevMobs.api.entity.variant.IEntityVariant;
import ru.BeYkeRYkt.DevMobs.implementation.entity.variant.EntityVariant;

public class CustomEntity implements ICustomEntity {

    private String id;
    private String name;
    private EntityType type;
    private Map<Integer, IEntityVariant> variants;
    private Random random;
    private boolean enable;

    public CustomEntity(String id, String name, EntityType type) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.variants = new HashMap<Integer, IEntityVariant>();
        this.random = new Random();
        this.enable = true;
        addVariant(new EntityVariant(0, Utils.getHealthFromEntityType(type)));
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return name;
    }

    @Override
    public EntityType getEntityType() {
        return type;
    }

    @Override
    public Collection<IEntityVariant> getVariants() {
        return variants.values();
    }

    @Override
    public IEntityVariant getVariant(int id) {
        return variants.get(id);
    }

    @Override
    public void addVariant(IEntityVariant variant) {
        if (variants.get(variant.getId()) == null) {
            variants.put(variant.getId(), variant);
        }
    }

    @Override
    public void removeVariant(int id) {
        if (variants.get(id) != null) {
            variants.remove(id);
        }
    }

    @Override
    public void spawnEntity(Location loc) {
        IEntityVariant variant = variants.get(0);
        int percent = random.nextInt(getVariants().size());
        variant = getVariant(percent);

        spawnEntity(loc, variant);
    }

    @Override
    public void spawnEntity(Location loc, int id) {
        IEntityVariant l = getVariant(id);
        spawnEntity(loc, l);
    }

    @Override
    public void spawnEntity(Location loc, IEntityVariant level) {
        LivingEntity entity = (LivingEntity) loc.getWorld().spawnEntity(loc, getEntityType());
        entity.setCustomName(getDisplayName() + ChatColor.WHITE + " : " + level.getChatColor() + level.getId());
        entity.setMaxHealth(level.getMaxHealth());
        entity.setHealth(level.getMaxHealth());

        if (level.getHelmet() != null) {
            entity.getEquipment().setHelmet(level.getHelmet());
        }

        if (level.getChestplate() != null) {
            entity.getEquipment().setChestplate(level.getChestplate());
        }

        if (level.getLeggings() != null) {
            entity.getEquipment().setLeggings(level.getLeggings());
        }

        if (level.getBoots() != null) {
            entity.getEquipment().setBoots(level.getBoots());
        }

        if (level.getItemInHand() != null) {
            entity.getEquipment().setItemInHand(level.getItemInHand());
        }

        entity.getEquipment().setHelmetDropChance(level.getHelmetDropChance());
        entity.getEquipment().setChestplateDropChance(level.getChestplateDropChance());
        entity.getEquipment().setLeggingsDropChance(level.getLeggingsDropChance());
        entity.getEquipment().setBootsDropChance(level.getBootsDropChance());
        entity.getEquipment().setItemInHandDropChance(level.getItemInHandDropChance());
        
        entity.addPotionEffects(level.getPotionEffects());
        entity.setCanPickupItems(level.getCanPickupItems());
        onEntitySpawn(entity.getLocation(), entity);
    }

    @Override
    public boolean isEnable() {
        return enable;
    }

    @Override
    public void setEnable(boolean flag) {
        this.enable = flag;
    }

    @Override
    public void onEntityDamage(EntityDamageEvent event) {
    }

    @Override
    public void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
    }

    @Override
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    }

    @Override
    public void onCreeperPower(CreeperPowerEvent event) {
    }

    @Override
    public void onEntityBreakDoor(EntityBreakDoorEvent event) {
    }

    @Override
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
    }

    @Override
    public void onEntityCombustByBlock(EntityCombustByBlockEvent event) {
    }

    @Override
    public void onEntityCombustByEntity(EntityCombustByEntityEvent event) {
    }

    @Override
    public void onEntityDeath(EntityDeathEvent event) {
    }

    @Override
    public void onEntityExplode(EntityExplodeEvent event) {
    }

    @Override
    public void onEntityInteract(EntityInteractEvent event) {
    }

    @Override
    public void onEntityPortalEnter(EntityPortalEnterEvent event) {
    }

    @Override
    public void onEntityPortal(EntityPortalEvent event) {
    }

    @Override
    public void onEntityPortalExit(EntityPortalExitEvent event) {
    }

    @Override
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
    }

    @Override
    public void onEntityShootBowEvent(EntityShootBowEvent event) {
    }

    @Override
    public void onEntitySpawn(Location loc, LivingEntity entity) {
    }

    @Override
    public void onEntityTame(EntityTameEvent event) {
    }

    @Override
    public void onEntityTarget(EntityTargetEvent event) {
    }

    @Override
    public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
    }

    @Override
    public void onEntityTeleport(EntityTeleportEvent event) {
    }

    @Override
    public void onHorseJump(HorseJumpEvent event) {
    }

    @Override
    public void onProjectileHit(ProjectileHitEvent event) {
    }

    @Override
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
    }

    @Override
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CustomEntity)) {
            return false;
        }
        CustomEntity other = (CustomEntity) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (type != other.type) {
            return false;
        }
        return true;
    }

    @Override
    public String getDisplayNameID() {
        return ChatColor.stripColor(getDisplayName());
    }
}