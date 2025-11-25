package uz.brb.java25.dto.response;

import module java.base;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private Integer code;
    private HttpStatus status;
    private String message;
    private Boolean success;
    private Map<String, String> errors;
    private Map<String, Object> meta = new HashMap<>();
    private Object data;
    private Long elements;
    private Integer pages;
    private String timestamp;
    private String path;
}