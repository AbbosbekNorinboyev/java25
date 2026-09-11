package uz.brb.java25.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.brb.java25.enums.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegionResponse {
    private Long id;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
