package uz.brb.java25.dto.response;

import java.time.Instant;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String unit,
        String description,
        Instant createdAt,
        Instant updatedAt) {
}
