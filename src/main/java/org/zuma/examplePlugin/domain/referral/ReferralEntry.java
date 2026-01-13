package org.zuma.examplePlugin.domain.referral;

import java.sql.Timestamp;
import java.util.UUID;

public record ReferralEntry(String mentorUsername, String menteeUsername, UUID mentorUUID, UUID menteeUUID,
                            int totalLevel, boolean isMentorRewardClaimed, boolean isMenteeRewardClaimed, Timestamp creationDate) {
}
