package me.realism.banelytra;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;


public class EntityGlideListener implements Listener {
    private final Main main;
    public EntityGlideListener(Main main) {
        Bukkit.getPluginManager().registerEvents(this, main);
        this.main = main;
    }


    @EventHandler
    public void OnEntityGlide(EntityToggleGlideEvent e) {

        if (e.getEntity().getType().equals(EntityType.PLAYER)) {

            FileConfiguration cfg = main.getConfig();

            Player player = (Player) e.getEntity();

            World world = player.getWorld();
            String worldName = world.getName();

            if (cfg.getBoolean("world." + worldName)) {
                // Players are allowed to use the elytra in this dimension
                return;
            }

            e.setCancelled(true);

            PlayerInventory inventory = player.getInventory();
            if (!( (inventory.getChestplate() != null) && inventory.getChestplate().getType().equals(Material.ELYTRA) ))
                return;

            ItemStack elytra = inventory.getChestplate();
            inventory.setChestplate(null);

            if (inventory.firstEmpty() != -1) {
                // Inventory has slot available
                inventory.addItem(elytra);
            }

            else {
                // Inventory was full
                world.dropItemNaturally(player.getLocation(), elytra);
            }
            player.updateInventory();
        }
    }
}
