package uz.brb.java25.dto.response;

import java.time.Instant;
import java.util.UUID;

public record ProductionOrderResponse(
        UUID id,
        String orderNumber,
        UUID productId,
        Integer quantity,
        String status,
        Instant plannedStartDate,
        Instant plannedEndDate,
        Instant createdAt,
        Instant updatedAt
) {}
