package com.ordwen.odailyquests.database.h2;

import com.ordwen.odailyquests.ODailyQuests;
import com.ordwen.odailyquests.database.LoadProgressionSQL;
import com.ordwen.odailyquests.database.SQLManager;
import com.ordwen.odailyquests.database.SaveProgressionSQL;
import com.ordwen.odailyquests.tools.PluginLogger;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public class H2Manager extends SQLManager {

    private final ODailyQuests oDailyQuests;

    public H2Manager(ODailyQuests oDailyQuests) {
        this.oDailyQuests = oDailyQuests;

        super.loadProgressionSQL = new LoadProgressionSQL(this);
        super.saveProgressionSQL = new SaveProgressionSQL(this);

        setupDatabase();
    }

    /**
     * Init database.
     */
    public void setupDatabase() {
        initH2();

        try {
            testConnection();
        } catch (SQLException e) {
            PluginLogger.error(e.getMessage());
        }

        setupTables();
    }

    private void initH2() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.h2.Driver");
        config.setJdbcUrl("jdbc:h2:./plugins/ODailyQuests/database");
        config.setUsername("odq");
        config.setPassword("");
        config.setMaximumPoolSize(100);
        config.setMaxLifetime(300000L);
        config.setLeakDetectionThreshold(60000L);
        config.setConnectionTimeout(60000L);
        super.hikariDataSource = new HikariDataSource(config);
    }
}
