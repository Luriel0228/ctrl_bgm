package com.github.luriel0228.ctrl_bgm.message;

import com.github.luriel0228.ctrl_bgm.Ctrl_bgm;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MessageConfig {

    private static File messageFile;
    public static FileConfiguration messageConfig;

    public static void setup() {
        if (messageFile == null) {
            messageFile = new File(Ctrl_bgm.getPlugin(Ctrl_bgm.class).getDataFolder(), "message.yml");
        }
        if (!messageFile.exists()) {
            Ctrl_bgm.getPlugin(Ctrl_bgm.class).saveResource("message.yml", false);
        }
        messageConfig = YamlConfiguration.loadConfiguration(messageFile);
        Message.instance = new Message(MessageConfig.messageConfig);
    }


    public static void reload() {
        try {
            if (messageFile == null || !messageFile.exists()) {
                setup();
            } else {
                messageConfig = YamlConfiguration.loadConfiguration(messageFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}