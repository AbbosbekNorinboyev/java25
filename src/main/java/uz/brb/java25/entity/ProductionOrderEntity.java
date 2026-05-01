package uz.brb.java25.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "production_order")
public class ProductionOrderEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "order_number", nullable = false, unique = true, length = 64)
    private String orderNumber;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "status", length = 32)
    private String status;

    @Column(name = "planned_start_date")
    private Instant plannedStartDate;

    @Column(name = "planned_end_date")
    private Instant plannedEndDate;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "sku", length = 128)
    private String sku;

    @Column(name = "unit", length = 32)
    private String unit;

    @Column(name = "production_line_code", length = 64)
    private String productionLineCode;

    @Column(name = "target_warehouse_id", length = 64)
    private String targetWarehouseId;

    @Column(name = "notes", length = 1024)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
