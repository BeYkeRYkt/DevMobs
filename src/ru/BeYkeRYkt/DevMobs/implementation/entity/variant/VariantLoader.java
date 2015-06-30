package ru.BeYkeRYkt.DevMobs.implementation.entity.variant;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ru.BeYkeRYkt.DevMobs.api.IEntityManager;
import ru.BeYkeRYkt.DevMobs.api.entity.ICustomEntity;
import ru.BeYkeRYkt.DevMobs.api.entity.variant.IEntityVariant;

public class VariantLoader {

    private IEntityManager manager;
    private String path = "Entities.variants.";

    public VariantLoader(IEntityManager manager) {
        this.manager = manager;
    }

    public void loadVariant(FileConfiguration config, String id) {
        String baseEntity = config.getString(path + id + ".baseEntity");
        double health = config.getDouble(path + id + ".maxHealth");
        String helmet = config.getString(path + id + ".equipment.helmet");
        String chestplate = config.getString(path + id + ".equipment.chestplate");
        String leggings = config.getString(path + id + ".equipment.leggings");
        String boots = config.getString(path + id + ".equipment.boots");
        String hand = config.getString(path + id + ".equipment.hand");
        int percentDrop = config.getInt(path + id + ".percentDrop");
        String color = config.getString(path + id + ".color");

        List<String> drop = config.getStringList(path + "." + id + ".dropList");
        List<String> potions = config.getStringList(path + "." + id + ".potionList");

        ICustomEntity entity = manager.getEntityFromId(baseEntity);
        if (entity == null) {
            manager.getPlugin().getLogger().info("Error loading entity: " + "null baseEntity");
            return;
        }

        IEntityVariant variant = new EntityVariant(entity, health);
        variant.setPercentDrop(percentDrop);

        ChatColor ccolor = ChatColor.WHITE;
        if (color != null) {
            if (ChatColor.valueOf(color) != null) {
                ccolor = ChatColor.valueOf(color);
            }
        }
        variant.setChatColor(ccolor);
        variant.setHelmet(parseItemStack(helmet));
        variant.setChestplate(parseItemStack(chestplate));
        variant.setLeggings(parseItemStack(leggings));
        variant.setBoots(parseItemStack(boots));
        variant.setItemInHand(parseItemStack(hand));

        // Drops
        if (drop != null && !drop.isEmpty()) {
            for (String s : drop) {
                ItemStack item = parseItemStack(s);
                if (item != null) {
                    variant.getEntityDrops().add(item);
                }
            }
            drop.clear();
        }

        // Potions
        if (potions != null && !potions.isEmpty()) {
            for (String s : potions) {
                PotionEffect effect = parsePotion(s);
                if (effect != null) {
                    variant.getPotionEffects().add(effect);
                }
            }
            potions.clear();
        }

        entity.addVariant(variant);

        if (entity.getVariant(variant.getId()) != null) {
            manager.getPlugin().getLogger().info("Custom variant " + id + " for " + baseEntity + " has been loaded.");
        }
    }

    public void loadVariants(FileConfiguration config) {
        for (String string : config.getConfigurationSection(path).getKeys(false)) {
            loadVariant(config, string);
        }
    }

    @SuppressWarnings("deprecation")
    public ItemStack parseItemStack(String itemId) {
        if (itemId == null)
            //return new ItemStack(Material.AIR);
            return null;
        String[] parts = itemId.split(":");
        // String mat = parts[0].toUpperCase();
        int mat = Integer.parseInt(parts[0]);
        if (parts.length == 2) {
            int amount = Integer.parseInt(parts[1]);
            return new ItemStack(Material.getMaterial(mat), amount);
        } else if (parts.length == 3) {
            int amount = Integer.parseInt(parts[1]);
            short data = Short.parseShort(parts[2]);
            return new ItemStack(Material.getMaterial(mat), amount, data);
        }
        return new ItemStack(Material.getMaterial(mat));
    }

    public PotionEffect parsePotion(String potionId) {
        if(potionId == null) return null;
        String[] parts = potionId.split(":");
        String potionName = parts[0].toUpperCase();
        if (parts.length == 2) {
            int level = Integer.parseInt(parts[1]);
            return new PotionEffect(PotionEffectType.getByName(potionName), 20 * 30, level);
        } else if (parts.length == 3) {
            int level = Integer.parseInt(parts[1]);
            int time = Integer.parseInt(parts[2]) * 20;
            return new PotionEffect(PotionEffectType.getByName(potionName), time, level);
        }
        return new PotionEffect(PotionEffectType.getByName(potionName), 20 * 30, 1);
    }
}