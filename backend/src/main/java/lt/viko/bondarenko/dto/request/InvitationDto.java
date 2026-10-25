package lt.viko.bondarenko.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvitationDto {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;
}