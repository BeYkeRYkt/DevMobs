package ru.BeYkeRYkt.DevMobs;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import ru.BeYkeRYkt.DevMobs.api.IEntityManager;
import ru.BeYkeRYkt.DevMobs.api.Utils;
import ru.BeYkeRYkt.DevMobs.api.entity.ICustomEntity;
import ru.BeYkeRYkt.DevMobs.api.entity.variant.IEntityVariant;
import ru.BeYkeRYkt.DevMobs.implementation.EntityManager;
import ru.BeYkeRYkt.DevMobs.implementation.entity.variant.VariantLoader;
import ru.BeYkeRYkt.DevMobsPack.FasterZombie;
import ru.BeYkeRYkt.DevMobsPack.HeavyZombie;
import ru.BeYkeRYkt.DevMobsPack.SkeletonRider;

public class DevMobs extends JavaPlugin implements Listener {

    private static IEntityManager eManager;
    private static DevMobs plugin;
    private int percent;
    private VariantLoader loader;

    @SuppressWarnings("static-access")
    @Override
    public void onLoad() {
        this.plugin = this;
        this.eManager = new EntityManager(this);
        this.loader = new VariantLoader(eManager);
    }

    @Override
    public void onEnable() {
        try {
            FileConfiguration fc = getConfig();
            if (!new File(getDataFolder(), "config.yml").exists()) {
                fc.options().header("DevMobs v" + getDescription().getVersion() + " Configuration" + "\nHave fun :3" + "\nby BeYkeRYkt");
                fc.set("Plugin.loadCustomVariantsDelaySeconds", 3); // Half-Life
                                                                    // 3
                fc.set("Plugin.spawnPercent", 50);
                fc.set("Entities.variants.testZombie.baseEntity", "testZombie");
                fc.set("Entities.variants.testZombie.maxHealth", 2);
                fc.set("Entities.variants.testZombie.percentDrop", 50);
                fc.set("Entities.variants.testZombie.color", "RED");
                fc.set("Entities.variants.testZombie.equipment.helmet", "298:0");
                fc.set("Entities.variants.testZombie.equipment.chestplate", "299:0");
                fc.set("Entities.variants.testZombie.equipment.leggings", "300:0");
                fc.set("Entities.variants.testZombie.equipment.boots", "301:0");

                ArrayList<String> drop = new ArrayList<String>();
                drop.add("2:5");
                drop.add("5:1");
                getConfig().set("Entities.variants.testZombie.dropList", drop);

                ArrayList<String> potion = new ArrayList<String>();
                potion.add("SPEED:2:20");
                getConfig().set("Entities.variants.testZombie.potionList", potion);

                fc.options().copyDefaults(true);
                saveConfig();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int delay = getConfig().getInt("Plugin.loadCustomVariantsDelaySeconds");
        percent = getConfig().getInt("Plugin.spawnPercent");
        getServer().getPluginManager().registerEvents(this, this);

        getEntityManager().registerEntity(new FasterZombie());
        getEntityManager().registerEntity(new HeavyZombie());
        getEntityManager().registerEntity(new SkeletonRider());

        getServer().getScheduler().runTaskLaterAsynchronously(this, new Runnable() {

            @Override
            public void run() {
                getLoader().loadVariants(getConfig());
            }
        }, delay * 20);
    }

    public static DevMobs getInstance() {
        return plugin;
    }

    public static IEntityManager getEntityManager() {
        return eManager;
    }

    public VariantLoader getLoader() {
        return loader;
    }

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent event) {
        if (event.isCancelled())
            return;
        int percent = getEntityManager().getRandom().nextInt(100);
        if (percent < this.percent) {
            if (event.getSpawnReason() == SpawnReason.NATURAL || event.getSpawnReason() == SpawnReason.SPAWNER_EGG || event.getSpawnReason() == SpawnReason.DEFAULT) {
                EntityType type = event.getEntityType();
                if (getEntityManager().availableEntityType(type)) {
                    event.getEntity().remove();
                    getEntityManager().spawnEntityRandom(event.getLocation(), type);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.isCancelled())
            return;
        Entity entity = event.getEntity();
        if (entity.getCustomName() != null) {
            String name = Utils.parseName(entity.getCustomName());
            if (name != null) {
                ICustomEntity ce = getEntityManager().getEntityFromDisplayName(name);
                if (ce != null) {
                    ce.onEntityDamage(event);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
        if (event.isCancelled())
            return;
        Entity entity = event.getEntity();
        if (entity.getCustomName() != null) {
            String name = Utils.parseName(entity.getCustomName());
            if (name != null) {
                ICustomEntity ce = getEntityManager().getEntityFromDisplayName(name);
                if (ce != null) {
                    ce.onEntityDamageByBlock(event);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.isCancelled())
            return;
        Entity entity = event.getEntity();
        if (entity.getCustomName() != null) {
            String name = Utils.parseName(entity.getCustomName());
            if (name != null) {
                ICustomEntity ce = getEntityManager().getEntityFromDisplayName(name);
                if (ce != null) {
                    ce.onEntityDamageByEntity(event);
                }
            }
        }
    }

    @EventHandler
    public void onCreeperPower(CreeperPowerEvent event) {
        if (event.isCancelled())
            return;
        Entity entity = event.getEntity();
        if (entity.getCustomName() != null) {
            String name = Utils.parseName(entity.getCustomName());
            if (name != null) {
                ICustomEntity ce = getEntityManager().getEntityFromDisplayName(name);
                if (ce != null) {
                    ce.onCreeperPower(event);
                }
            }
        }
    }

    @EventHandler
    public void onEntityBreakDoor(EntityBreakDoorEvent event) {
        if (event.isCancelled())
            return;
        Entity entity = event.getEntity();
        if (entity.getCustomName() != null) {
            String name = Utils.parseName(entity.getCustomName());
            if (name != null) {
                ICustomEntity ce = getEntityManager().getEntityFromDisplayName(name);
                if (ce != null) {
                    ce.onEntityBreakDoor(event);
                }
            }
        }
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (event.isCancelled())
            return;
        Entity entity = event.getEntity();
        if (entity.getCustomName() != null) {
            String name = Utils.parseName(entity.getCustomName());
            if (name != null) {
                ICustomEntity ce = getEntityManager().getEntityFromDisplayName(name);
                if (ce != null) {
                    ce.onEntityChangeBlock(event);
                }
            }
        }
    }

    @EventHandler
    public void onEntityCombustByBlock(EntityCombustByBlockEvent event) {
        if (event.isCancelled())
            return;
        Entity entity = event.getEntity();
        if (entity.getCustomName() != null) {
            String name = Utils.parseName(entity.getCustomName());
            if (name != null) {
                ICustomEntity ce = getEntityManager().getEntityFromDisplayName(name);
                if (ce != null) {
                    ce.onEntityCombustByBlock(event);
                }
            }
        }
    }

    @EventHandler
    public void onEntityCombustByEntity(EntityCombustByEntityEvent event) {
        if (event.isCancelled())
            return;
        Entity entity = event.getEntity();
        if (entity.getCustomName() != null) {
            String name = Utils.parseName(entity.getCustomName());
            if (name != null) {
                ICustomEntity ce = getEntityManager().getEntityFromDisplayName(name);
                if (ce != null) {
                    ce.onEntityCombustByEntity(event);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity.getCustomName() != null) {
            String name = Utils.parseName(entity.getCustomName());
            if (name != null) {
                ICustomEntity ce = getEntityManager().getEntityFromDisplayName(name);

                if (ce != null) {
                    ce.onEntityDeath(event);

                    // drop
                    IEntityVariant level = ce.getVariant(Utils.parseId(entity.getCustomName()));
                    int percent = new Random().nextInt(99);
                    if (percent < level.getPercentDrop()) {
                        for (ItemStack item : level.getEntityDrops()) {
                            entity.getWorld().dropItemNaturally(entity.getLocation(), item);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.isCancelled())
            return;
        Entity entity = event.getEntity();
        if (entity.getCustomName() != null) {
            String name = Utils.parseName(entity.getCustomName());
            if (name != null) {
                ICustomEntity ce = getEntityManager().getEntityFromDisplayName(name);
                if (ce != null) {
                    ce.onEntityExplode(event);
                }
            }
        }
    }

    @EventHandler
    public void onEntityInteract(EntityInteractEvent event) {
        if (event.isCancelled())
            return;
        Entity entity = event.getEntity();
        if (entity.getCustomName() != null) {
            String name = Utils.parseName(entity.getCustomName());
            if (name != null) {
                ICustomEntity ce = getEntityManager().getEntityFromDisplayName(name);
                if (ce != null) {
                    ce.onEntityInteract(event);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        if (event.isCancelled())
            return;
        Entity entity = event.getRightClicked();
        if (entity.getCustomName() != null) {
            String name = Utils.parseName(entity.getCustomName());
            if (name != null) {
                ICustomEntity ce = getEntityManager().getEntityFromDisplayName(name);
                if (ce != null) {
                    ce.onPlayerInteract(event);
                }
            }
        }
    }

    @EventHandler
    public void onEntityPortalEnter(EntityPortalEnterEvent event) {
        Entity entity = event.getEntity();
        if (entity.getCustomName() != null) {
            String name = Utils.parseName(entity.getCustomName());
            if (name != null) {
                ICustomEntity ce = getEntityManager().getEntityFromDisplayName(name);
                if (ce != null) {
                    ce.onEntityPortalEnter(event);
                }
            }
        }
    }

    @EventHandler
    public void onEntityPortal(EntityPortalEvent event) {
        if (event.isCancelled())
            return;
        Entity entity = event.getEntity();
        if (entity.getCustomName() != null) {
            String name = Utils.parseName(entity.getCustomName());
            if (name != null) {
                ICustomEntity ce = getEntityManager().getEntityFromDisplayName(name);
                if (ce != null) {
                    ce.onEntityPortal(event);
                }
            }
        }
    }

    @EventHandler
    public void onEntityPortalExit(EntityPortalExitEvent event) {
        if (event.isCancelled())
            return;
        Entity entity = event.getEntity();
        if (entity.getCustomName() != null) {
            String name = Utils.parseName(entity.getCustomName());
            if (name != null) {
                ICustomEntity ce = getEntityManager().getEntityFromDisplayName(name);
                if (ce != null) {
                    ce.onEntityPortalExit(event);
                }
            }
        }
    }

    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        if (event.isCancelled())
            return;
        Entity entity = event.getEntity();
        if (entity.getCustomName() != null) {
            String name = Utils.parseName(entity.getCustomName());
            if (name != null) {
                ICustomEntity ce = getEntityManager().getEntityFromDisplayName(name);
                if (ce != null) {
                    ce.onEntityRegainHealth(event);
                }
            }
        }
    }

    @EventHandler
    public void onEntityShootBowEvent(EntityShootBowEvent event) {
        if (event.isCancelled())
            return;
        Entity entity = event.getEntity();
        if (entity.getCustomName() != null) {
            String name = Utils.parseName(entity.getCustomName());
            if (name != null) {
                ICustomEntity ce = getEntityManager().getEntityFromDisplayName(name);
                if (ce != null) {
                    ce.onEntityShootBowEvent(event);
                }
            }
        }
    }

    @EventHandler
    public void onEntityTame(EntityTameEvent event) {
        if (event.isCancelled())
            return;
        Entity entity = event.getEntity();
        if (entity.getCustomName() != null) {
            String name = Utils.parseName(entity.getCustomName());
            if (name != null) {
                ICustomEntity ce = getEntityManager().getEntityFromDisplayName(name);
                if (ce != null) {
                    ce.onEntityTame(event);
                }
            }
        }
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if (event.isCancelled())
            return;
        Entity entity = event.getEntity();
        if (entity.getCustomName() != null) {
            String name = Utils.parseName(entity.getCustomName());
            if (name != null) {
                ICustomEntity ce = getEntityManager().getEntityFromDisplayName(name);
                if (ce != null) {
                    ce.onEntityTarget(event);
                }
            }
        }
    }

    @EventHandler
    public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
        if (event.isCancelled())
            return;
        Entity entity = event.getEntity();

        // hotfix
        if (entity.getType() == EntityType.WOLF) {
            Wolf wolf = (Wolf) entity;
            if (wolf.getPassenger() != null && wolf.getPassenger().equals(event.getTarget())) {
                event.setCancelled(true);
            }
        }

        if (entity.getCustomName() != null) {
            String name = Utils.parseName(entity.getCustomName());
            if (name != null) {
                ICustomEntity ce = getEntityManager().getEntityFromDisplayName(name);
                if (ce != null) {
                    ce.onEntityTargetLivingEntity(event);
                }
            }
        }
    }

    @EventHandler
    public void onEntityTeleport(EntityTeleportEvent event) {
        if (event.isCancelled())
            return;
        Entity entity = event.getEntity();
        if (entity.getCustomName() != null) {
            String name = Utils.parseName(entity.getCustomName());
            if (name != null) {
                ICustomEntity ce = getEntityManager().getEntityFromDisplayName(name);
                if (ce != null) {
                    ce.onEntityTeleport(event);
                }
            }
        }
    }

    @EventHandler
    public void onHorseJump(HorseJumpEvent event) {
        if (event.isCancelled())
            return;
        Entity entity = event.getEntity();
        if (entity.getCustomName() != null) {
            String name = Utils.parseName(entity.getCustomName());
            if (name != null) {
                ICustomEntity ce = getEntityManager().getEntityFromDisplayName(name);
                if (ce != null) {
                    ce.onHorseJump(event);
                }
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Entity entity = event.getEntity();
        if (entity.getCustomName() != null) {
            String name = Utils.parseName(entity.getCustomName());
            if (name != null) {
                ICustomEntity ce = getEntityManager().getEntityFromDisplayName(name);
                if (ce != null) {
                    ce.onProjectileHit(event);
                }
            }
        }
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (event.isCancelled())
            return;
        Entity entity = event.getEntity();
        if (entity.getCustomName() != null) {
            String name = Utils.parseName(entity.getCustomName());
            if (name != null) {
                ICustomEntity ce = getEntityManager().getEntityFromDisplayName(name);
                if (ce != null) {
                    ce.onProjectileLaunch(event);
                }
            }
        }
    }
}