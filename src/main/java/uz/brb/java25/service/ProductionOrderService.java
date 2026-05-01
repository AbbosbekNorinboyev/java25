package uz.brb.java25.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.brb.java25.dto.PageResponse;
import uz.brb.java25.dto.request.ProductionOrderRequest;
import uz.brb.java25.dto.response.ProductionOrderResponse;
import uz.brb.java25.entity.ProductEntity;
import uz.brb.java25.entity.ProductionOrderEntity;
import uz.brb.java25.exception.CustomException;
import uz.brb.java25.repository.ProductRepository;
import uz.brb.java25.repository.ProductionOrderRepository;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductionOrderService {

    private final ProductionOrderRepository repository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public PageResponse<ProductionOrderResponse> getAll(Pageable pageable) {
        Page<ProductionOrderResponse> page = repository.findAll(pageable)
                .map(this::toResponse);
        return PageResponse.from(page);
    }

    @Transactional
    public ProductionOrderResponse create(ProductionOrderRequest request) {
        ProductEntity productEntity = productRepository.findById(request.productId())
                .orElseThrow(() -> CustomException.notFound("Product not found"));

        ProductionOrderEntity entity = new ProductionOrderEntity();
        entity.setId(UUID.randomUUID());
        entity.setOrderNumber(UUID.randomUUID().toString().substring(0, 8)); // Generate temporary order number
        entity.setProduct(productEntity);
        entity.setQuantity(request.plannedQuantity());
        entity.setStatus("PLANNED");
        entity.setPlannedStartDate(request.plannedStart());
        entity.setPlannedEndDate(request.plannedEnd());
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());

        // Map new fields
        entity.setSku(request.sku());
        entity.setUnit(request.unit());
        entity.setProductionLineCode(request.productionLineCode());
        entity.setTargetWarehouseId(request.targetWarehouseId());
        entity.setNotes(request.notes());

        return toResponse(repository.save(entity));
    }

    private ProductionOrderResponse toResponse(ProductionOrderEntity e) {
        return new ProductionOrderResponse(
                e.getId(),
                e.getOrderNumber(),
                e.getProduct() != null ? e.getProduct().getId() : null,
                e.getQuantity(),
                e.getStatus(),
                e.getPlannedStartDate(),
                e.getPlannedEndDate(),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }
}
