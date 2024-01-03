package com.github.luriel0228.ctrl_bgm;

import com.github.luriel0228.ctrl_bgm.commands.BgmCommand;
import com.github.luriel0228.ctrl_bgm.datafile.DataFile;
import com.github.luriel0228.ctrl_bgm.listener.BgmOnJoinListener;
import com.github.luriel0228.ctrl_bgm.message.MessageConfig;
import com.github.luriel0228.ctrl_bgm.tasks.BgmTask;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ctrl_bgm extends JavaPlugin {

    public FileConfiguration config;
    public DataFile dataFile;
    private BgmTask bgmTask;

    @Override
    public void onEnable() {
        config = getConfig();
        saveDefaultConfig();
        MessageConfig.setup();
        dataFile = new DataFile("USERDATA.db", "plugins/Ctrl_bgm");
        if (config != null && config.getBoolean("BgmPlugin.EnablePlugin")) {
            setExecutor();
            registerEvents();
        } else {
            getLogger().info("플러그인이 비활성화 상태입니다. 활성화하려면 config.yml에서 `EnablePlugin: true`로 설정한 후 서버를 재시작 해주십시오.");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        DataFile.close();
    }

    private void setExecutor() {
        getCommand("bgm").setExecutor(new BgmCommand(this));
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new BgmOnJoinListener(), this);
    }

}
