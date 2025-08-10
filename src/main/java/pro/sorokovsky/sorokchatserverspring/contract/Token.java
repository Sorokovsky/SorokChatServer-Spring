package pro.sorokovsky.sorokchatserverspring.contract;

import java.time.Instant;
import java.util.UUID;

public record Token(UUID id, String subject, Instant createdAt, Instant expiresAt) {
}
