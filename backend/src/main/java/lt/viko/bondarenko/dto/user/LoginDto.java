package lt.viko.bondarenko.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDto {
    private String email;
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
