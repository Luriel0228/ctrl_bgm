package com.github.luriel0228.ctrl_bgm.listener;

import com.github.luriel0228.ctrl_bgm.datafile.DataFile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class BgmOnJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        if (DataFile.getBgmData(event.getPlayer())) {
            DataFile.setBgmData(event.getPlayer(), true);
            event.getPlayer().playSound(event.getPlayer().getLocation(), "minecraft.bgm", 1, 1);
        } else {
            event.getPlayer().stopSound("minecraft.bgm");
        }

    }

}
