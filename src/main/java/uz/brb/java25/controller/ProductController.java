package uz.brb.java25.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.brb.java25.dto.PageResponse;
import uz.brb.java25.dto.request.ProductRequest;
import uz.brb.java25.dto.response.ProductResponse;
import uz.brb.java25.service.ProductService;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Tag(name = "Productions", description = "Products management")
public class ProductController {

    private final ProductService service;

    @GetMapping
    @Operation(summary = "List products", security = @SecurityRequirement(name = "bearerAuth"))
    public PageResponse<ProductResponse> list(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(200) int size
    ) {
        return service.getAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        );
    }

    @PostMapping
    @Operation(summary = "Create a product", security = @SecurityRequirement(name = "bearerAuth"))
    public ProductResponse create(@Validated @RequestBody ProductRequest request) {
        return service.create(request);
    }
}
