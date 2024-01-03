package com.github.luriel0228.ctrl_bgm.commands;

import com.github.luriel0228.ctrl_bgm.Ctrl_bgm;
import com.github.luriel0228.ctrl_bgm.datafile.DataFile;
import com.github.luriel0228.ctrl_bgm.message.Message;
import com.github.luriel0228.ctrl_bgm.message.MessageConfig;
import com.github.luriel0228.ctrl_bgm.message.MessageKey;
import com.github.luriel0228.ctrl_bgm.tasks.BgmTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BgmCommand implements CommandExecutor {

    private final Ctrl_bgm plugin;
    private final BgmTask bgmTask;

    public BgmCommand(Ctrl_bgm plugin) {
        this.plugin = plugin;
        this.bgmTask = new BgmTask(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(Message.getInstance().getMessage(MessageKey.PLAYER_ONLY));
            return true;
        }

        if (args.length == 0) {
            handleBgmCommand(player);
        } else if (args[1].equalsIgnoreCase("reload")) {
            handleReloadCommand(player);
        }

        return false;
    }

    private void handleBgmCommand(Player player) {
        if (!(DataFile.getBgmData(player))) {
            DataFile.setBgmData(player, true);
            player.sendMessage(Message.getInstance().getMessage(MessageKey.SUCCESS_ON));
            bgmTask.startTask(player);
        } else {
            DataFile.setBgmData(player, false);
            player.sendMessage(Message.getInstance().getMessage(MessageKey.SUCCESS_OFF));
            player.stopSound("minecraft:bgm");
            bgmTask.stopTask(player);
        }
    }

    private void handleReloadCommand(Player player) {
        if (player.isOp()) {
            plugin.reloadConfig();
            MessageConfig.reload();
            player.sendMessage(Message.getInstance().getMessage(MessageKey.RELOAD_CONFIG));
        } else {
            player.sendMessage(Message.getInstance().getMessage(MessageKey.NO_PERMISSION));
        }
    }
}
