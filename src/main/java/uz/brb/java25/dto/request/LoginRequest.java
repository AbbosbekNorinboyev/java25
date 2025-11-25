package uz.brb.java25.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank(message = "username can not be null or empty")
    private String username;
    @NotBlank(message = "password can not be null or empty")
    private String password;
}