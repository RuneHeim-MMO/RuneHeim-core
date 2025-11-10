package org.zuma.examplePlugin.domain.payments;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentsHistoryRepository {
    private final HikariPool pool;

    public PaymentsHistoryRepository(HikariPool pool) {
        this.pool = pool;
    }

    public void addPayment(Payment payment) throws SQLException {
        String paymentSql = """
                INSERT INTO payments_history (uuid, username, transaction_id, amount, email)
                VALUES (?, ?, ?, ?, ?)""";

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(paymentSql)) {

            stmt.setString(1, payment.playerUUID().toString());
            stmt.setString(2, payment.username());
            stmt.setString(3, payment.transactionId());
            stmt.setBigDecimal(4, payment.amount());
            stmt.setString(5, payment.email());
            stmt.executeUpdate();

        }
    }

    public BigDecimal getTotalPayments(String uuid) {
        String sql = "SELECT SUM(amount) FROM payments_history WHERE uuid = ?";

        try (Connection conn = pool.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, uuid);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    BigDecimal total = rs.getBigDecimal(1);
                    return total != null ? total : BigDecimal.ZERO;
                }
            }

        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL,
                    "Failed to calculate total payments for UUID " + uuid + "!", e);
        }

        return BigDecimal.ZERO;
    }

}
