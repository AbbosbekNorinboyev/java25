package uz.brb.java25.service.impl;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.brb.java25.dto.request.RegionRequest;
import uz.brb.java25.dto.response.RegionResponse;
import uz.brb.java25.dto.response.Response;
import uz.brb.java25.entity.Region;
import uz.brb.java25.enums.Status;
import uz.brb.java25.exception.CustomException;
import uz.brb.java25.repository.RegionRepository;
import uz.brb.java25.service.RegionService;

import java.time.LocalDateTime;
import java.util.List;

import static uz.brb.java25.util.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;

    @Override
    @Transactional
    public Response<?> createRegion(RegionRequest request) {
        Region region = Region.builder()
                .nameUz(request.getNameUz())
                .nameRu(request.getNameRu())
                .nameEn(request.getNameEn())
                .status(Status.ACTIVE)
                .build();
        regionRepository.save(region);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Region successfully created")
                .data(toResponse(region))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Response<?> getAllRegion(Pageable pageable) {
        Page<@NonNull Region> page = regionRepository.findAll(pageable);
        List<RegionResponse> regions = page.map(this::toResponse).toList();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Region list successfully found")
                .data(regions)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .elements(page.getTotalElements())
                .pages(page.getTotalPages())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Response<?> getRegion(Long id) {
        Region region = findActiveById(id);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Region successfully found")
                .data(toResponse(region))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    @Transactional
    public Response<?> updateRegion(Long id, RegionRequest request) {
        Region region = findActiveById(id);
        region.setNameUz(request.getNameUz());
        region.setNameRu(request.getNameRu());
        region.setNameEn(request.getNameEn());
        if (request.getStatus() != null) {
            region.setStatus(request.getStatus());
        }
        regionRepository.save(region);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Region successfully updated")
                .data(toResponse(region))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    @Transactional
    public Response<?> deleteRegion(Long id) {
        Region region = findActiveById(id);
        region.setStatus(Status.DELETED);
        regionRepository.save(region);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Region successfully deleted")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    private Region findActiveById(Long id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> CustomException.notFound("Region not found by id: " + id));
        if (region.getStatus() == Status.DELETED) {
            throw CustomException.notFound("Region not found by id: " + id);
        }
        return region;
    }

    private RegionResponse toResponse(Region region) {
        return RegionResponse.builder()
                .id(region.getId())
                .nameUz(region.getNameUz())
                .nameRu(region.getNameRu())
                .nameEn(region.getNameEn())
                .status(region.getStatus())
                .createdAt(region.getCreatedAt())
                .updatedAt(region.getUpdatedAt())
                .build();
    }
}
