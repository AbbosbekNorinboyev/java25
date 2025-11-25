package uz.brb.java25.service.impl;

import module java.base;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.brb.java25.dto.response.Response;
import uz.brb.java25.entity.ApiLog;
import uz.brb.java25.repository.ApiLogRepository;
import uz.brb.java25.service.ApiLogService;

import static uz.brb.java25.util.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ApiLogServiceImpl implements ApiLogService {
    private final ApiLogRepository apiLogRepository;

    @Override
    public Response<?> saveLog(String username, String method, String path, LocalDateTime time, long duration) {
        ApiLog apiLog = ApiLog.builder()
                .username(username)
                .method(method)
                .path(path)
                .timestamp(time)
                .durationMs(duration)
                .build();
        apiLogRepository.save(apiLog);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("ApiLog successfully saved")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getAll(Pageable pageable) {
        Page<ApiLog> apiLogs = apiLogRepository.findAll(pageable);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("ApiLog list successfully found")
                .data(apiLogs)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
