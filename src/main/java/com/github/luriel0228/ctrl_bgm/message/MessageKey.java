package com.github.luriel0228.ctrl_bgm.message;

import lombok.Getter;

@Getter
public enum MessageKey {

    PREFIX("normal.prefix"),
    RELOAD_CONFIG("normal.reload_config"),

    PLAYER_ONLY("error.player_only"),
    SQL_ERROR("error.sql_error"),
    NO_PERMISSION("error.no_permission"),

    SUCCESS_ON("main.success_on"),
    SUCCESS_OFF("main.success_off");

    private final String key;

    MessageKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}