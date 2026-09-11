package uz.brb.java25.service;

import org.springframework.data.domain.Pageable;
import uz.brb.java25.dto.request.RegionRequest;
import uz.brb.java25.dto.response.Response;

public interface RegionService {
    Response<?> createRegion(RegionRequest request);

    Response<?> getAllRegion(Pageable pageable);

    Response<?> getRegion(Long id);

    Response<?> updateRegion(Long id, RegionRequest request);

    Response<?> deleteRegion(Long id);
}
