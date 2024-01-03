package com.github.luriel0228.ctrl_bgm.datafile;

import org.bukkit.entity.Player;

import java.io.File;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataFile {

    private static final Logger logger = Logger.getLogger(DataFile.class.getName());
    private static Connection connection;

    public DataFile(String dbName, String dbPath) {
        try {
            File dbDirectory = new File(dbPath);
            if (!dbDirectory.exists()) {
                dbDirectory.mkdirs();
            }

            String dbFilePath = dbPath + File.separator + dbName;

            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);
            createTable();
        } catch (SQLException e) {
            handleSQLException("연결 또는 테이블 생성 중 오류가 발생했습니다", e);
        }
    }

    private void createTable() {
        try (Statement statement = connection.createStatement()) {
            String invitesTableSQL = "CREATE TABLE IF NOT EXISTS settings (" +
                    "player STRING, " +
                    "bgm_enabled BOOLEAN)";
            statement.execute(invitesTableSQL);
        } catch (SQLException e) {
            handleSQLException("테이블 생성 중 오류가 발생했습니다", e);
        }
    }

    public static Boolean getBgmData(Player player) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT bgm_enabled FROM settings WHERE player = ?")) {
            statement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBoolean("bgm_enabled");
            } else {
                setBgmData(player, true);
                return true;
            }
        } catch (SQLException e) {
            handleSQLException("BGM 데이터 가져오기 중 오류가 발생했습니다", e);
            return true;
        }
    }

    public static void setBgmData(Player player, boolean isEnabled) {
        if (player == null) {
            return;
        }

        try {
            try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE settings SET bgm_enabled = ? WHERE player = ?")) {
                updateStatement.setBoolean(1, isEnabled);
                updateStatement.setString(2, player.getUniqueId().toString());
                int updateCount = updateStatement.executeUpdate();

                if (updateCount == 0) {
                    try (PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO settings (player, bgm_enabled) VALUES (?, ?)")) {
                        insertStatement.setString(1, player.getUniqueId().toString());
                        insertStatement.setBoolean(2, isEnabled);
                        insertStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            handleSQLException("BGM 데이터 설정 중 오류가 발생했습니다", e);
        }
    }

    private static void handleSQLException(String errorMessage, SQLException e) {
        logger.log(Level.SEVERE, errorMessage, e);
    }

    public static void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            handleSQLException("연결 종료 중 오류가 발생했습니다", e);
        } finally {
            connection = null;
        }
    }
}
