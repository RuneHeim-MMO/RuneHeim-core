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

        ds = new HikariDataSource(config);

        try (var connection = ds.getConnection()) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Connected to the MySQL Server: " + !connection.isClosed());
        } catch (Exception e) {
            e.printStackTrace();
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
