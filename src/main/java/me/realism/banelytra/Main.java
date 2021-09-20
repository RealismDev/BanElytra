package me.realism.banelytra;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        new EntityGlideListener(this);
        this.getCommand("banelytra").setExecutor(new ReloadCommand(this));
        Bukkit.getLogger().info(this.getConfig().getString("reload.message"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
