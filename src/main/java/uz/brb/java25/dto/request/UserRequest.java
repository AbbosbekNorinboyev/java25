package uz.brb.java25.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.brb.java25.enums.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    @NotBlank(message = "fullName can not be null or empty")
    private String fullName;

    @NotBlank(message = "username can not be null or empty")
    private String username;

    private String password;

    @NotNull(message = "role can not be null")
    private Role role;
}
