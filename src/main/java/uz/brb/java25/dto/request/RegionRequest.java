package uz.brb.java25.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.brb.java25.enums.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegionRequest {
    @NotBlank(message = "nameUz can not be null or empty")
    private String nameUz;

    @NotBlank(message = "nameRu can not be null or empty")
    private String nameRu;

    @NotBlank(message = "nameEn can not be null or empty")
    private String nameEn;

    private Status status;
}
