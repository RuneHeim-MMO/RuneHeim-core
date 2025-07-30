package org.zuma.examplePlugin.core.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;

public class HikariPool {
    private HikariDataSource ds;

    public HikariPool() {
    }

    public void initialize(Dotenv dotenv) {

        String url = dotenv.get("JDBC_URL");
        String usr = dotenv.get("MYSQL_USERNAME");
        String pw = dotenv.get("MYSQL_PASSWORD");

        if (url == null || usr == null || pw == null) {
            throw new Error("Environment values are not set.");
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(usr);
        config.setPassword(pw);

        // ✅ Pool sizing
        config.setMaximumPoolSize(10);        // Tune for your load
        config.setMinimumIdle(2);             // Keep a few idle ready

        // ✅ Connection timeout settings
        config.setConnectionTimeout(30000);   // 30s max wait for a connection
        config.setIdleTimeout(600000);        // 10 min idle timeout (connections removed if idle this long)
        config.setMaxLifetime(1800000);       // 30 min max lifetime (recycled before MySQL closes them)

        // ✅ Keepalive (optional but helpful)
        config.setKeepaliveTime(300000);      // Every 5 min, do a lightweight ping on idle connections

        // Optional tuning
        config.setValidationTimeout(5000);    // 5s timeout for connection test
        config.setLeakDetectionThreshold(10000); // Warn if a connection is open > 10s

        ds = new HikariDataSource(config);

        try (var connection = ds.getConnection()) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Connected to the MySQL Server: " + !connection.isClosed());
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to connect to MySQL Server", e);
        }
    }

    public HikariDataSource getDataSource() {
        return this.ds;
    }

    public void closeConnection() {
        if (this.ds != null)
            this.ds.close();
    }


}
