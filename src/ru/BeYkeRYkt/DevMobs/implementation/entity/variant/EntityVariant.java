package ru.BeYkeRYkt.DevMobs.implementation.entity.variant;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import ru.BeYkeRYkt.DevMobs.api.entity.ICustomEntity;
import ru.BeYkeRYkt.DevMobs.api.entity.variant.IEntityVariant;

public class EntityVariant implements IEntityVariant {

    private ItemStack helmet;
    private ItemStack chestplate;
    private ItemStack leggings;
    private ItemStack boots;
    private ItemStack hand;
    private List<PotionEffect> effects;
    private List<ItemStack> drops;
    private double maxHealth;
    private int id;
    private int percentDrop;
    private ChatColor color;
    private boolean canPickup;
    private int helmetChance;
    private int chestplateChance;
    private int leggingsChance;
    private int bootsChance;
    private int itemChance;

    public EntityVariant(int id, double maxHeathLevel, int percentDrop, ChatColor color, ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots, ItemStack hand) {
        this.id = id;
        this.maxHealth = maxHeathLevel;
        this.percentDrop = percentDrop;
        this.color = color;
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
        this.hand = hand;
        this.effects = new ArrayList<PotionEffect>();
        this.drops = new ArrayList<ItemStack>();
    }

    public EntityVariant(ICustomEntity entity, double maxHeathLevel, int percentDrop, ChatColor color, ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots, ItemStack hand) {
        this(entity.getVariants().size(), maxHeathLevel, percentDrop, color, helmet, chestplate, leggings, boots, hand);
    }

    public EntityVariant(int id, double maxHeathLevel) {
        this(id, maxHeathLevel, 0, ChatColor.WHITE, null, null, null, null, null);
    }

    public EntityVariant(ICustomEntity entity, double maxHeathLevel) {
        this(entity, maxHeathLevel, 0, null, null, null, null, null, null);
    }
    
    public EntityVariant(int id, IEntityVariant variant) {
        this(id, variant.getMaxHealth(), variant.getPercentDrop(), variant.getChatColor(), variant.getHelmet(), variant.getChestplate(), variant.getLeggings(), variant.getBoots(), variant.getBoots());
    }
    
    public EntityVariant(ICustomEntity entity, IEntityVariant variant) {
        this(entity.getVariants().size(), variant.getMaxHealth(), variant.getPercentDrop(), variant.getChatColor(), variant.getHelmet(), variant.getChestplate(), variant.getLeggings(), variant.getBoots(), variant.getBoots());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public double getMaxHealth() {
        return maxHealth;
    }

    @Override
    public ItemStack getHelmet() {
        return helmet;
    }

    @Override
    public ItemStack getChestplate() {
        return chestplate;
    }

    @Override
    public ItemStack getLeggings() {
        return leggings;
    }

    @Override
    public ItemStack getBoots() {
        return boots;
    }

    @Override
    public ItemStack getItemInHand() {
        return hand;
    }

    @Override
    public List<PotionEffect> getPotionEffects() {
        return effects;
    }

    @Override
    public List<ItemStack> getEntityDrops() {
        return drops;
    }

    @Override
    public int getPercentDrop() {
        return percentDrop;
    }

    @Override
    public ChatColor getChatColor() {
        return color;
    }

    @Override
    public void setHelmet(ItemStack item) {
        this.helmet = item;
    }

    @Override
    public void setChestplate(ItemStack item) {
        this.chestplate = item;
    }

    @Override
    public void setLeggings(ItemStack item) {
        this.leggings = item;
    }

    @Override
    public void setBoots(ItemStack item) {
        this.boots = item;
    }

    @Override
    public void setItemInHand(ItemStack item) {
        this.hand = item;
    }

    @Override
    public void setPercentDrop(int percent) {
        this.percentDrop = percent;
    }

    @Override
    public void setChatColor(ChatColor color) {
        this.color = color;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        long temp;
        temp = Double.doubleToLongBits(maxHealth);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + percentDrop;
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
        if (!(obj instanceof EntityVariant)) {
            return false;
        }
        EntityVariant other = (EntityVariant) obj;
        if (id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(maxHealth) != Double.doubleToLongBits(other.maxHealth)) {
            return false;
        }
        if (percentDrop != other.percentDrop) {
            return false;
        }
        return true;
    }

    @Override
    public boolean getCanPickupItems() {
        return canPickup;
    }

    @Override
    public void setPickupItems(boolean flag) {
        canPickup = flag;
    }

    @Override
    public int getHelmetDropChance() {
        return helmetChance;
    }

    @Override
    public void setHelmetDropChance(int chance) {
        helmetChance = chance;
    }

    @Override
    public int getChestplateDropChance() {
        return chestplateChance;
    }

    @Override
    public void setChestplateDropChance(int chance) {
        chestplateChance = chance;
    }

    @Override
    public int getLeggingsDropChance() {
        return leggingsChance;
    }

    @Override
    public void setLeggingsDropChance(int chance) {
        leggingsChance = chance;
    }

    @Override
    public int getBootsDropChance() {
        return bootsChance;
    }

    @Override
    public void setBootsDropChance(int chance) {
        bootsChance = chance;
    }

    @Override
    public int getItemInHandDropChance() {
        return itemChance;
    }

    @Override
    public void setItemInHandChance(int chance) {
        itemChance = chance; 
    }

}