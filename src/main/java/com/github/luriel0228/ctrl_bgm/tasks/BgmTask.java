package com.github.luriel0228.ctrl_bgm.tasks;

import com.github.luriel0228.ctrl_bgm.datafile.DataFile;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class BgmTask {

    private final Plugin plugin;
    private final Map<Player, BukkitTask> playerTasks;

    public BgmTask(Plugin plugin) {
        this.plugin = plugin;
        this.playerTasks = new HashMap<>();
    }

    public void startTask(Player player) {
        if (!playerTasks.containsKey(player)) {
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    if (DataFile.getBgmData(player)) {
                        player.playSound(player.getLocation(), "minecraft:bgm", 1, 1);
                    }
                }
            }.runTaskTimer(plugin, 0, 20 * 60);

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
