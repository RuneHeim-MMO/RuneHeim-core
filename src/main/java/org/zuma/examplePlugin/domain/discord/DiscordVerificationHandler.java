package org.zuma.examplePlugin.domain.discord;

import org.zuma.examplePlugin.domain.discord.persistence.VerificationRepository;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class DiscordVerificationHandler {
    private final VerificationRepository repository;

    public DiscordVerificationHandler(VerificationRepository repository) {
        this.repository = repository;
    }

    @Nullable
    public String createOrUpdateVerificationCode(UUID playerUUID) throws IllegalStateException {
        String code = generateCode();

        try {
            if (repository.doesVerificationCodeExist(playerUUID)) {
                repository.overwriteVerificationCode(playerUUID, code);
            } else {
                repository.addVerificationCode(playerUUID, code);
            }
        } catch (Exception e) {
            return null;
        }

        return code;
    }

    public boolean isPlayerVerified(UUID playerUUID) {
        try {
            return repository.isPlayerVerified(playerUUID);
        } catch (Exception e) {
            return false;
        }
    }

    public String generateCode() {
        ThreadLocalRandom r = ThreadLocalRandom.current();

        return String.format("%c%c%c-%c%c%c-%c%c%c",
                'a' + r.nextInt(26), 'a' + r.nextInt(26), 'a' + r.nextInt(26),
                'a' + r.nextInt(26), 'a' + r.nextInt(26), 'a' + r.nextInt(26),
                'a' + r.nextInt(26), 'a' + r.nextInt(26), 'a' + r.nextInt(26)
        );
    }



}
