package uz.brb.java25.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.brb.java25.enums.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank(message = "fullName can not be null or empty")
    private String fullName;
    @NotBlank(message = "username can not be null or empty")
    private String username;
    @NotBlank(message = "password can not be null or empty")
    private String password;
    @NotBlank(message = "role can not be null or empty")
    private Role role;
}