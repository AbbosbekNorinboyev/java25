package uz.brb.java25.service;

import module java.base;
import org.springframework.data.domain.Pageable;
import uz.brb.java25.dto.response.Response;

public interface ApiLogService {
    Response<?> saveLog(String username, String method, String path, LocalDateTime time, long duration);

    Response<?> getAll(Pageable pageable);
}