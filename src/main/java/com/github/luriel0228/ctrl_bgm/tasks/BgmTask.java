package com.github.luriel0228.ctrl_bgm.tasks;

import com.github.luriel0228.ctrl_bgm.datafile.DataFile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class BgmTask {

    private final Plugin plugin;
    private final Map<Player, BukkitTask> playerTasks;
    private FileConfiguration config;

    public BgmTask(Plugin plugin) {
        config = plugin.getConfig();
        this.plugin = plugin;
        this.playerTasks = new HashMap<>();
    }

    public void startTask(Player player) {
        if (!playerTasks.containsKey(player)) {
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    if (DataFile.getBgmData(player)) {
                        player.playSound(player.getLocation(), config.getString("bgm_name"), 1, 1);
                    }
                }
            }.runTaskTimer(plugin, 0, 20L * config.getInt("bgm_interval"));

            playerTasks.put(player, task);
        }
    }

    public void stopTask(Player player) {
        BukkitTask task = playerTasks.remove(player);
        if (task != null) {
            task.cancel();
        }
    }
}
