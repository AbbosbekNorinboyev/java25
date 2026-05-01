package uz.brb.java25.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.brb.java25.dto.PageResponse;
import uz.brb.java25.dto.request.ProductRequest;
import uz.brb.java25.dto.response.ProductResponse;
import uz.brb.java25.entity.ProductEntity;
import uz.brb.java25.repository.ProductRepository;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> getAll(Pageable pageable) {
        Page<ProductResponse> page = productRepository.findAll(pageable).map(this::toResponse);
        return PageResponse.from(page);
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(UUID.randomUUID());
        productEntity.setName(request.name());
        productEntity.setUnit(request.unit());
        productEntity.setDescription(request.description());
        productEntity.setCreatedAt(Instant.now());
        productEntity.setUpdatedAt(Instant.now());
        productRepository.save(productEntity);
        return toResponse(productEntity);
    }

    private ProductResponse toResponse(ProductEntity p) {
        return new ProductResponse(
                p.getId(),
                p.getName(),
                p.getUnit(),
                p.getDescription(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }
}
