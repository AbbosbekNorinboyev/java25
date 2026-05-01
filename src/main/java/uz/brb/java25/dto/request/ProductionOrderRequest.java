package uz.brb.java25.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record ProductionOrderRequest(
        @NotBlank String sku,
        UUID productId,
        @NotNull Integer plannedQuantity,
        @NotBlank String unit,
        @NotNull Instant plannedStart,
        @NotNull Instant plannedEnd,
        String productionLineCode,
        String targetWarehouseId,
        String notes
) {
}
