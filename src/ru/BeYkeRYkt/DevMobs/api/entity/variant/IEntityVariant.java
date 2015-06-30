package ru.BeYkeRYkt.DevMobs.api.entity.variant;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public interface IEntityVariant {
    
    public int getId();
    
    public double getMaxHealth();
    
    public ItemStack getHelmet();
    
    public void setHelmet(ItemStack item);
    
    public ItemStack getChestplate();
    
    public void setChestplate(ItemStack item);

    public ItemStack getLeggings();
    
    public void setLeggings(ItemStack item);

    public ItemStack getBoots();
    
    public void setBoots(ItemStack item);
    
    public ItemStack getItemInHand();
    
    public void setItemInHand(ItemStack item);

    public List<PotionEffect> getPotionEffects();
    
    public List<ItemStack> getEntityDrops();
    
    public int getPercentDrop();
    
    public void setPercentDrop(int percent);

    public ChatColor getChatColor();
    
    public void setChatColor(ChatColor color);
    
    public boolean getCanPickupItems();
    
    public void setPickupItems(boolean flag);
    
    public int getHelmetDropChance();
    
    public void setHelmetDropChance(int chance);
    
    public int getChestplateDropChance();
    
    public void setChestplateDropChance(int chance);
    
    public int getLeggingsDropChance();
    
    public void setLeggingsDropChance(int chance);
    
    public int getBootsDropChance();
    
    public void setBootsDropChance(int chance);
    
    public int getItemInHandDropChance();
    
    public void setItemInHandChance(int chance);
}