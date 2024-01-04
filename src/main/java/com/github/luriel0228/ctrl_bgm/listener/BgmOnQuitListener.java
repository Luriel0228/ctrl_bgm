package com.github.luriel0228.ctrl_bgm.listener;

import com.github.luriel0228.ctrl_bgm.Ctrl_bgm;
import com.github.luriel0228.ctrl_bgm.datafile.DataFile;
import com.github.luriel0228.ctrl_bgm.tasks.BgmTask;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class BgmOnQuitListener implements Listener {

    private final BgmTask bgmTask;

    public BgmOnQuitListener(Ctrl_bgm plugin) {
        this.bgmTask = new BgmTask(plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent event) {
        if (DataFile.getBgmData(event.getPlayer())) {
            DataFile.setBgmData(event.getPlayer(), true);
            bgmTask.stopTask(event.getPlayer());
        }
    }

}