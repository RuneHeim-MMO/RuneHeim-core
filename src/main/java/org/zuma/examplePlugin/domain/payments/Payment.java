package org.zuma.examplePlugin.domain.payments;

import java.math.BigDecimal;
import java.util.UUID;

public record Payment(UUID playerUUID, String username, String transactionId, BigDecimal amount, String email) {
}
